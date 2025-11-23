package com.milsabores.ms_catalogo.repository;

import com.milsabores.ms_catalogo.model.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Método mágico: Busca productos por el ID de su categoría
    // SELECT * FROM PRODUCTO WHERE ID_CATEGORIA = ?
    List<Producto> findByCategoriaIdCategoria(Long idCategoria);
    
    // Opcional: Buscar por nombre (para una barra de búsqueda)
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
}
