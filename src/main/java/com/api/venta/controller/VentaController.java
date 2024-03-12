package com.api.venta.controller;

import com.api.venta.dto.VentaRequestDTO;
import com.api.venta.entity.Venta;
import com.api.venta.exception.ResourceNotFoundException;
import com.api.venta.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public List<Venta> obtenerTodasLasVentas() {
        return ventaService.obtenerTodasLasVentas();
    }

    @GetMapping("/{folio}")
    public ResponseEntity<Venta> buscarVentaId(@PathVariable Long folio) throws ResourceNotFoundException {
        Venta venta = ventaService.buscarVentaId(folio);
        return ResponseEntity.ok().body(venta);
    }

    @PostMapping
    public Venta agregarVenta(@RequestBody VentaRequestDTO ventaRequestDTO) throws ResourceNotFoundException {
        return ventaService.agregarVenta(ventaRequestDTO);
    }

    @PutMapping("/{folio}")
    public ResponseEntity<Map<String, Boolean>> actualizarVenta(@PathVariable Long folio, @RequestBody VentaRequestDTO ventaRequestDTO)
            throws ResourceNotFoundException {
        Map<String, Boolean> ventaActualizada = ventaService.actualizarVenta(folio, ventaRequestDTO);
        return ResponseEntity.ok(ventaActualizada);
    }


    @DeleteMapping("/{folio}")
    public Map<String, Boolean> eliminarVenta(@PathVariable Long folio) throws ResourceNotFoundException {
        ventaService.eliminarVenta(folio);
        Map<String, Boolean> response = Map.of("eliminado", true);
        return response;
    }
}
