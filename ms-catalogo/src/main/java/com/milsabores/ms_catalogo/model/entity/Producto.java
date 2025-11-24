package com.milsabores.ms_catalogo.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List; // Necesario para List<DetalleOferta>

@Entity
@Table(name = "PRODUCTO")
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUCTO")
    private Long idProducto;
    
    @ManyToOne 
    @JoinColumn(name = "ID_CATEGORIA", nullable = false)
    private Categoria categoria;

    // --- NUEVA RELACIÓN DE OFERTAS TEMPORALES (Opción 2) ---
    // Un Producto puede tener muchos registros en la tabla DETALLE_OFERTA
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleOferta> detallesOferta; 
    // --------------------------------------------------------

    @Column(name = "CODIGO_SKU")
    private String codigoSku;
    // ... resto de campos (NOMBRE, DESCRIPCION, PRECIO, URL_IMAGEN, STOCK, etc.)
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
