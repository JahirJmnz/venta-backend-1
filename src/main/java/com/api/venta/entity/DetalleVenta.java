package com.api.venta.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "detalleVenta")
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_detalle")
    private Long idDetalle;
    @ManyToOne
    @JoinColumn(name = "folio")
    private Venta folio;
    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto idProducto;
    @Column(name = "cantidad")
    private int cantidad;

    public Long getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Long idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Venta getFolio() {
        return folio;
    }

    public void setFolio(Venta folio) {
        this.folio = folio;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
