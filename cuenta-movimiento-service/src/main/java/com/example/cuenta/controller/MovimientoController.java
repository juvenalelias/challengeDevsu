package com.example.cuenta.controller;

import com.example.cuenta.dto.CuentaReporteDTO;
import com.example.cuenta.dto.MovimientoDTO;
import com.example.cuenta.entity.Movimiento;
import com.example.cuenta.service.MovimientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {
    private final MovimientoService service;

    public MovimientoController(MovimientoService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimiento> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Movimiento registrar(@RequestBody MovimientoDTO movimiento) {
        return service.registrarMovimiento(movimiento);
    }



}
