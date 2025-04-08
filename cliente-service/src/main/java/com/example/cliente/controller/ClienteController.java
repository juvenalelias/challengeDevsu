package com.example.cliente.controller;

import com.example.cliente.entity.Cliente;
import com.example.cliente.service.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Cliente> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() ->new EntityNotFoundException("Cliente no Existente"));
    }

    @PostMapping
    public Cliente create(@RequestBody Cliente cliente) {

        return service.save(cliente);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Cliente updatedCliente) {

        return ResponseEntity.ok(service.updateCustomer(updatedCliente));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
