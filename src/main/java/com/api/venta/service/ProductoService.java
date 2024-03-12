package com.api.venta.service;

import com.api.venta.entity.Producto;
import com.api.venta.exception.ResourceNotFoundException;
import com.api.venta.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    public Producto buscarProductoId(Long id) throws ResourceNotFoundException {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un producto para el id :: " + id));
    }

    public Producto agregarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto actualizarProducto(Long id, Producto datosProducto) throws ResourceNotFoundException {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un producto para el id :: " + id));

        producto.setNombreProducto(datosProducto.getNombreProducto());
        producto.setDescripcion(datosProducto.getDescripcion());
        producto.setPrecio(datosProducto.getPrecio());

        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long id) throws ResourceNotFoundException {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un producto para el id :: " + id));

        productoRepository.delete(producto);
    }
}
