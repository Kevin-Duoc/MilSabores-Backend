package com.milsabores.ms_pedidos.service;

import com.milsabores.ms_pedidos.model.dto.DetallePedidoDto;
import com.milsabores.ms_pedidos.model.dto.PedidoDto;
import com.milsabores.ms_pedidos.model.entity.DetallePedido;
import com.milsabores.ms_pedidos.model.entity.Pedido;
import com.milsabores.ms_pedidos.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido crearPedido(PedidoDto pedidoDto) {
        
        //crear la cabecera del pedido (Entity)
        Pedido pedido = new Pedido();
        pedido.setIdUsuarioRef(pedidoDto.getIdUsuarioRef());
        pedido.setTotal(pedidoDto.getTotal());
        //fecha y estado se ponen solos por el @PrePersist de la entidad
        
        List<DetallePedido> detallesEntity = new ArrayList<>();
        
        for (DetallePedidoDto itemDto : pedidoDto.getDetalles()) {
            DetallePedido detalle = new DetallePedido();
            detalle.setIdProductoRef(itemDto.getIdProductoRef());
            detalle.setNombreProducto(itemDto.getNombreProducto());
            detalle.setPrecioUnitario(itemDto.getPrecioUnitario());
            detalle.setCantidad(itemDto.getCantidad());
            
            //Decirle al detalle a qué pedido pertenece
            detalle.setPedido(pedido); 
            
            detallesEntity.add(detalle);
        }

        //asigna la lista de detalles al pedido
        pedido.setDetalles(detallesEntity);

        //guardar todo en cascada
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> obtenerPedidosPorUsuario(Long idUsuario) {
        return pedidoRepository.findByIdUsuarioRef(idUsuario);
    }
}