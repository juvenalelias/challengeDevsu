package com.example.cuenta.service;

import com.example.cuenta.entity.Cuenta;

import java.util.List;
import java.util.Optional;

public interface CuentaService {
    public List<Cuenta> findAll();
    public Cuenta save(Cuenta cuenta);
    public Optional<Cuenta> getByNumeroCuenta(String numero);
    public void delete(String numero);
    public Cuenta updateAccount(Cuenta account);
}
