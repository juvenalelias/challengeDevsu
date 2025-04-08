package com.example.cliente.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.*;

@Data
@Entity

@SQLDelete(sql = "UPDATE cliente SET estado=false WHERE id = ?")
//@Where(clause = "estado=true")
@EqualsAndHashCode(callSuper = true)
public class Cliente extends Persona {
    private String password;

    @Column(nullable = false)
    private boolean estado = true;
}
