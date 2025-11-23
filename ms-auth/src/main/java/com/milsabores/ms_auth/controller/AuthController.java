package com.milsabores.ms_auth.controller;

import com.milsabores.ms_auth.model.dto.LoginDto;
import com.milsabores.ms_auth.model.entity.Usuario;
import com.milsabores.ms_auth.repository.UsuarioRepository;
import com.milsabores.ms_auth.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // LOGIN: Recibe correo/clave y devuelve un Token
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginRequest) {
        try {
            // 1. Validamos las credenciales (Spring Security hace la magia aquí)
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getCorreo(), loginRequest.getContrasena())
            );

            // 2. Si pasó, buscamos al usuario para sacar sus datos
            Usuario usuario = usuarioRepository.findByCorreo(loginRequest.getCorreo()).get();

            // 3. Generamos el Token
            String token = jwtUtil.generateToken(usuario.getCorreo());

            // 4. Preparamos la respuesta (Token + Rol + Nombre)
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("token", token);
            respuesta.put("rol", usuario.getRol());
            respuesta.put("nombre", usuario.getNombreCompleto());
            respuesta.put("idUsuario", usuario.getIdUsuario());

            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Error: Credenciales incorrectas");
        }
    }
    
    // REGISTRO: Crea un usuario nuevo (encriptando la clave)
    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            return ResponseEntity.badRequest().body("Error: El correo ya existe");
        }
        // Encriptamos la contraseña antes de guardar
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuario registrado con éxito");
    }
}
