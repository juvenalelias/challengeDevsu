package com.example.cuenta.service;

import com.example.cuenta.entity.Cliente;
import com.example.cuenta.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClienteServiceImpl implements ClienteService{

    private final ClienteRepository repository;

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Optional<Cliente> findById(Long id) {
        return Optional.ofNullable(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado")));
    }

    public Cliente save(Cliente cliente) {

        return repository.save(cliente);
    }
}
