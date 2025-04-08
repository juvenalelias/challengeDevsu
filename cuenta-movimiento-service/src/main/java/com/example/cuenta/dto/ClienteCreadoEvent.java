package com.example.cuenta.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClienteCreadoEvent {
    private Long clienteId;
    private String nombre;
    private String identificacion;
    private boolean estado;
}
