package com.example.cuenta.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@SuperBuilder
@Entity
@ToString
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private String tipoMovimiento;
    private BigDecimal saldoInicial;
    private BigDecimal valor;
    private BigDecimal saldo;
    private String numeroCuenta;
}
