package com.example.cuenta;

import com.example.cuenta.entity.Cuenta;
import com.example.cuenta.entity.Movimiento;
import com.example.cuenta.repository.CuentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovimientoIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CuentaRepository cuentaRepository;

    @BeforeEach
    void setUp() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("9999");
        cuenta.setTipoCuenta("Ahorros");
        cuenta.setSaldoInicial(BigDecimal.valueOf(500));
        cuenta.setEstado(true);
        cuenta.setClienteId(1L);
        cuentaRepository.save(cuenta);
    }

    @Test
    void testRegistrarDeposito() {
        Movimiento mov = new Movimiento();
        mov.setNumeroCuenta("9999");
        mov.setTipoMovimiento("Deposito");
        mov.setValor(BigDecimal.valueOf(100));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Movimiento> request = new HttpEntity<>(mov, headers);
        ResponseEntity<Movimiento> response = restTemplate.postForEntity("http://localhost:" + port + "/movimientos", request, Movimiento.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(BigDecimal.valueOf(600.00).compareTo(response.getBody().getSaldo()) == 0);

    }
}
