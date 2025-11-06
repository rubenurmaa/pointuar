package com.rarugames.pointuar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rarugames.pointuar.model.ResumenMensual;

public interface ResumenMensualRepository extends JpaRepository<ResumenMensual, Long> {
    Optional<ResumenMensual> findByMes(String mes);
}
