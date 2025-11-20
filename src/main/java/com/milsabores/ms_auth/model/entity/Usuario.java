package com.milsabores.ms_auth.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "USUARIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long idUsuario;

    @Column(name = "NOMBRE_COMPLETO")
    private String nombreCompleto;

    @Column(name = "CORREO", unique = true, nullable = false)
    private String correo;

    @Column(name = "CONTRASENA", nullable = false)
    private String contrasena;

    @Column(name = "ROL", nullable = false)
    private String rol;

    @Column(name = "TELEFONO")
    private String telefono;

    //FK
    @Column(name = "ID_REGION")
    private Long idRegion;

    @Column(name = "ID_COMUNA")
    private Long idComuna;

    // --- MÉTODOS DE SEGURIDAD (UserDetails) ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convierte tu rol (ej: "ADMIN") en un permiso de Spring ("ROLE_ADMIN")
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + rol));
    }

    @Override
    public String getPassword() {
        return this.contrasena;
    }

    @Override
    public String getUsername() {
        return this.correo; //correo para loguearse
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
