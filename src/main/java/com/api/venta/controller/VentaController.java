package com.api.venta.controller;

import com.api.venta.entity.Empleado;
import com.api.venta.entity.Venta;
import com.api.venta.exception.ResourceNotFoundException;
import com.api.venta.repository.EmpleadoRepository;
import com.api.venta.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class VentaController {
    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private EmpleadoRepository empleadoRepository;

    @GetMapping("/ventas")
    public List<Venta> obtenerTodosLasVentas() {
        return ventaRepository.findAll();
    }

    @GetMapping("/ventas/{id}")
    public ResponseEntity<Venta> buscarVentaPorId(@PathVariable(value = "id") Long idVenta) throws ResourceNotFoundException {
        Venta venta = ventaRepository.findById(idVenta).orElseThrow(() -> new ResourceNotFoundException("No se encontró una venta para el id ::" + idVenta));
        return ResponseEntity.ok().body(venta);
    }

    @PostMapping("/ventas")
    public Venta agregarVenta(@RequestBody Map<String, Long> datosVenta) throws ResourceNotFoundException {
        // Validar que las IDs existan en las tablas correspondientes
        Empleado empleado = empleadoRepository.findById(datosVenta.get("id_empleado"))
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un empleado para el id :: " + datosVenta.get("id_empleado")));

        // Crear una nueva venta
        Venta nuevaVenta = new Venta();
        nuevaVenta.setFechaVenta(LocalDateTime.now());
        nuevaVenta.setId_empleado(empleado);

        // El monto se calculará automáticamente en base a los detalles de venta
        return ventaRepository.save(nuevaVenta);
    }

    @PutMapping("/ventas/{folio}")
    public ResponseEntity<Venta> actualizarVenta(@PathVariable(value = "folio") Long folioVenta, @RequestBody Map<String, Object> datosVenta)
            throws ResourceNotFoundException {
        Venta venta = ventaRepository.findById(folioVenta)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una venta para el folio :: " + folioVenta));

        // Actualizar los datos de la venta
        venta.setMonto((Double) datosVenta.get("monto"));

        // Si se proporciona un nuevo ID de empleado, actualizar el empleado asociado
        if (datosVenta.containsKey("id_empleado")) {
            Long idEmpleado = ((Number) datosVenta.get("id_empleado")).longValue();
            Empleado nuevoEmpleado = empleadoRepository.findById(idEmpleado)
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró un empleado para el id :: " + idEmpleado));
            venta.setId_empleado(nuevoEmpleado);
        }

        final Venta ventaActualizada = ventaRepository.save(venta);
        return ResponseEntity.ok(ventaActualizada);
    }


    @DeleteMapping("/ventas/{folio}")
    public Map<String, Boolean> eliminarVenta(@PathVariable(value = "folio") Long folioVenta) throws ResourceNotFoundException {
        Venta venta = ventaRepository.findById(folioVenta)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una venta para el folio :: " + folioVenta));

        ventaRepository.delete(venta);
        Map<String, Boolean> response = new HashMap<>();
        response.put("eliminado", Boolean.TRUE);
        return response;
    }

}
