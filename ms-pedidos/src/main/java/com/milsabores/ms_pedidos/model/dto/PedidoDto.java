package com.milsabores.ms_pedidos.model.dto;

import lombok.Data;
import java.util.List;

@Data
public class PedidoDto {
    private Long idUsuarioRef; //viene del login quien comprara
    private Integer total;
    private List<DetallePedidoDto> detalles;
}
