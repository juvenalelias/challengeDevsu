package com.example.cliente.dto;

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
