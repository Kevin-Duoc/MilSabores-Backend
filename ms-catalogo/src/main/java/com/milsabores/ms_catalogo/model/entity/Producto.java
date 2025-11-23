package com.milsabores.ms_catalogo.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "PRODUCTO")
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUCTO")
    private Long idProducto;
    
    @ManyToOne //se relaciona con CATEGORIA, muchos productos pertenecen a una categoria
    @JoinColumn(name = "ID_CATEGORIA", nullable = false)
    private Categoria categoria;

    @Column(name = "CODIGO_SKU")
    private String codigoSku;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "PRECIO", nullable = false)
    private Integer precio;

    @Column(name = "URL_IMAGEN")
    private String urlImagen;

    @Column(name = "STOCK", nullable = false)
    private Integer stock;

    @Column(name = "STOCK_CRITICO")
    private Integer stockCritico;
}
