package com.api.venta.service;

import com.api.venta.entity.Empleado;
import com.api.venta.entity.Venta;
import com.api.venta.repository.EmpleadoRepository;
import com.api.venta.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    // Realizar una venta
    public Venta realizarVenta(Long idEmpleado) {
        Optional<Empleado> empleadoOptional = empleadoRepository.findById(idEmpleado);

        if (empleadoOptional.isPresent()) {
            Empleado empleado = empleadoOptional.get();

            Venta venta = new Venta();
            venta.setFechaVenta(LocalDateTime.now());
            venta.setIdEmpleado(empleado);

            // Aquí puedes realizar lógica para calcular el monto, si es necesario
            // venta.setMonto(...);

            return ventaRepository.save(venta);
        } else {
            // Manejar el caso en que el empleado no exista
            throw new RuntimeException("No se encontró un empleado con el id " + idEmpleado);
        }
    }

    // Obtener todas las ventas
    public List<Venta> getAllVentas() {
        return ventaRepository.findAll();
    }

    // Obtener venta por ID
    public Optional<Venta> getVentaById(Long id) {
        return ventaRepository.findById(id);
    }

    // Actualizar una venta existente
    public Venta updateVenta(Long id, Venta venta) {
        if (ventaRepository.existsById(id)) {
            venta.setFolio(id);
            return ventaRepository.save(venta);
        } else {
            // Manejar el caso en que la venta no exista
            throw new RuntimeException("No se encontró una venta con el folio " + id);
        }
    }

    // Eliminar una venta por ID
    public String deleteVenta(Long id) {
        if (ventaRepository.existsById(id)) {
            ventaRepository.deleteById(id);
            return "Venta eliminada";
        } else {
            // Manejar el caso en que la venta no exista
            throw new RuntimeException("No se encontró una venta con el folio " + id);
        }
    }
}
