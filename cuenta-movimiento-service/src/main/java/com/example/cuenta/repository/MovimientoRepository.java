package com.example.cuenta.repository;

import com.example.cuenta.dto.ReporteDTO;
import com.example.cuenta.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    @Query("Select m.fecha as fecha, cl.name as nombre, c.numeroCuenta as numeroCuenta, c.tipoCuenta as tipoCuenta, m.saldoInicial as saldoInicial, c.estado as estado, m.valor as valor, m.saldo as saldo from Movimiento m join Cuenta c on m.numeroCuenta = c.numeroCuenta join Cliente cl on c.clienteId = cl.id where cl.id = :clienteId and m.fecha >= :desde and m.fecha <= :hasta")
    List<ReporteDTO> findByClienteIdAndFechaBetween(Long clienteId, LocalDate desde, LocalDate hasta);
    List<Movimiento> findByNumeroCuenta(String numeroCuenta);
}
