package com.milsabores.ms_catalogo.repository;

import com.milsabores.ms_catalogo.model.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // Aquí podrías agregar: findByNombre(String nombre); si lo necesitaras
}
