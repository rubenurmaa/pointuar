package com.rarugames.pointuar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rarugames.pointuar.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
