package com.api.venta.controller;

import com.api.venta.entity.Empleado;
import com.api.venta.exception.ResourceNotFoundException;
import com.api.venta.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {
    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoService.obtenerTodosLosEmpleados();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> buscarEmpleadoPorId(@PathVariable(value = "id") Long idEmpleado) throws ResourceNotFoundException {
        Empleado empleado = empleadoService.buscarEmpleadoPorId(idEmpleado);
        return ResponseEntity.ok().body(empleado);
    }

    @PostMapping
    public Empleado agregarEmpleado(@RequestBody Empleado empleado) {
        return empleadoService.agregarEmpleado(empleado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable(value = "id") Long idEmpleado, @RequestBody Empleado datosEmpleado)
            throws ResourceNotFoundException {
        Empleado empleadoActualizado = empleadoService.actualizarEmpleado(idEmpleado, datosEmpleado);
        return ResponseEntity.ok(empleadoActualizado);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> eliminarEmpleado(@PathVariable(value = "id") Long idEmpleado) throws ResourceNotFoundException {
        return empleadoService.eliminarEmpleado(idEmpleado);
    }
}
