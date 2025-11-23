package com.milsabores.ms_pedidos.repository;

import com.milsabores.ms_pedidos.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Buscar todos los pedidos de un usuario específico (Historial de compra)
    List<Pedido> findByIdUsuarioRef(Long idUsuarioRef);
}
