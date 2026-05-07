package com.sanosysalvos.mascotas.controller;

import com.sanosysalvos.mascotas.dto.MascotaRequestDTO;
import com.sanosysalvos.mascotas.dto.MascotaResponseDTO;
import com.sanosysalvos.mascotas.service.MascotaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mascotas")
@RequiredArgsConstructor
public class MascotaController {

    private final MascotaService mascotaService;

    @PostMapping
    public ResponseEntity<MascotaResponseDTO> createMascota(@Valid @RequestBody MascotaRequestDTO request) {
        MascotaResponseDTO response = mascotaService.createMascota(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> getMascotaById(@PathVariable Long id) {
        return ResponseEntity.ok(mascotaService.getMascotaById(id));
    }

    @GetMapping
    public ResponseEntity<List<MascotaResponseDTO>> getAllMascotas() {
        return ResponseEntity.ok(mascotaService.getAllMascotas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> updateMascota(@PathVariable Long id, @Valid @RequestBody MascotaRequestDTO request) {
        return ResponseEntity.ok(mascotaService.updateMascota(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMascota(@PathVariable Long id) {
        mascotaService.deleteMascota(id);
        return ResponseEntity.noContent().build();
    }
}
