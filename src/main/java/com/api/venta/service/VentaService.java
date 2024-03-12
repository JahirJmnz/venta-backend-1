package com.api.venta.service;

import com.api.venta.dto.VentaRequestDTO;
import com.api.venta.entity.Empleado;
import com.api.venta.entity.Venta;
import com.api.venta.exception.ResourceNotFoundException;
import com.api.venta.repository.EmpleadoRepository;
import com.api.venta.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Venta> obtenerTodasLasVentas() {
        return ventaRepository.findAll();
    }

    public Venta buscarVentaId(Long folio) throws ResourceNotFoundException {
        return ventaRepository.findById(folio)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una venta para el folio :: " + folio));
    }

    public Venta agregarVenta(VentaRequestDTO ventaRequestDTO) throws ResourceNotFoundException {
        Empleado empleado = empleadoRepository.findById(ventaRequestDTO.getIdEmpleado())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un empleado para el id :: " + ventaRequestDTO.getIdEmpleado()));

        Venta venta = new Venta();
        venta.setFechaVenta(LocalDateTime.now());
        venta.setMonto(0.0); // Puedes establecer el monto en otro momento según tu lógica de negocio
        venta.setIdEmpleado(empleado);

        return ventaRepository.save(venta);
    }

    public Map<String, Boolean> actualizarVenta(Long folio, VentaRequestDTO ventaRequestDTO)
            throws ResourceNotFoundException {
        Venta ventaActual = ventaRepository.findById(folio)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una venta para el folio :: " + folio));

        ventaActual.setFechaVenta(LocalDateTime.now());

        Long idEmpleado = ventaRequestDTO.getIdEmpleado();
        Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un empleado para el id :: " + idEmpleado));

        ventaActual.setIdEmpleado(empleado);

        ventaRepository.save(ventaActual);

        // Devuelve un Map indicando que la actualización fue exitosa
        Map<String, Boolean> response = new HashMap<>();
        response.put("actualizado", true);
        return response;
    }



    public void eliminarVenta(Long folio) throws ResourceNotFoundException {
        Venta venta = ventaRepository.findById(folio)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una venta para el folio :: " + folio));

        ventaRepository.delete(venta);
    }
}
