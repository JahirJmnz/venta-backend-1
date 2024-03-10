package com.api.venta.controller;

import com.api.venta.entity.Empleado;
import com.api.venta.entity.Producto;
import com.api.venta.exception.ResourceNotFoundException;
import com.api.venta.repository.EmpleadoRepository;
import com.api.venta.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProductoController {
    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/productos")
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> buscarProductoPorId(@PathVariable(value = "id") Long idProducto) throws ResourceNotFoundException {
        Producto producto = productoRepository.findById(idProducto).orElseThrow(() -> new ResourceNotFoundException("No se encontró un producto para el id ::" + idProducto));
        return ResponseEntity.ok().body(producto);
    }

    @PostMapping("/productos")
    public Producto agregarProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable(value = "id") Long idProducto, @RequestBody Producto datosProducto)
            throws ResourceNotFoundException {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un producto para el id :: " + idProducto));

        producto.setNombre_producto(datosProducto.getNombre_producto());
        producto.setDescripcion(datosProducto.getDescripcion());
        producto.setPrecio(datosProducto.getPrecio());

        final Producto productoActualizado = productoRepository.save(producto);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/productos/{id}")
    public Map<String, Boolean> eliminarProducto(@PathVariable(value = "id") Long idProducto) throws ResourceNotFoundException {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un producto para el id :: " + idProducto));

        productoRepository.delete(producto);
        Map<String, Boolean> response = new HashMap<>();
        response.put("eliminado", Boolean.TRUE);
        return response;
    }
}
