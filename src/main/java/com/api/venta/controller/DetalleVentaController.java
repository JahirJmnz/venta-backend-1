package com.api.venta.controller;

import com.api.venta.entity.DetalleVenta;
import com.api.venta.entity.Producto;
import com.api.venta.entity.Venta;
import com.api.venta.exception.ResourceNotFoundException;
import com.api.venta.repository.DetalleVentaRepository;
import com.api.venta.repository.ProductoRepository;
import com.api.venta.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DetalleVentaController {
    @Autowired
    private DetalleVentaRepository detalleVentaRepository;
    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/detalleVentas")
    public List<DetalleVenta> obtenerTodosLosDetallesVenta() {
        return detalleVentaRepository.findAll();
    }

    @GetMapping("/detalleVentas/{id}")
    public ResponseEntity<DetalleVenta> buscarDetalleVentaPorId(@PathVariable(value = "id") Long idDetalleVenta) throws ResourceNotFoundException {
        DetalleVenta detalleVenta = detalleVentaRepository.findById(idDetalleVenta)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un detalle de venta para el id ::" + idDetalleVenta));
        return ResponseEntity.ok().body(detalleVenta);
    }

    @PostMapping("/detalleVentas")
    public DetalleVenta agregarDetalleVenta(@RequestBody Map<String, Object> datosDetalleVenta) throws ResourceNotFoundException {
        // Validar que las IDs existan en las tablas correspondientes
        Venta venta = ventaRepository.findById((Long) datosDetalleVenta.get("folio"))
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una venta para el folio :: " + datosDetalleVenta.get("folio")));

        Producto producto = productoRepository.findById((Long) datosDetalleVenta.get("id_producto"))
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un producto para el id :: " + datosDetalleVenta.get("id_producto")));

        // Crear un nuevo detalle de venta
        DetalleVenta nuevoDetalleVenta = new DetalleVenta();
        nuevoDetalleVenta.setFolio(venta);
        nuevoDetalleVenta.setId_producto(producto);
        nuevoDetalleVenta.setCantidad((int) datosDetalleVenta.get("cantidad"));

        // Actualizar el monto de la venta basándose en el nuevo detalle
        double montoTotal = venta.getMonto() + (producto.getPrecio() * nuevoDetalleVenta.getCantidad());
        venta.setMonto(montoTotal);
        ventaRepository.save(venta);

        return detalleVentaRepository.save(nuevoDetalleVenta);
    }

    @PutMapping("/detalleVentas/{id}")
    public ResponseEntity<DetalleVenta> actualizarDetalleVenta(@PathVariable(value = "id") Long idDetalleVenta, @RequestBody DetalleVenta datosDetalleVenta)
            throws ResourceNotFoundException {
        DetalleVenta detalleVenta = detalleVentaRepository.findById(idDetalleVenta)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un detalle de venta para el id :: " + idDetalleVenta));

        // Actualizar los datos del detalle de venta
        detalleVenta.setCantidad(datosDetalleVenta.getCantidad());

        // Actualizar el monto de la venta basándose en los nuevos datos del detalle
        Venta venta = detalleVenta.getFolio();
        double montoTotal = venta.getMonto() - (detalleVenta.getId_producto().getPrecio() * detalleVenta.getCantidad());

        // Actualizar el monto con la nueva cantidad
        venta.setMonto(montoTotal + (datosDetalleVenta.getId_producto().getPrecio() * datosDetalleVenta.getCantidad()));
        ventaRepository.save(venta);

        final DetalleVenta detalleVentaActualizado = detalleVentaRepository.save(detalleVenta);
        return ResponseEntity.ok(detalleVentaActualizado);
    }

    @DeleteMapping("/detalleVentas/{id}")
    public Map<String, Boolean> eliminarDetalleVenta(@PathVariable(value = "id") Long idDetalleVenta) throws ResourceNotFoundException {
        DetalleVenta detalleVenta = detalleVentaRepository.findById(idDetalleVenta)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un detalle de venta para el id :: " + idDetalleVenta));

        // Actualizar el monto de la venta restando el monto del detalle antes de eliminarlo
        Venta venta = detalleVenta.getFolio();
        double montoTotal = venta.getMonto() - (detalleVenta.getId_producto().getPrecio() * detalleVenta.getCantidad());
        venta.setMonto(montoTotal);
        ventaRepository.save(venta);

        detalleVentaRepository.delete(detalleVenta);
        Map<String, Boolean> response = new HashMap<>();
        response.put("eliminado", Boolean.TRUE);
        return response;
    }
}
