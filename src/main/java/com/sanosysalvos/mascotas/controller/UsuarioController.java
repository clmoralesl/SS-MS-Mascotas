package com.sanosysalvos.mascotas.controller;

import com.sanosysalvos.mascotas.dto.UsuarioRequestDTO;
import com.sanosysalvos.mascotas.dto.UsuarioResponseDTO;
import com.sanosysalvos.mascotas.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    /**
     * Endpoint para registrar un nuevo usuario luego de que inicie sesión en Auth0.
     * En el futuro, el API Gateway llamará o permitirá este paso.
     */
    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponseDTO> registrarUsuario(@Valid @RequestBody UsuarioRequestDTO request) {
        UsuarioResponseDTO nuevoPerfil = usuarioService.registrarUsuario(request);
        return new ResponseEntity<>(nuevoPerfil, HttpStatus.CREATED);
    }

    /**
     * Endpoint protegido. Devuelve el perfil del usuario utilizando la cabecera X-Auth0-Id.
     * Supone que el API Gateway inyecta esta cabecera tras validar el JWT de Auth0. 
     */
    @GetMapping("/me")
    public ResponseEntity<UsuarioResponseDTO> obtenerMiPerfil(@RequestHeader("X-Auth0-Id") String auth0Id) {
        UsuarioResponseDTO miPerfil = usuarioService.obtenerPerfilPorAuth0Id(auth0Id);
        return ResponseEntity.ok(miPerfil);
    }

    @PutMapping("/me")
    public ResponseEntity<UsuarioResponseDTO> actualizarMiPerfil(@RequestHeader("X-Auth0-Id") String auth0Id, @Valid @RequestBody UsuarioRequestDTO request) {
        UsuarioResponseDTO perfilActualizado = usuarioService.actualizarUsuario(auth0Id, request);
        return ResponseEntity.ok(perfilActualizado);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> eliminarMiPerfil(@RequestHeader("X-Auth0-Id") String auth0Id) {
        usuarioService.eliminarUsuario(auth0Id);
        return ResponseEntity.noContent().build();
    }
}
