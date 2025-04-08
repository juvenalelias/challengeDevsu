package com.example.cuenta.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;


public interface ReporteDTO {
    LocalDate getFecha();
    String getNombre();
    String getNumeroCuenta();
    String getTipoCuenta();
    BigDecimal getSaldoInicial();
    boolean getEstado();
    BigDecimal getValor();
    BigDecimal getSaldo();
}
