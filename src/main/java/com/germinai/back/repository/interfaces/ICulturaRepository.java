package com.germinai.back.repository.interfaces;

import com.germinai.back.entities.Cultura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICulturaRepository extends JpaRepository<Cultura, Long> {
}
