package com.api.venta.controller;

import com.api.venta.dto.DetalleVentaDTO;
import com.api.venta.entity.DetalleVenta;
import com.api.venta.service.DetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/detalle-ventas")
public class DetalleVentaController {

    @Autowired
    private DetalleVentaService detalleVentaService;

    @PostMapping
    public ResponseEntity<?> crearDetalleVenta(@RequestBody DetalleVentaDTO detalleVentaDTO) {
        try {
            DetalleVenta detalleVenta = detalleVentaService.crearDetalleVenta(detalleVentaDTO);
            return new ResponseEntity<>(detalleVenta, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error al procesar la solicitud: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<DetalleVenta>> getAllDetallesVenta() {
        List<DetalleVenta> detallesVenta = detalleVentaService.getAllDetallesVenta();
        return new ResponseEntity<>(detallesVenta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleVenta> getDetalleVentaById(@PathVariable Long id) {
        Optional<DetalleVenta> detalleVenta = detalleVentaService.getDetalleVentaById(id);
        return detalleVenta.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleVenta> updateDetalleVenta(@PathVariable Long id, @RequestBody DetalleVenta detalleVenta) {
        try {
            DetalleVenta detalleVentaActualizado = detalleVentaService.updateDetalleVenta(id, detalleVenta);
            return new ResponseEntity<>(detalleVentaActualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDetalleVenta(@PathVariable Long id) {
        try {
            String mensaje = detalleVentaService.deleteDetalleVenta(id);
            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

