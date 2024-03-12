package com.api.venta.service;

import com.api.venta.entity.Empleado;
import com.api.venta.exception.ResourceNotFoundException;
import com.api.venta.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

@Service
public class EmpleadoService {
    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoRepository.findAll();
    }

    public Empleado buscarEmpleadoPorId(Long idEmpleado) throws ResourceNotFoundException {
        return empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un empleado para el id :: " + idEmpleado));
    }

    public Empleado agregarEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public Empleado actualizarEmpleado(Long idEmpleado, Empleado datosEmpleado) throws ResourceNotFoundException {
        Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un empleado para el id :: " + idEmpleado));

        empleado.setNombreEmpleado(datosEmpleado.getNombreEmpleado());
        empleado.setApPaterno(datosEmpleado.getApPaterno());
        empleado.setApMaterno(datosEmpleado.getApMaterno());
        empleado.setCurp(datosEmpleado.getCurp());

        return empleadoRepository.save(empleado);
    }

    public Map<String, Boolean> eliminarEmpleado(Long idEmpleado) throws ResourceNotFoundException {
        Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un empleado para el id :: " + idEmpleado));

        empleadoRepository.delete(empleado);
        Map<String, Boolean> response = new HashMap<>();
        response.put("eliminado", Boolean.TRUE);
        return response;
    }
}
