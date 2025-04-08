package com.example.cuenta;

import com.example.cuenta.dto.MovimientoDTO;
import com.example.cuenta.entity.Cliente;
import com.example.cuenta.entity.Cuenta;
import com.example.cuenta.entity.Movimiento;
import com.example.cuenta.exceptions.SaldoInsuficienteException;
import com.example.cuenta.repository.ClienteRepository;
import com.example.cuenta.repository.CuentaRepository;
import com.example.cuenta.repository.MovimientoRepository;
import com.example.cuenta.service.MovimientoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovimientoServiceIntegrationTest {

    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private MovimientoRepository movimientoRepo;

    @Autowired
    private CuentaRepository cuentaRepo;

    @Autowired
    private ClienteRepository clienteRepo;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private Cuenta cuenta;
    private Cliente cliente;

    @BeforeEach
    void setup() {
        movimientoRepo.deleteAll();
        cuentaRepo.deleteAll();
        clienteRepo.deleteAll();

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setName("Juan");
        cliente.setEstado(true);
        cliente.setIdentificacion("1234");
        clienteRepo.save(cliente);

        cuenta = new Cuenta();
        cuenta.setNumeroCuenta("00897867857");
        cuenta.setSaldoInicial(BigDecimal.valueOf(500));
        cuenta.setTipoCuenta("AHORROS");
        cuenta.setClienteId(cliente.getId());
        cuentaRepo.save(cuenta);

        /**/
    }

    @Test
    void registrarMovimientoConSaldoSuficiente() {
        MovimientoDTO dto = new MovimientoDTO();
        dto.setNumeroCuenta(cuenta.getNumeroCuenta());
        dto.setValor(BigDecimal.valueOf(-100));
        dto.setTipoMovimiento("Retiro");

        Movimiento resultado = movimientoService.registrarMovimiento(dto);

        assertNotNull(resultado.getId());
        assertTrue(BigDecimal.valueOf(400).compareTo(resultado.getSaldo()) == 0);
    }

    @Test
    void registrarMovimientoConSaldoInsuficiente_deberiaLanzarExcepcion() {
        MovimientoDTO dto = new MovimientoDTO();
        dto.setNumeroCuenta(cuenta.getNumeroCuenta());
        dto.setValor(BigDecimal.valueOf(-600));
        dto.setTipoMovimiento("Retiro");

        assertThrows(SaldoInsuficienteException.class, () -> movimientoService.registrarMovimiento(dto));
    }

    @Test
    void obtenerMovimientosPorFechaYCliente() {
        Movimiento movimiento = Movimiento.builder()
                .numeroCuenta(cuenta.getNumeroCuenta())
                .fecha(LocalDate.now())
                .valor(BigDecimal.valueOf(100))
                .saldo(BigDecimal.valueOf(600))
                .saldoInicial(BigDecimal.valueOf(500))
                .tipoMovimiento("DEPOSITO")
                .build();

        movimientoRepo.save(movimiento);

        List<?> reportes = movimientoService.obtenerPorCuentaYFechas(cliente.getId(), LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
        assertFalse(reportes.isEmpty());
    }

    @Test
    void testRegistrarDeposito() {

        Cuenta account = new Cuenta();
        account.setNumeroCuenta("9999");
        account.setTipoCuenta("Ahorros");
        account.setSaldoInicial(BigDecimal.valueOf(500));
        account.setEstado(true);
        account.setClienteId(1L);
        cuentaRepo.save(account);

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
