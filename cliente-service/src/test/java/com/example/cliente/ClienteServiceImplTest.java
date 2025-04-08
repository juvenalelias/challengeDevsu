package com.example.cliente;

import com.example.cliente.entity.Cliente;
import com.example.cliente.repository.ClienteRepository;
import com.example.cliente.service.ClienteEventPublisher;
import com.example.cliente.service.ClienteService;
import com.example.cliente.service.ClienteServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteEventPublisher clienteEventPublisher;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente sampleCliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleCliente = new Cliente();
        sampleCliente.setId(1L);
        sampleCliente.setNombre("Juan");
        sampleCliente.setEstado(true);
    }

    @Test
    void testFindAll() {
        List<Cliente> lista = List.of(sampleCliente);
        when(clienteRepository.findAll()).thenReturn(lista);

        List<Cliente> result = clienteService.findAll();

        assertEquals(1, result.size());
        assertEquals("Juan", result.get(0).getNombre());
    }

    @Test
    void testFindByIdSuccess() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(sampleCliente));

        Optional<Cliente> result = clienteService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Juan", result.get().getNombre());
    }

    @Test
    void testFindByIdThrows() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clienteService.findById(1L));
    }

    @Test
    void testSave() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(sampleCliente);

        Cliente saved = clienteService.save(sampleCliente);

        assertEquals("Juan", saved.getNombre());
        verify(clienteEventPublisher).publicarClienteCreado(sampleCliente);
    }

    @Test
    void testDeleteSuccess() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(sampleCliente));

        clienteService.delete(1L);

        verify(clienteRepository).deleteById(1L);
        verify(clienteEventPublisher).publicarClienteCreado(any(Cliente.class));
    }

    @Test
    void testDeleteNotFound() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clienteService.delete(1L));
    }

    @Test
    void testUpdateCustomerSuccess() {
        Cliente updatedCliente = new Cliente();
        updatedCliente.setId(1L);
        updatedCliente.setNombre("Pedro");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(sampleCliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(updatedCliente);

        Cliente result = clienteService.updateCustomer(updatedCliente);

        assertEquals("Pedro", result.getNombre());
        verify(clienteEventPublisher).publicarClienteCreado(updatedCliente);
    }

    @Test
    void testUpdateCustomerNotFound() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        Cliente input = new Cliente();
        input.setId(1L);
        assertThrows(EntityNotFoundException.class, () -> clienteService.updateCustomer(input));
    }
}
