package com.example.cuenta.controller;

import com.example.cuenta.entity.Cuenta;
import com.example.cuenta.service.CuentaService;
//import com.example.cuenta.service.CuentaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private final CuentaService service;

    public CuentaController(CuentaService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> getByNumeroCuenta(@PathVariable String id) {
        return service.getByNumeroCuenta(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Cuenta> getAll() {
        return service.findAll();
    }

    @PostMapping
    public Cuenta create(@RequestBody Cuenta cuenta) {
        return service.save(cuenta);
    }

    @PutMapping
    public ResponseEntity<Cuenta> update(@RequestBody Cuenta updatedCuenta) { //@PathVariable String id,

        return ResponseEntity.ok(service.updateAccount(updatedCuenta));
    }

    @DeleteMapping("/{numero}")
    public void delete(@PathVariable String numero) {
        service.delete(numero);
    }
}
