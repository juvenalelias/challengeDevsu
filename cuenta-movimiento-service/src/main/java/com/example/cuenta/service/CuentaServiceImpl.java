package com.example.cuenta.service;

import com.example.cuenta.entity.Cuenta;
import com.example.cuenta.repository.CuentaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaServiceImpl implements CuentaService{

    @Autowired
    private final CuentaRepository repository;

    public CuentaServiceImpl(CuentaRepository repository) {
        this.repository = repository;
    }

    public List<Cuenta> findAll() {
        return repository.findAll();
    }

    public Cuenta save(Cuenta cuenta) {
        return repository.save(cuenta);
    }

    public Optional<Cuenta> getByNumeroCuenta(String numero) {
        return Optional.ofNullable(repository.findById(numero)
                .orElseThrow(() -> new EntityNotFoundException("Cuenta no encontrada")));
    }

    public void delete(String numero) {
        Cuenta account = repository.findById(numero)
                .orElseThrow(() -> new EntityNotFoundException("Cuenta Inexistente..."));

        repository.deleteById(account.getNumeroCuenta());
    }

    public Cuenta updateAccount(Cuenta account) {
        Cuenta cuenta = repository.findById(account.getNumeroCuenta())
                .orElseThrow(() -> new EntityNotFoundException("Cuenta no encontrada"));

        cuenta.setNumeroCuenta(account.getNumeroCuenta());
        cuenta.setTipoCuenta(account.getTipoCuenta());
        cuenta.setEstado(account.isEstado());
        cuenta.setClienteId(account.getClienteId());
        cuenta.setSaldoInicial(account.getSaldoInicial());

        return repository.save(cuenta);
    }
}
