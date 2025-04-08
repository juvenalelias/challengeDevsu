package com.example.cuenta.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

// MovimientoDTO.java
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@SuperBuilder
@ToString
public class RepoMovimientoDTO {
    private LocalDate fecha;
    private String tipoMovimiento;
    private BigDecimal valor;
    private BigDecimal saldo;
}
