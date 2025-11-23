package com.milsabores.ms_pedidos.model.dto;

import lombok.Data;

@Data
public class DetallePedidoDto {
    private Long idProductoRef;    
    private String nombreProducto; 
    private Integer precioUnitario;
    private Integer cantidad;
}
