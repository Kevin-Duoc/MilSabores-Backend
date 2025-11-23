package com.milsabores.ms_pedidos.controller;

import com.milsabores.ms_pedidos.model.dto.PedidoDto;
import com.milsabores.ms_pedidos.model.entity.Pedido;
import com.milsabores.ms_pedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    //crear pedido (recibe el carrito del React)
    //URL: POST http://localhost:8083/api/v1/pedidos
    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody PedidoDto pedidoDto) {
        try {
            Pedido nuevoPedido = pedidoService.crearPedido(pedidoDto);
            return ResponseEntity.ok("Pedido creado con éxito. ID: " + nuevoPedido.getIdPedido());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al procesar el pedido: " + e.getMessage());
        }
    }

    //listar pedidos de un usuario (historial)
    // URL: GET http://localhost:8083/api/v1/pedidos/usuario/{id}
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Pedido>> obtenerPedidos(@PathVariable Long idUsuario) {
        List<Pedido> pedidos = pedidoService.obtenerPedidosPorUsuario(idUsuario);
        return ResponseEntity.ok(pedidos);
    }
}
