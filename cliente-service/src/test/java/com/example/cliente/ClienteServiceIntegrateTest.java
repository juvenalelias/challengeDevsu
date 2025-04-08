package com.example.cliente;

import com.example.cliente.entity.Cliente;
import com.example.cliente.repository.ClienteRepository;
import com.example.cliente.service.ClienteEventPublisher;
import com.example.cliente.service.ClienteServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteServiceIntegrateTest {
    @Mock
    private ClienteRepository repository;

    @Mock
    private ClienteEventPublisher clienteEventPublisher;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Juan");
        cliente.setEstado(true);
    }

    @Test
    void findAll_shouldReturnListOfClientes() {
        when(repository.findAll()).thenReturn(Arrays.asList(cliente));

        var result = clienteService.findAll();

        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    void findById_shouldReturnCliente() {
        when(repository.findById(1L)).thenReturn(Optional.of(cliente));

        var result = clienteService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Juan", result.get().getNombre());
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clienteService.findById(2L));
    }

    @Test
    void save_shouldStoreAndPublishCliente() {
        when(repository.save(cliente)).thenReturn(cliente);

        var saved = clienteService.save(cliente);

        assertEquals(cliente, saved);
        verify(clienteEventPublisher).publicarClienteCreado(cliente);
    }

    @Test
    void delete_shouldRemoveAndPublishCliente() {
        when(repository.findById(1L)).thenReturn(Optional.of(cliente));

        clienteService.delete(1L);

        verify(repository).deleteById(1L);
        verify(clienteEventPublisher).publicarClienteCreado(cliente);
        assertFalse(cliente.isEstado());
    }

    @Test
    void updateCustomer_shouldUpdateAndPublishCliente() {
        Cliente updated = new Cliente();
        updated.setId(1L);
        updated.setNombre("Pedro");
        updated.setEstado(true);

        when(repository.findById(1L)).thenReturn(Optional.of(cliente));
        when(repository.save(any())).thenReturn(updated);

        Cliente result = clienteService.updateCustomer(updated);

        assertEquals("Pedro", result.getNombre());
        verify(clienteEventPublisher).publicarClienteCreado(updated);
    }
}
