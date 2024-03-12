package com.api.venta.controller;

import com.api.venta.dto.DetalleVentaDTO;
import com.api.venta.entity.DetalleVenta;
import com.api.venta.exception.ResourceNotFoundException;
import com.api.venta.service.DetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/detalles-venta")
public class DetalleVentaController {

    @Autowired
    private DetalleVentaService detalleVentaService;

    @GetMapping
    public List<DetalleVenta> obtenerTodosLosDetalles() {
        return detalleVentaService.obtenerTodosLosDetalles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleVenta> buscarDetalleId(@PathVariable Long id) throws ResourceNotFoundException {
        DetalleVenta detalleVenta = detalleVentaService.buscarDetalleId(id);
        return ResponseEntity.ok().body(detalleVenta);
    }

    @PostMapping
    public DetalleVenta agregarDetalle(@RequestBody DetalleVentaDTO detalleVentaDTO) throws ResourceNotFoundException {
        return detalleVentaService.agregarDetalle(detalleVentaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleVenta> actualizarDetalle(@PathVariable Long id, @RequestBody DetalleVentaDTO detalleVentaDTO)
            throws ResourceNotFoundException {
        DetalleVenta updatedDetalleVenta = detalleVentaService.actualizarDetalle(id, detalleVentaDTO);
        return ResponseEntity.ok(updatedDetalleVenta);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> eliminarDetalle(@PathVariable Long id) throws ResourceNotFoundException {
        detalleVentaService.eliminarDetalle(id);
        Map<String, Boolean> response = Map.of("eliminado", true);
        return response;
    }
}
