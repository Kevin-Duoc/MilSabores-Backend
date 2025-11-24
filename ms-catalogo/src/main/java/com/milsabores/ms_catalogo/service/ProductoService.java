package com.milsabores.ms_catalogo.service;

import com.milsabores.ms_catalogo.model.dto.CategoriaDto;
import com.milsabores.ms_catalogo.model.dto.ProductoDto;
import com.milsabores.ms_catalogo.model.entity.Producto;
import com.milsabores.ms_catalogo.model.dto.OfertaProductoDto; 
import com.milsabores.ms_catalogo.repository.CategoriaRepository;
import com.milsabores.ms_catalogo.repository.ProductoRepository;
import com.milsabores.ms_catalogo.repository.DetalleOfertaRepository; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional; 
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private DetalleOfertaRepository detalleOfertaRepository; // <-- INYECCIÓN NUEVA

    // --- MÉTODO PRINCIPAL DE OFERTAS  ---
    /**
     * Busca todos los productos que tienen una oferta activa HOY y calcula el precio promocional.
     */
    public List<OfertaProductoDto> listarOfertasActivas() {
        
        // El Repository ejecuta la consulta compleja (fechas, estado ACTIVA)
        List<Producto> productosEnOferta = detalleOfertaRepository.findProductosActivosEnOferta();
        
        // 1. Mapear los productos encontrados a un DTO de Oferta
        return productosEnOferta.stream()
                .map(producto -> {
                    
                    // 2. Buscar el porcentaje de descuento ACTIVO para este producto específico
                    Optional<Integer> porcentajeOpt = detalleOfertaRepository
                        .findPorcentajeOfertaActivaByProductoId(producto.getIdProducto());
                        
                    // Si no hay porcentaje, usamos 0.
                    Integer porcentaje = porcentajeOpt.orElse(0); 
                    
                    // 3. Crear el DTO y calcular el precio final
                    OfertaProductoDto dto = new OfertaProductoDto();
                    dto.setIdProducto(producto.getIdProducto());
                    dto.setNombre(producto.getNombre());
                    dto.setUrlImagen(producto.getUrlImagen());
                    dto.setPrecioNormal(producto.getPrecio());
                    dto.setPorcentajeOferta(porcentaje);
                    
                    // Cálculo del Precio de Oferta
                    double descuento = producto.getPrecio() * (porcentaje / 100.0);
                    dto.setPrecioOferta((int) Math.round(producto.getPrecio() - descuento));

                    return dto;
                })
                .collect(Collectors.toList());
    }
    // -----------------------------------------------------

    // obtener todos los productos
    public List<ProductoDto> listarProductos() {
        return productoRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // BUSCAR POR ID
    public ProductoDto buscarPorId(Long id) {
        Optional<Producto> productoOptional = productoRepository.findById(id);
        
        if (productoOptional.isPresent()) {
            return mapToDto(productoOptional.get());
        } else {
            return null;
        }
    }

    // BUSCAR PRODUCTOS POR CATEGORIA (Filtro)
    public List<ProductoDto> obtenerProductosPorCategoria(Long idCategoria) {
        List<Producto> productos = productoRepository.findByCategoriaIdCategoria(idCategoria);
        return productos.stream()
                .map(this::mapToDto) 
                .collect(Collectors.toList());
    }

    // ACTUALIZAR STOCK (Concurrencia)
    @Transactional
    public void actualizarStock(Long idProducto, Integer cantidad) {
        Producto producto = productoRepository.findById(idProducto)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado."));

        Integer nuevoStock = producto.getStock() + cantidad; 
        
        if (nuevoStock < 0) {
            throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
        }

        producto.setStock(nuevoStock);
        productoRepository.save(producto);
    }
    
    // obtener todas las categorias
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
        
        if (producto.getCategoria() != null) {
            dto.setIdCategoria(producto.getCategoria().getIdCategoria());
            dto.setNombreCategoria(producto.getCategoria().getNombre());
        }
        return dto;
    }
}
