package com.example.cliente.service;

import com.example.cliente.entity.Cliente;
import com.example.cliente.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClienteServiceImpl implements ClienteService{

    private final ClienteRepository repository;
    private final ClienteEventPublisher clienteEventPublisher;

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Optional<Cliente> findById(Long id) {
        return Optional.ofNullable(
                repository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Cliente no válido")));
    }

    public Cliente save(Cliente cliente) {

        Cliente cli = repository.save(cliente);
        clienteEventPublisher.publicarClienteCreado(cli);
        return cli;
    }

    public void delete(Long id) {

        Cliente client = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        repository.deleteById(client.getId());

        client.setEstado(false);
        clienteEventPublisher.publicarClienteCreado(client);
    }

    public Cliente updateCustomer(Cliente cliente) {
        Cliente client = repository.findById(cliente.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        client.setIdentificacion(cliente.getIdentificacion());
        client.setNombre(cliente.getNombre());
        client.setGenero(cliente.getGenero());
        client.setEstado(cliente.isEstado());
        client.setEdad(cliente.getEdad());
        client.setDireccion(cliente.getDireccion());
        client.setTelefono(cliente.getTelefono());
        client.setPassword(cliente.getPassword());

        Cliente cli = repository.save(client);
        clienteEventPublisher.publicarClienteCreado(cli);
        return cli;
    }
}
