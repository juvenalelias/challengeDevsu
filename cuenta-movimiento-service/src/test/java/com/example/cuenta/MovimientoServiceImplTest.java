package com.example.cuenta;

import com.example.cuenta.dto.CuentaReporteDTO;
import com.example.cuenta.dto.MovimientoDTO;
import com.example.cuenta.dto.RepoMovimientoDTO;
import com.example.cuenta.dto.ReporteDTO;
import com.example.cuenta.entity.Cliente;
import com.example.cuenta.entity.Cuenta;
import com.example.cuenta.entity.Movimiento;
import com.example.cuenta.exceptions.SaldoInsuficienteException;
import com.example.cuenta.repository.ClienteRepository;
import com.example.cuenta.repository.CuentaRepository;
import com.example.cuenta.repository.MovimientoRepository;
import com.example.cuenta.service.MovimientoServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovimientoServiceImplTest {

    private MovimientoServiceImpl movimientoService;
    private MovimientoRepository movimientoRepo;
    private CuentaRepository cuentaRepo;
    private ClienteRepository clienteRepo;

    @BeforeEach
    void setup() {
        movimientoRepo = mock(MovimientoRepository.class);
        cuentaRepo = mock(CuentaRepository.class);
        clienteRepo = mock(ClienteRepository.class);
        movimientoService = new MovimientoServiceImpl(movimientoRepo, cuentaRepo, clienteRepo);
    }

    @Test
    void registrarMovimiento_conSaldoSuficiente_deberiaRegistrarMovimiento() {
        MovimientoDTO dto = new MovimientoDTO();
        dto.setNumeroCuenta("0012345678");
        dto.setValor(BigDecimal.valueOf(-50));
        dto.setTipoMovimiento("RETIRO");

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("0012345678");
        cuenta.setSaldoInicial(BigDecimal.valueOf(100));

        when(cuentaRepo.findById("0012345678")).thenReturn(Optional.of(cuenta));
        when(movimientoRepo.save(any(Movimiento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Movimiento resultado = movimientoService.registrarMovimiento(dto);

        assertEquals(BigDecimal.valueOf(50), resultado.getSaldo());
        assertEquals(BigDecimal.valueOf(-50), resultado.getValor());
        verify(cuentaRepo).save(cuenta);
    }

    @Test
    void registrarMovimiento_conSaldoInsuficiente_deberiaLanzarExcepcion() {
        MovimientoDTO dto = new MovimientoDTO();
        dto.setNumeroCuenta("0012345678");
        dto.setValor(BigDecimal.valueOf(-150));

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("0012345678");
        cuenta.setSaldoInicial(BigDecimal.valueOf(100));

        when(cuentaRepo.findById("0012345678")).thenReturn(Optional.of(cuenta));

        assertThrows(SaldoInsuficienteException.class, () -> {
            movimientoService.registrarMovimiento(dto);
        });

        verify(movimientoRepo, never()).save(any());
    }

    @Test
    void obtenerPorCuentaYFechas_conClienteExistente_deberiaRetornarLista() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        when(clienteRepo.findById(1L)).thenReturn(Optional.of(cliente));
        List<ReporteDTO> reporte = List.of(new ReporteDTO() {
            @Override
            public LocalDate getFecha() {
                return null;
            }

            @Override
            public String getNombre() {
                return "";
            }

            @Override
            public String getNumeroCuenta() {
                return "";
            }

            @Override
            public String getTipoCuenta() {
                return "";
            }

            @Override
            public BigDecimal getSaldoInicial() {
                return null;
            }

            @Override
            public boolean getEstado() {
                return false;
            }

            @Override
            public BigDecimal getValor() {
                return null;
            }

            @Override
            public BigDecimal getSaldo() {
                return null;
            }
        });

        when(movimientoRepo.findByClienteIdAndFechaBetween(eq(1L), any(), any()))
                .thenReturn(reporte);

        List<ReporteDTO> result = movimientoService.obtenerPorCuentaYFechas(1L, LocalDate.now().minusDays(5), LocalDate.now());
        assertEquals(1, result.size());
    }

    @Test
    void obtenerReporteCuentas_conCuentasYMovimientos_deberiaRetornarReporte() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("0012345678");
        cuenta.setTipoCuenta("AHORROS");
        cuenta.setSaldoInicial(BigDecimal.valueOf(500));

        Movimiento movimiento = Movimiento.builder()
                .numeroCuenta("0012345678")
                .fecha(LocalDate.now())
                .valor(BigDecimal.valueOf(100))
                .saldo(BigDecimal.valueOf(600))
                .tipoMovimiento("DEPOSITO")
                .build();

        when(cuentaRepo.findByClienteId(1L)).thenReturn(List.of(cuenta));
        when(movimientoRepo.findByNumeroCuenta("0012345678")).thenReturn(List.of(movimiento));

        List<CuentaReporteDTO> resultado = movimientoService.obtenerReporteCuentas(1L);

        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getMovimientos().size());
        assertEquals("DEPOSITO", resultado.get(0).getMovimientos().get(0).getTipoMovimiento());
    }

    @Test
    void obtenerReporteCuentas_sinCuentas_deberiaLanzarExcepcion() {
        when(cuentaRepo.findByClienteId(2L)).thenReturn(Collections.emptyList());

        assertThrows(EntityNotFoundException.class, () -> movimientoService.obtenerReporteCuentas(2L));
    }
}
