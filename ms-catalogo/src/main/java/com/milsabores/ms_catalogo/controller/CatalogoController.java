package com.milsabores.ms_catalogo.controller;

import com.milsabores.ms_catalogo.model.dto.CategoriaDto;
import com.milsabores.ms_catalogo.model.dto.ProductoDto;
import com.milsabores.ms_catalogo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalogo")
@CrossOrigin(origins = "*")
public class CatalogoController {

    @Autowired
    private ProductoService productoService;

    // Endpoint 1: Traer todos los productos
    // URL: http://localhost:8082/api/v1/catalogo/productos
    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDto>> listarProductos() {
        List<ProductoDto> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    // Endpoint 2: Traer todas las categorías 
    // URL: http://localhost:8082/api/v1/catalogo/categorias
    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaDto>> listarCategorias() {
        List<CategoriaDto> categorias = productoService.listarCategorias();
        return ResponseEntity.ok(categorias);
    }

    // Endpoint 3: Buscar un producto por su ID
    // URL: http://localhost:8082/api/v1/catalogo/productos/{id}
    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoDto> obtenerProductoPorId(@PathVariable Long id) {
        ProductoDto producto = productoService.buscarPorId(id);
        
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
