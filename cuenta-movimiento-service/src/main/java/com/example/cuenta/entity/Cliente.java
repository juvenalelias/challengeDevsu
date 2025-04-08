package com.example.cuenta.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class Cliente {

    @Id
    private Long id;
    private String name;
    private String identificacion;
    @Column(nullable = false)
    private boolean estado = true;
}
