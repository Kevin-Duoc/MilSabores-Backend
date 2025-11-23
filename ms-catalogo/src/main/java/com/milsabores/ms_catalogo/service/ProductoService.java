package com.milsabores.ms_catalogo.service;

import com.milsabores.ms_catalogo.model.dto.CategoriaDto;
import com.milsabores.ms_catalogo.model.dto.ProductoDto;
import com.milsabores.ms_catalogo.model.entity.Categoria;
import com.milsabores.ms_catalogo.model.entity.Producto;
import com.milsabores.ms_catalogo.repository.CategoriaRepository;
import com.milsabores.ms_catalogo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional; 
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // obtener todos los productos
    public List<ProductoDto> listarProductos() {
        return productoRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // BUSCAR POR ID
    public ProductoDto buscarPorId(Long id) {
        // Buscamos en la BD. findById retorna un "Optional" (puede estar o no)
        Optional<Producto> productoOptional = productoRepository.findById(id);
        
        // Si existe, lo convertimos a DTO y lo devolvemos. Si no, retornamos null.
        if (productoOptional.isPresent()) {
            return mapToDto(productoOptional.get());
        } else {
            return null;
        }
    }
    // -----------------------------------

    // obtener todas las categorias (en el front se filtrará)
    public List<CategoriaDto> listarCategorias() {
        return categoriaRepository.findAll().stream()
                .map(cat -> {
                    CategoriaDto dto = new CategoriaDto();
                    dto.setIdCategoria(cat.getIdCategoria());
                    dto.setNombre(cat.getNombre());
                    return dto;
                }).collect(Collectors.toList());
    }

    // metodo para convertir de entidad a dto
    private ProductoDto mapToDto(Producto producto) {
        ProductoDto dto = new ProductoDto();
        dto.setIdProducto(producto.getIdProducto());
        dto.setCodigoSku(producto.getCodigoSku());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setUrlImagen(producto.getUrlImagen());
        dto.setStock(producto.getStock());
        dto.setStockCritico(producto.getStockCritico());
        
        // Datos de la categoría aplanados
        if (producto.getCategoria() != null) {
            dto.setIdCategoria(producto.getCategoria().getIdCategoria());
            dto.setNombreCategoria(producto.getCategoria().getNombre());
        }
        return dto;
    }
}
