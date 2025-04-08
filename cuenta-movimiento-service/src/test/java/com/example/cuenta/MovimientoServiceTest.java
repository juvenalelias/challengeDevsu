package com.example.cuenta;

import com.example.cuenta.dto.MovimientoDTO;
import com.example.cuenta.entity.Cuenta;
import com.example.cuenta.entity.Movimiento;
import com.example.cuenta.repository.ClienteRepository;
import com.example.cuenta.repository.CuentaRepository;
import com.example.cuenta.repository.MovimientoRepository;
import com.example.cuenta.service.MovimientoService;
import com.example.cuenta.service.MovimientoServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovimientoServiceTest {

    @Test
    void testRegistrarDeposito() {
        CuentaRepository cuentaRepo = Mockito.mock(CuentaRepository.class);
        MovimientoRepository movimientoRepo = Mockito.mock(MovimientoRepository.class);
        ClienteRepository clienteRepo = Mockito.mock(ClienteRepository.class);

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("123");
        cuenta.setSaldoInicial(BigDecimal.valueOf(100));

        Mockito.when(cuentaRepo.findById("123")).thenReturn(Optional.of(cuenta));
        Mockito.when(cuentaRepo.save(Mockito.any())).thenReturn(cuenta);
        Mockito.when(movimientoRepo.save(Mockito.any())).thenAnswer(i -> i.getArgument(0));

        MovimientoServiceImpl service = new MovimientoServiceImpl(movimientoRepo, cuentaRepo, clienteRepo);

        MovimientoDTO mov = new MovimientoDTO();
        mov.setNumeroCuenta("123");
        mov.setTipoMovimiento("Deposito");
        mov.setValor(BigDecimal.valueOf(50));

        Movimiento result = service.registrarMovimiento(mov);
        assertEquals(BigDecimal.valueOf(150), result.getSaldo());
    }
}
