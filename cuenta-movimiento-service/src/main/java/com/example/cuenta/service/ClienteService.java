package com.example.cuenta.service;

import com.example.cuenta.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    public List<Cliente> findAll();

    public Cliente save(Cliente cliente);

    public Optional<Cliente> findById (Long id);

}
