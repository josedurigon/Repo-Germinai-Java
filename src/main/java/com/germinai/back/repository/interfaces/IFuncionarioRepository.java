package com.germinai.back.repository.interfaces;

import com.germinai.back.entities.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFuncionarioRepository extends JpaRepository<Funcionario, Long> {
}
