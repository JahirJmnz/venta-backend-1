package com.api.venta.controller;

import com.api.venta.dto.VentaRequestDTO;
import com.api.venta.entity.Venta;
import com.api.venta.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    // Realizar una venta
    @PostMapping
    public ResponseEntity<Venta> realizarVenta(@RequestBody VentaRequestDTO ventaRequestDTO) {
        try {
            Venta venta = ventaService.realizarVenta(ventaRequestDTO.getIdEmpleado());
            return new ResponseEntity<>(venta, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Obtener todas las ventas
    @GetMapping
    public ResponseEntity<List<Venta>> getAllVentas() {
        List<Venta> ventas = ventaService.getAllVentas();
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }

    // Obtener venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Venta> getVentaById(@PathVariable Long id) {
        Optional<Venta> venta = ventaService.getVentaById(id);
        return venta.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Actualizar una venta existente
    @PutMapping("/{id}")
    public ResponseEntity<Venta> updateVenta(@PathVariable Long id, @RequestBody Venta venta) {
        try {
            Venta ventaActualizada = ventaService.updateVenta(id, venta);
            return new ResponseEntity<>(ventaActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar una venta por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVenta(@PathVariable Long id) {
        try {
            String mensaje = ventaService.deleteVenta(id);
            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
