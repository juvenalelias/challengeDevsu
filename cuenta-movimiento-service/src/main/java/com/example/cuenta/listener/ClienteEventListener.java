package com.example.cuenta.listener;

import com.example.cuenta.dto.ClienteCreadoEvent;
import com.example.cuenta.entity.Cliente;
import com.example.cuenta.service.ClienteService;
import com.example.cuenta.service.ClienteServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClienteEventListener {

    private final ClienteService service;

    @ConditionalOnProperty(name = "kafka.enabled", havingValue = "true", matchIfMissing = true)
    @KafkaListener(topics = "cliente-creado-topic", groupId = "cuenta-service-group")
    public void recibirClienteCreado(String msg) throws JsonProcessingException {
        log.info("Event received {}", msg);

        ObjectMapper obj = new ObjectMapper();
        obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ClienteCreadoEvent event = obj.readValue(msg, ClienteCreadoEvent.class);

        service.save(Cliente.builder().id(event.getClienteId())
                .name(event.getNombre())
                .identificacion(event.getIdentificacion())
                .estado(event.isEstado())
                .build());

    }
}
