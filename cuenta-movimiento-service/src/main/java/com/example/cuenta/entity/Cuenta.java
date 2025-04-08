package com.example.cuenta.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@SuperBuilder
@Data
@Entity
@ToString
@SQLDelete(sql = "UPDATE cuenta SET estado=false WHERE numero_cuenta = ?")
//@Where(clause = "estado=true")
public class Cuenta {
    @Id
    //@Column(nullable = false, name = "numeroCuenta")
    private String numeroCuenta;

    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private boolean estado = true;
    private Long clienteId;
}
