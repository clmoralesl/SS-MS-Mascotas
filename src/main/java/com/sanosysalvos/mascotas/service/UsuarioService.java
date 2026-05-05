package com.sanosysalvos.mascotas.service;

import com.sanosysalvos.mascotas.dto.UsuarioRequestDTO;
import com.sanosysalvos.mascotas.dto.UsuarioResponseDTO;

public interface UsuarioService {
    UsuarioResponseDTO registrarUsuario(UsuarioRequestDTO request);
    UsuarioResponseDTO obtenerPerfilPorAuth0Id(String auth0Id);
}
