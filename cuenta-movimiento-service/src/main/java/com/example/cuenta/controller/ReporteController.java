package com.example.cuenta.controller;

import com.example.cuenta.dto.CuentaReporteDTO;
import com.example.cuenta.dto.ReporteDTO;
import com.example.cuenta.service.MovimientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReporteController {
    private final MovimientoService movimientoService;

    public ReporteController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @GetMapping
    public List<ReporteDTO> reporte(
        @RequestParam Long clienteId,
        @RequestParam String desde,
        @RequestParam String hasta
    ) {
        return movimientoService.obtenerPorCuentaYFechas(clienteId, LocalDate.parse(desde), LocalDate.parse(hasta));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<CuentaReporteDTO>> getReporteCuentas(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoService.obtenerReporteCuentas(id));
    }
}
