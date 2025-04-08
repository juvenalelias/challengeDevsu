package com.example.cuenta.service;

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
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovimientoServiceImpl implements MovimientoService{
    private final MovimientoRepository movimientoRepo;
    private final CuentaRepository cuentaRepo;
    private final ClienteRepository clienteRepo;

    public MovimientoServiceImpl(MovimientoRepository movimientoRepo, CuentaRepository cuentaRepo, ClienteRepository clienteRepo) {
        this.movimientoRepo = movimientoRepo;
        this.cuentaRepo = cuentaRepo;
        this.clienteRepo = clienteRepo;
    }

    public Optional<Movimiento> getById(Long id) {

        return Optional.ofNullable(movimientoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movimiento no Existe")));
    }

    public Movimiento registrarMovimiento(MovimientoDTO movimiento) {
        Cuenta cuenta = cuentaRepo.findById(movimiento.getNumeroCuenta())
                .orElseThrow(() -> new EntityNotFoundException("Cuenta no válida"));

        BigDecimal saldoActual = cuenta.getSaldoInicial();
        BigDecimal valor = movimiento.getValor();

        BigDecimal nuevoSaldo = saldoActual.add(valor);

        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoInsuficienteException("Saldo no disponible para realizar el retiro");
        }

        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepo.save(cuenta);

        Movimiento newMov = Movimiento.builder()
                                .tipoMovimiento(movimiento.getTipoMovimiento())
                                .fecha(LocalDate.now())
                                .valor(movimiento.getValor())
                                .saldo(nuevoSaldo)
                                .numeroCuenta(movimiento.getNumeroCuenta())
                                .saldoInicial(saldoActual)
                                .build();

        return movimientoRepo.save(newMov);
    }

    public List<ReporteDTO> obtenerPorCuentaYFechas(Long clienteId, LocalDate desde, LocalDate hasta) {

        Cliente cliente = clienteRepo.findById(clienteId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no válido"));

        return movimientoRepo.findByClienteIdAndFechaBetween(cliente.getId(), desde, hasta);
    }

    public List<CuentaReporteDTO> obtenerReporteCuentas(Long id) {
        List<Cuenta> cuentas = cuentaRepo.findByClienteId(id);

        if (!cuentas.isEmpty()){
            return cuentas.stream().map(cuenta -> {
                CuentaReporteDTO dto = new CuentaReporteDTO();
                dto.setNumeroCuenta(cuenta.getNumeroCuenta());
                dto.setTipoCuenta(cuenta.getTipoCuenta());
                dto.setSaldo(cuenta.getSaldoInicial());

                List<RepoMovimientoDTO> movimientos = movimientoRepo
                        .findByNumeroCuenta(cuenta.getNumeroCuenta())
                        .stream()
                        .map(mov -> {
                            RepoMovimientoDTO mDto = new RepoMovimientoDTO();
                            mDto.setFecha(mov.getFecha());
                            mDto.setTipoMovimiento(mov.getTipoMovimiento());
                            mDto.setValor(mov.getValor());
                            mDto.setSaldo(mov.getSaldo());
                            return mDto;
                        })
                        .collect(Collectors.toList());

                dto.setMovimientos(movimientos);
                return dto;
            }).collect(Collectors.toList());
        }else {
            throw new EntityNotFoundException("Cliente no reporta cuentas");
        }
    }
}
