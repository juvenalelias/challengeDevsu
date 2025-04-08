package com.example.cliente.service;



import com.example.cliente.entity.Cliente;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.Optional;

public interface ClienteService {
    public List<Cliente> findAll();

    public Cliente save(Cliente cliente);

    public Cliente updateCustomer(Cliente cliente);

    void delete(Long id);

    public Optional<Cliente> findById (Long id);

}
