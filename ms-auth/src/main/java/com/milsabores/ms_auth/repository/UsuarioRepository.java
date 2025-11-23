package com.milsabores.ms_auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.milsabores.ms_auth.model.entity.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método mágico: Spring crea el SQL solo con leer el nombre del método
    // "SELECT * FROM USUARIO WHERE CORREO = ?"
    Optional<Usuario> findByCorreo(String correo);
    
    // Método para verificar si existe al registrarse
    boolean existsByCorreo(String correo);
}
