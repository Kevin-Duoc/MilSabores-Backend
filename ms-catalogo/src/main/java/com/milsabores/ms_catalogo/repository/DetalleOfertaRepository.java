package com.milsabores.ms_catalogo.repository;

import com.milsabores.ms_catalogo.model.entity.DetalleOferta;
import com.milsabores.ms_catalogo.model.entity.Producto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DetalleOfertaRepository extends JpaRepository<DetalleOferta, Long> {
    
    // MÉTODO CRÍTICO: Buscar ofertas activas ahora mismo 
    // Esta consulta trae todos los productos que están en una OFERTA activa.
    @Query("SELECT do.producto FROM DetalleOferta do JOIN do.oferta o " +
           "WHERE o.estado = 'ACTIVA' AND o.fechaInicio <= CURRENT_DATE AND o.fechaFin >= CURRENT_DATE")
    List<Producto> findProductosActivosEnOferta();

    // MÉTODO ADICIONAL: Obtener el porcentaje de una oferta activa para un producto
    @Query("SELECT o.porcentaje FROM DetalleOferta do JOIN do.oferta o " +
           "WHERE do.producto.idProducto = :idProducto AND o.estado = 'ACTIVA' " +
           "AND o.fechaInicio <= CURRENT_DATE AND o.fechaFin >= CURRENT_DATE")
    Optional<Integer> findPorcentajeOfertaActivaByProductoId(@Param("idProducto") Long idProducto);
}
