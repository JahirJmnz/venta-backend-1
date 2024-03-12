package com.api.venta.service;

import com.api.venta.entity.Empleado;
import com.api.venta.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    // Obtener todos los empleados
    public List<Empleado> getAllEmpleados() {
        return empleadoRepository.findAll();
    }

    // Obtener empleado por ID
    public Optional<Empleado> getEmpleadoById(Long id) {
        return empleadoRepository.findById(id);
    }

    // Crear un nuevo empleado
    public Empleado createEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    // Actualizar un empleado existente
    public Empleado updateEmpleado(Long id, Empleado empleado) {
        if (empleadoRepository.existsById(id)) {
            empleado.setIdEmpleado(id);
            return empleadoRepository.save(empleado);
        } else {
            // Manejar el caso en que el empleado no exista
            throw new RuntimeException("No se encontró un empleado con el id " + id);
        }
    }

    // Eliminar un empleado por ID
    public String deleteEmpleado(Long id) {
        if (empleadoRepository.existsById(id)) {
            empleadoRepository.deleteById(id);
            return "Empleado eliminado";
        } else {
            // Manejar el caso en que el empleado no exista
            throw new RuntimeException("No se encontró un empleado con el id " + id);
        }
    }
}
