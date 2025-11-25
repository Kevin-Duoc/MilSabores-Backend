package com.milsabores.ms_pedidos.controller;

import com.milsabores.ms_pedidos.model.dto.PedidoDto;
import com.milsabores.ms_pedidos.model.dto.DetallePedidoDto; 
import com.milsabores.ms_pedidos.model.entity.Pedido;
import com.milsabores.ms_pedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient; 

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // WebClient para la comunicación con ms-catalogo
    private final WebClient webClient; 

    // Constructor para inyectar WebClient
    public PedidoController(WebClient.Builder webClientBuilder) {
        // ⚠️ CAMBIO CRÍTICO: Usar la IP de la EC2 de CATÁLOGO, no localhost
        // IP Elástica de Catálogo: 52.73.124.122
        this.webClient = webClientBuilder
                .baseUrl("http://52.73.124.122:8082/api/v1/catalogo") 
                .build();
    }

    // crear pedido (recibe el carrito del React)
    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody PedidoDto pedidoDto) {
        try {
            // PASO 1: VERIFICACIÓN Y DESCUENTO DE STOCK EN ms-catalogo (8082)
            for (DetallePedidoDto detalle : pedidoDto.getDetalles()) {
                
                // Enviamos la cantidad en NEGATIVO para que el servicio de Catálogo la reste
                this.webClient.post()
                    .uri("/productos/stock/{idProducto}/{cantidad}", 
                          detalle.getIdProductoRef(), 
                          -detalle.getCantidad()) 
                    .retrieve()
                    .bodyToMono(String.class) 
                    .block(); // Bloqueamos para asegurar la atomicidad
            }

            // PASO 2: Si todos los descuentos de stock fueron exitosos, guardamos el pedido.
            Pedido nuevoPedido = pedidoService.crearPedido(pedidoDto);
            return ResponseEntity.ok("Pedido creado con éxito. ID: " + nuevoPedido.getIdPedido());

        } catch (Exception e) {
            // Capturamos el error de stock insuficiente (u otros errores)
            return ResponseEntity.badRequest().body("Error al procesar el pedido/stock: " + e.getMessage());
        }
    }

    // listar pedidos de un usuario (historial)
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Pedido>> obtenerPedidos(@PathVariable Long idUsuario) {
        List<Pedido> pedidos = pedidoService.obtenerPedidosPorUsuario(idUsuario);
        return ResponseEntity.ok(pedidos);
    }
}
