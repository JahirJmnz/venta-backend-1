package com.api.venta.service;

import com.api.venta.entity.Producto;
import com.api.venta.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Obtener todos los productos
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    // Obtener producto por ID
    public Optional<Producto> getProductoById(Long id) {
        return productoRepository.findById(id);
    }

    // Crear un nuevo producto
    public Producto createProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    // Actualizar un producto existente
    public Producto updateProducto(Long id, Producto producto) {
        if (productoRepository.existsById(id)) {
            producto.setId_producto(id);
            return productoRepository.save(producto);
        } else {
            // Manejar el caso en que el producto no exista
            throw new RuntimeException("No se encontró un producto con el id " + id);
        }
    }

    // Eliminar un producto por ID
    public String deleteProducto(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return "Producto eliminado";
        } else {
            // Manejar el caso en que el producto no exista
            throw new RuntimeException("No se encontró un producto con el id " + id);
        }
    }
}
