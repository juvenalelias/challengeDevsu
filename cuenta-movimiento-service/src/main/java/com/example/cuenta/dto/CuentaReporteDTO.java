package com.example.cuenta.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

// CuentaReporteDTO.java
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@SuperBuilder
@ToString
public class CuentaReporteDTO {
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldo;
    private List<RepoMovimientoDTO> movimientos;
}
