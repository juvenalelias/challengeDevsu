package com.example.cuenta.repository;

import com.example.cuenta.dto.ReporteDTO;
import com.example.cuenta.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuentaRepository extends JpaRepository<Cuenta, String> {
    List<Cuenta> findByClienteId(Long id);
}
