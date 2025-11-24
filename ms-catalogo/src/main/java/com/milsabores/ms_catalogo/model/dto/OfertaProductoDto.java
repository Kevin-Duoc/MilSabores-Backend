package com.milsabores.ms_catalogo.model.dto;

import lombok.Data;

@Data
public class OfertaProductoDto {
    private Long idProducto;
    private String nombre;
    private String urlImagen;
    private Integer precioNormal; // Precio original de la tabla PRODUCTO
    private Integer porcentajeOferta; // Porcentaje ACTIVO (ej: 10)
    private Integer precioOferta; // Precio ya calculado (ej: $45000 * 0.9)
    private String nombreOferta;
}
