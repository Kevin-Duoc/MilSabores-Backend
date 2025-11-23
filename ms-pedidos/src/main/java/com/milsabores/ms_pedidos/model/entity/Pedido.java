package com.milsabores.ms_pedidos.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "PEDIDO")
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PEDIDO")
    private Long idPedido;

    @Column(name = "ID_USUARIO_REF", nullable = false)
    private Long idUsuarioRef;

    @Column(name = "FECHA")
    private LocalDateTime fecha;

    @Column(name = "ESTADO")
    private String estado; // EMITIDA, PAGADA, ETC.

    @Column(name = "TOTAL")
    private Integer total;

    // Relación: Un pedido tiene muchos detalles
    // "mappedBy" se refiere al nombre del atributo en la clase DetallePedido
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<DetallePedido> detalles;
    
    // Método útil para que se asigne la fecha al crear
    @PrePersist
    public void prePersist() {
        this.fecha = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = "EMITIDA";
        }
    }
}
