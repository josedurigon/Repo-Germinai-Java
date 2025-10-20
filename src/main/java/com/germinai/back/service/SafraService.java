package com.germinai.back.service;

import com.germinai.back.dto.CulturaSimpleDto;
import com.germinai.back.dto.FuncionarioSimpleDto;
import com.germinai.back.dto.safra.*;
import com.germinai.back.entities.*;
import com.germinai.back.repository.interfaces.*;
import com.germinai.back.service.interfaces.ISafraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SafraService implements ISafraService {

    private final ISafraRepository safraRepository;
    private final ICulturaRepository culturaRepository;
    private final IFuncionarioRepository funcionarioRepository;
    private final ITalhaoRepository talhaoRepository;

    @Override
    @Transactional
    public void criarSafra(Safra safra) {
        safra.setStatus("Planejada");
        this.safraRepository.save(safra);
    }

    @Transactional
    public SafraResponse criarSafraCompleta(SafraCreateRequest request) {
        // 1. Buscar cultura
        Cultura cultura = culturaRepository.findById(request.culturaId())
                .orElseThrow(() -> new RuntimeException("Cultura não encontrada"));

        // 2. Buscar responsável
        Funcionario responsavel = funcionarioRepository.findById(request.responsavelId())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        // 3. Criar entidade Safra
        Safra safra = new Safra();
        safra.setNome(request.nome());
        safra.setCultura(cultura);
        safra.setResponsavel(responsavel);
        safra.setDataInicio(request.dataInicio());

        // Calcular data fim se não fornecida
        if (request.dataFim() != null) {
            safra.setDataFim(request.dataFim());
        } else {
            safra.setDataFim(request.dataInicio().plusDays(cultura.getCicloDias()));
        }

        safra.setStatus("Planejada");
        safra.setAreaTotalHa(request.areaTotalHa());

        // 4. Associar talhões (se fornecidos)
        if (request.talhoes() != null && !request.talhoes().isEmpty()) {
            Set<SafraTalhao> safraTalhoes = new HashSet<>();

            double areaTotalTalhoes = 0.0;
            for (TalhaoSafraRequest talhaoReq : request.talhoes()) {
                Talhao talhao = talhaoRepository.findById(talhaoReq.talhaoId())
                        .orElseThrow(() -> new RuntimeException("Talhão não encontrado"));

                // Validar área
                if (talhaoReq.areaUtilizadaHa() > talhao.getAreaHa()) {
                    throw new RuntimeException(
                            "Área utilizada (" + talhaoReq.areaUtilizadaHa() +
                                    ") maior que área disponível do talhão (" + talhao.getAreaHa() + ")"
                    );
                }

                SafraTalhao safraTalhao = new SafraTalhao();
                safraTalhao.setSafra(safra);
                safraTalhao.setTalhao(talhao);
                safraTalhao.setAreaUtilizadaHa(talhaoReq.areaUtilizadaHa());

                safraTalhoes.add(safraTalhao);
                areaTotalTalhoes += talhaoReq.areaUtilizadaHa();
            }

            // Validar área total
            if (areaTotalTalhoes > request.areaTotalHa()) {
                throw new RuntimeException(
                        "Soma das áreas dos talhões (" + areaTotalTalhoes +
                                ") excede a área total da safra (" + request.areaTotalHa() + ")"
                );
            }

            safra.setTalhoes(safraTalhoes);
        }

        // 5. Criar meta (se fornecida)
        if (request.meta() != null) {
            MetaSafra meta = new MetaSafra();
            meta.setSafra(safra);
            meta.setProdutividadeAlvo(request.meta().produtividadeAlvo());
            meta.setCustoEstimadoTotal(request.meta().custoEstimadoTotal());
            safra.setMeta(meta);
        }

        // 6. Salvar
        Safra safraSalva = safraRepository.save(safra);

        // 7. Converter para response
        return convertToResponse(safraSalva);
    }

    @Override
    public List<Safra> buscarTodasAsSafras() {
        return this.safraRepository.findAll().stream().toList();
    }

    public List<SafraResponse> buscarTodasAsSafrasDto() {
        return safraRepository.findAll().stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<SafraResponse> buscarSafrasAtivas() {
        return safraRepository.findAll().stream()
                .filter(s -> !"Concluída".equals(s.getStatus()))
                .map(this::convertToResponse)
                .toList();
    }

    public Safra buscarPorId(Long id) {
        return safraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Safra não encontrada"));
    }

    @Override
    public Safra buscarSafraPorId(long id) {
        return this.safraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Safra não encontrada"));
    }

    public SafraResponse buscarSafraPorIdDto(Long id) {
        Safra safra = buscarPorId(id);
        return convertToResponse(safra);
    }

    @Override
    public Safra atualizar(Long id, Safra safraAtualizada) {
        Safra existente = buscarPorId(id);
        existente.setNome(safraAtualizada.getNome());
        existente.setCultura(safraAtualizada.getCultura());
        existente.setResponsavel(safraAtualizada.getResponsavel());
        existente.setDataInicio(safraAtualizada.getDataInicio());
        existente.setDataFim(safraAtualizada.getDataFim());
        existente.setStatus(safraAtualizada.getStatus());
        existente.setAreaTotalHa(safraAtualizada.getAreaTotalHa());
        return safraRepository.save(existente);
    }

    @Override
    public void deletar(Long id) {
        safraRepository.deleteById(id);
    }

    // Método auxiliar para converter entidade para DTO
    private SafraResponse convertToResponse(Safra safra) {
        // Calcular dias até colheita
        Integer diasAteColheita = null;
        if (safra.getDataFim() != null) {
            long dias = ChronoUnit.DAYS.between(LocalDate.now(), safra.getDataFim());
            diasAteColheita = (int) Math.max(0, dias);
        }

        // Calcular progresso (exemplo simplificado - pode ser melhorado)
        Integer progresso = calcularProgresso(safra);

        // Converter talhões
        List<TalhaoSafraDto> talhoesDto = null;
        if (safra.getTalhoes() != null && !safra.getTalhoes().isEmpty()) {
            talhoesDto = safra.getTalhoes().stream()
                    .map(st -> new TalhaoSafraDto(
                            st.getId(),
                            st.getTalhao().getNome(),
                            st.getAreaUtilizadaHa()
                    ))
                    .toList();
        }

        // Converter meta
        MetaSafraDto metaDto = null;
        if (safra.getMeta() != null) {
            metaDto = new MetaSafraDto(
                    safra.getMeta().getProdutividadeAlvo(),
                    safra.getMeta().getCustoEstimadoTotal()
            );
        }

        return new SafraResponse(
                safra.getId(),
                safra.getNome(),
                new CulturaSimpleDto(
                        safra.getCultura().getId(),
                        safra.getCultura().getNome(),
                        safra.getCultura().getCicloDias()
                ),
                new FuncionarioSimpleDto(
                        safra.getResponsavel().getId(),
                        safra.getResponsavel().getNome(),
                        safra.getResponsavel().getCargo()
                ),
                safra.getDataInicio(),
                safra.getDataFim(),
                safra.getStatus(),
                safra.getAreaTotalHa(),
                diasAteColheita,
                progresso,
                null, // TODO: Implementar cálculo de receita
                null, // TODO: Implementar cálculo de lucro
                talhoesDto,
                metaDto
        );
    }

    private Integer calcularProgresso(Safra safra) {
        // Cálculo simplificado baseado em tempo decorrido
        if (safra.getDataInicio() == null || safra.getDataFim() == null) {
            return 0;
        }

        LocalDate hoje = LocalDate.now();
        if (hoje.isBefore(safra.getDataInicio())) {
            return 0;
        }
        if (hoje.isAfter(safra.getDataFim())) {
            return 100;
        }

        long totalDias = ChronoUnit.DAYS.between(safra.getDataInicio(), safra.getDataFim());
        long diasDecorridos = ChronoUnit.DAYS.between(safra.getDataInicio(), hoje);

        if (totalDias == 0) {
            return 0;
        }

        return (int) ((diasDecorridos * 100) / totalDias);
    }
}
