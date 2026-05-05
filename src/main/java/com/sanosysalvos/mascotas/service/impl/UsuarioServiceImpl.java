package com.sanosysalvos.mascotas.service.impl;

import com.sanosysalvos.mascotas.dto.UsuarioRequestDTO;
import com.sanosysalvos.mascotas.dto.UsuarioResponseDTO;
import com.sanosysalvos.mascotas.entity.Organizacion;
import com.sanosysalvos.mascotas.entity.TipoCuenta;
import com.sanosysalvos.mascotas.entity.Usuario;
import com.sanosysalvos.mascotas.exception.ResourceNotFoundException;
import com.sanosysalvos.mascotas.repository.OrganizacionRepository;
import com.sanosysalvos.mascotas.repository.TipoCuentaRepository;
import com.sanosysalvos.mascotas.repository.UsuarioRepository;
import com.sanosysalvos.mascotas.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TipoCuentaRepository tipoCuentaRepository;
    private final OrganizacionRepository organizacionRepository;

    @Override
    @Transactional
    public UsuarioResponseDTO registrarUsuario(UsuarioRequestDTO request) {
        // Verificar si el usuario ya existe para este Auth0 ID
        if (usuarioRepository.existsByAuth0Id(request.getAuth0Id())) {
            throw new RuntimeException("El usuario con Auth0 ID " + request.getAuth0Id() + " ya está registrado.");
        }

        // Si no envía tipo de cuenta, asignamos el ID 1 por defecto (Usuario Estándar)
        Long idTipoCuenta = (request.getIdTipoCuenta() != null) ? request.getIdTipoCuenta() : 1L;
        TipoCuenta tipoCuenta = tipoCuentaRepository.findById(idTipoCuenta)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de cuenta no encontrado con ID: " + idTipoCuenta));

        Organizacion organizacion = null;
        if (request.getIdOrganizacion() != null) {
            organizacion = organizacionRepository.findById(request.getIdOrganizacion())
                    .orElseThrow(() -> new ResourceNotFoundException("Organización no encontrada con ID: " + request.getIdOrganizacion()));
        }

        Usuario nuevoUsuario = Usuario.builder()
                .auth0Id(request.getAuth0Id())
                .nombre(request.getNombre())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .tipoCuenta(tipoCuenta)
                .organizacion(organizacion)
                .build();

        Usuario guardado = usuarioRepository.save(nuevoUsuario);

        return mapToDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO obtenerPerfilPorAuth0Id(String auth0Id) {
        Usuario usuario = usuarioRepository.findByAuth0Id(auth0Id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un perfil para el Auth0 ID proporcionado."));
        
        return mapToDTO(usuario);
    }

    // Helper metod para convertir a DTO
    private UsuarioResponseDTO mapToDTO(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .auth0Id(usuario.getAuth0Id())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .telefono(usuario.getTelefono())
                .nombreOrganizacion(usuario.getOrganizacion() != null ? usuario.getOrganizacion().getNombreOrganizacion() : null)
                .descripcionTipoCuenta(usuario.getTipoCuenta() != null ? usuario.getTipoCuenta().getDescripcion() : null)
                .build();
    }
}
