package com.milsabores.ms_catalogo.model.dto;

import lombok.Data;

@Data
public class ProductoDto {
    private Long idProducto;   
    private String codigoSku;
    private String nombre;
    private String descripcion;
    private Integer precio;
    private String urlImagen;
    private Integer stock;
    private Integer stockCritico;
  
    private Long idCategoria;
    private String nombreCategoria; 
}
