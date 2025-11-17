package com.germinai.back.repository.interfaces;

import com.germinai.back.entities.SerieHistoricaSoja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SerieHistoricaRepository extends JpaRepository<SerieHistoricaSoja, Long> {
    List<SerieHistoricaSoja> findTop15ByCommodityOrderByDataDesc(String commodity);
    List<SerieHistoricaSoja> findTop90ByCommodityOrderByDataDesc(String commodity);
    Optional<SerieHistoricaSoja> findByCommodityAndData(String commodity, LocalDate data);



    @Query("""
        SELECT s.preco FROM SerieHistoricaSoja s
        WHERE s.commodity = :commodity
        ORDER BY s.data DESC
        LIMIT :limit
    """)
    List<BigDecimal> buscarUltimosPrecos(String commodity, int limit);
}
