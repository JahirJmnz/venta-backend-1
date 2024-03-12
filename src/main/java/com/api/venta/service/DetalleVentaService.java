package com.api.venta.service;

import com.api.venta.dto.DetalleVentaDTO;
import com.api.venta.entity.DetalleVenta;
import com.api.venta.entity.Producto;
import com.api.venta.entity.Venta;
import com.api.venta.exception.ResourceNotFoundException;
import com.api.venta.repository.DetalleVentaRepository;
import com.api.venta.repository.ProductoRepository;
import com.api.venta.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleVentaService {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<DetalleVenta> obtenerTodosLosDetalles() {
        return detalleVentaRepository.findAll();
    }

    public DetalleVenta buscarDetalleId(Long id) throws ResourceNotFoundException {
        return detalleVentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un detalle de venta para el id :: " + id));
    }

    public DetalleVenta agregarDetalle(DetalleVentaDTO detalleVentaDTO) throws ResourceNotFoundException {
        DetalleVenta detalleVenta = new DetalleVenta();

        // Obtener la venta
        Venta venta = ventaRepository.findById(detalleVentaDTO.getFolio())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una venta para el folio :: " + detalleVentaDTO.getFolio()));
        detalleVenta.setFolio(venta);

        // Obtener el producto
        Producto producto = productoRepository.findById(detalleVentaDTO.getIdProducto())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un producto para el id :: " + detalleVentaDTO.getIdProducto()));
        detalleVenta.setIdProducto(producto);

        // Establecer la cantidad
        detalleVenta.setCantidad(detalleVentaDTO.getCantidad());

        // Calcular el monto
        double montoDetalle = detalleVenta.getCantidad() * detalleVenta.getIdProducto().getPrecio();

        // Guardar el detalle de venta
        DetalleVenta savedDetalleVenta = detalleVentaRepository.save(detalleVenta);

        // Actualizar el monto de la venta
        venta.setMonto(venta.getMonto() + montoDetalle);
        ventaRepository.save(venta);

        return savedDetalleVenta;
    }


    public DetalleVenta actualizarDetalle(Long id, DetalleVentaDTO detalleVentaDTO) throws ResourceNotFoundException {
        DetalleVenta existingDetalleVenta = detalleVentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un detalle de venta para el id :: " + id));

        // Obtener la venta
        Venta venta = ventaRepository.findById(detalleVentaDTO.getFolio())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una venta para el folio :: " + detalleVentaDTO.getFolio()));
        existingDetalleVenta.setFolio(venta);

        // Obtener el producto
        Producto producto = productoRepository.findById(detalleVentaDTO.getIdProducto())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un producto para el id :: " + detalleVentaDTO.getIdProducto()));
        existingDetalleVenta.setIdProducto(producto);

        // Restar la cantidad anterior al monto de la venta
        double montoDetalleAnterior = existingDetalleVenta.getCantidad() * existingDetalleVenta.getIdProducto().getPrecio();
        venta.setMonto(venta.getMonto() - montoDetalleAnterior);

        // Actualizar las propiedades necesarias según la lógica de tu aplicación
        existingDetalleVenta.setCantidad(detalleVentaDTO.getCantidad());

        // Calcular el monto con la nueva cantidad
        double montoDetalleNuevo = existingDetalleVenta.getCantidad() * existingDetalleVenta.getIdProducto().getPrecio();

        // Sumar el nuevo monto al monto de la venta
        venta.setMonto(venta.getMonto() + montoDetalleNuevo);
        ventaRepository.save(venta);

        // Guardar el detalle de venta
        DetalleVenta savedDetalleVenta = detalleVentaRepository.save(existingDetalleVenta);

        return savedDetalleVenta;
    }



    public void eliminarDetalle(Long id) throws ResourceNotFoundException {
        DetalleVenta detalleVenta = detalleVentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un detalle de venta para el id :: " + id));

        // Restar el monto del detalle de venta de la venta asociada
        Venta venta = detalleVenta.getFolio();
        double montoDetalle = detalleVenta.getCantidad() * detalleVenta.getIdProducto().getPrecio();
        venta.setMonto(venta.getMonto() - montoDetalle);
        ventaRepository.save(venta);

        detalleVentaRepository.delete(detalleVenta);
    }
}
