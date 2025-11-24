package com.milsabores.ms_catalogo.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "DETALLE_OFERTA")
@Data
public class DetalleOferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE_OFERTA")
    private Long idDetalleOferta;

    // Relación ManyToOne con Oferta
    @ManyToOne
    @JoinColumn(name = "ID_OFERTA", nullable = false)
    private Oferta oferta;

    // Relación ManyToOne con Producto
    // Aunque podríamos usar un Long, si el producto existe como Entidad, es más limpio relacionar
    @ManyToOne 
    @JoinColumn(name = "ID_PRODUCTO", nullable = false)
    private Producto producto;
}
