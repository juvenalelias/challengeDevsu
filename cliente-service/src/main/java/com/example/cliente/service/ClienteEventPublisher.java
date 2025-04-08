package com.example.cliente.service;

import com.example.cliente.dto.ClienteCreadoEvent;
import com.example.cliente.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ClienteEventPublisher {

    @Autowired
    private KafkaTemplate<String, ClienteCreadoEvent> kafkaTemplate;

    public void publicarClienteCreado(Cliente cliente) {
        ClienteCreadoEvent event = new ClienteCreadoEvent();
        event.setClienteId(cliente.getId());
        event.setNombre(cliente.getNombre());
        event.setIdentificacion(cliente.getIdentificacion());
        event.setEstado(cliente.isEstado());

        kafkaTemplate.send("cliente-creado-topic", event);
    }
}
