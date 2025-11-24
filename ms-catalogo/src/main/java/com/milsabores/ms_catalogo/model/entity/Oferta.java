package com.milsabores.ms_catalogo.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "OFERTA")
@Data
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_OFERTA")
    private Long idOferta;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Column(name = "PORCENTAJE")
    private Integer porcentaje; // 10, 20, 50, etc.
    
    @Column(name = "FECHA_INICIO", nullable = false)
    private LocalDate fechaInicio; // Usamos LocalDate para las fechas

    @Column(name = "FECHA_FIN", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "ESTADO")
    private String estado;
}
