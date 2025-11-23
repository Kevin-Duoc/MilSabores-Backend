package com.milsabores.ms_pedidos.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "DETALLE_PEDIDO")
@Data
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE")
    private Long idDetalle;

    @ManyToOne
    @JoinColumn(name = "ID_PEDIDO", nullable = false)
    @JsonIgnore // Para evitar bucles infinitos al convertir a JSON
    private Pedido pedido;

    @Column(name = "ID_PRODUCTO_REF", nullable = false)
    private Long idProductoRef;

    @Column(name = "NOMBRE_PRODUCTO")
    private String nombreProducto;

    @Column(name = "PRECIO_UNITARIO", nullable = false)
    private Integer precioUnitario;

    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;
}
