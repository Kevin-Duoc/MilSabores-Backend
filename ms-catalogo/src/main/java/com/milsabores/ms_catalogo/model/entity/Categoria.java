package com.milsabores.ms_catalogo.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "CATEGORIA")
@Data
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CATEGORIA")
    private Long idCategoria;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
}
