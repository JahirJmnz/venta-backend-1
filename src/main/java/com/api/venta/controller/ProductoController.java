package com.api.venta.controller;

import com.api.venta.entity.Producto;
import com.api.venta.exception.ResourceNotFoundException;
import com.api.venta.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> obtenerTodosLosProductos() {
        return productoService.obtenerTodosLosProductos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarProductoId(@PathVariable Long id) throws ResourceNotFoundException {
        Producto producto = productoService.buscarProductoId(id);
        return ResponseEntity.ok().body(producto);
    }

    @PostMapping
    public Producto agregarProducto(@RequestBody Producto producto) {
        return productoService.agregarProducto(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto datosProducto)
            throws ResourceNotFoundException {
        Producto producto = productoService.actualizarProducto(id, datosProducto);
        return ResponseEntity.ok(producto);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> eliminarProducto(@PathVariable Long id) throws ResourceNotFoundException {
        productoService.eliminarProducto(id);
        Map<String, Boolean> response = Map.of("eliminado", true);
        return response;
    }
}
