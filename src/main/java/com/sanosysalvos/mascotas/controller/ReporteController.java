package com.sanosysalvos.mascotas.controller;

import com.sanosysalvos.mascotas.dto.ReporteRequestDTO;
import com.sanosysalvos.mascotas.dto.ReporteResponseDTO;
import com.sanosysalvos.mascotas.service.ReporteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    @PostMapping
    public ResponseEntity<ReporteResponseDTO> crearReporte(@Valid @RequestBody ReporteRequestDTO request,
                                                           @RequestHeader("X-Auth0-Id") String auth0Id) {
        return new ResponseEntity<>(reporteService.crearReporte(request, auth0Id), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReporteResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(reporteService.obtenerTodosLosReportes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reporteService.obtenerReportePorId(id));
    }

    @PutMapping("/{id}/cerrar")
    public ResponseEntity<ReporteResponseDTO> cerrarReporte(@PathVariable Long id, @RequestHeader("X-Auth0-Id") String auth0Id) {
        return ResponseEntity.ok(reporteService.cerrarReporte(id, auth0Id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteResponseDTO> actualizarReporte(@PathVariable Long id, @Valid @RequestBody ReporteRequestDTO request, @RequestHeader("X-Auth0-Id") String auth0Id) {
        return ResponseEntity.ok(reporteService.actualizarReporte(id, request, auth0Id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReporte(@PathVariable Long id, @RequestHeader("X-Auth0-Id") String auth0Id) {
        reporteService.eliminarReporte(id, auth0Id);
        return ResponseEntity.noContent().build();
    }
}
