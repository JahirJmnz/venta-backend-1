package com.api.venta.service;

import com.api.venta.dto.DetalleVentaDTO;
import com.api.venta.entity.DetalleVenta;
import com.api.venta.entity.Producto;
import com.api.venta.entity.Venta;
import com.api.venta.repository.DetalleVentaRepository;
import com.api.venta.repository.ProductoRepository;
import com.api.venta.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleVentaService {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public DetalleVenta crearDetalleVenta(DetalleVentaDTO detalleVentaDTO) {
        try {
            Optional<Venta> ventaOptional = ventaRepository.findById(detalleVentaDTO.getFolio());
            Optional<Producto> productoOptional = productoRepository.findById(detalleVentaDTO.getIdProducto());

            if (ventaOptional.isPresent() && productoOptional.isPresent()) {
                Venta venta = ventaOptional.get();
                Producto producto = productoOptional.get();

                DetalleVenta detalleVenta = new DetalleVenta();
                detalleVenta.setFolio(venta);
                detalleVenta.setIdProducto(producto);
                detalleVenta.setCantidad(detalleVentaDTO.getCantidad());

                // Calcular monto y actualizar la venta
                double montoDetalle = producto.getPrecio() * detalleVentaDTO.getCantidad();
                venta.setMonto(venta.getMonto() + montoDetalle);
                ventaRepository.save(venta);

                // Guardar el detalle de la venta
                return detalleVentaRepository.save(detalleVenta);
            } else {
                throw new RuntimeException("No se encontró la venta o el producto correspondiente.");
            }
        } catch (Exception e) {
            // Puedes agregar logs aquí para depuración
            throw new RuntimeException("Error al procesar la solicitud: " + e.getMessage());
        }
    }


    public List<DetalleVenta> getAllDetallesVenta() {
        return detalleVentaRepository.findAll();
    }

    public Optional<DetalleVenta> getDetalleVentaById(Long id) {
        return detalleVentaRepository.findById(id);
    }

    public DetalleVenta updateDetalleVenta(Long id, DetalleVenta detalleVenta) {
        if (detalleVentaRepository.existsById(id)) {
            detalleVenta.setIdDetalle(id);
            return detalleVentaRepository.save(detalleVenta);
        } else {
            throw new RuntimeException("No se encontró un detalle de venta con el id " + id);
        }
    }

    public String deleteDetalleVenta(Long id) {
        if (detalleVentaRepository.existsById(id)) {
            detalleVentaRepository.deleteById(id);
            return "Detalle de venta eliminado";
        } else {
            throw new RuntimeException("No se encontró un detalle de venta con el id " + id);
        }
    }
}

