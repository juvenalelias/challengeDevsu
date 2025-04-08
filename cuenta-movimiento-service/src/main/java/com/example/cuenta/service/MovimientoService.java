package com.example.cuenta.service;

import com.example.cuenta.dto.CuentaReporteDTO;
import com.example.cuenta.dto.MovimientoDTO;
import com.example.cuenta.dto.ReporteDTO;
import com.example.cuenta.entity.Movimiento;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovimientoService {
    public Optional<Movimiento> getById(Long id);
    public Movimiento registrarMovimiento(MovimientoDTO movimiento);
    public List<ReporteDTO> obtenerPorCuentaYFechas(Long clienteId, LocalDate desde, LocalDate hasta);
    public List<CuentaReporteDTO> obtenerReporteCuentas(Long id);
}
