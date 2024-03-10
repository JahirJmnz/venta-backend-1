package com.api.venta.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "detalleVenta")
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_detalle")
    private String id_detalle;
    @ManyToOne
    @JoinColumn(name = "folio")
    private Venta folio;
    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto id_producto;
    @Column(name = "cantidad")
    private int cantidad;

    public String getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(String id_detalle) {
        this.id_detalle = id_detalle;
    }

    public Venta getFolio() {
        return folio;
    }

    public void setFolio(Venta folio) {
        this.folio = folio;
    }

    public Producto getId_producto() {
        return id_producto;
    }

    public void setId_producto(Producto id_producto) {
        this.id_producto = id_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
