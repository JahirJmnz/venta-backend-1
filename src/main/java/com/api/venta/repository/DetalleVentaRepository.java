package com.api.venta.repository;

import com.api.venta.entity.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {

    // Agrega la consulta para calcular el monto total de los detalles de una venta
    @Query("SELECT COALESCE(SUM(d.cantidad * p.precio), 0) FROM DetalleVenta d JOIN d.idProducto p WHERE d.folio.folio = :folio")
    double calcularMontoTotal(@Param("folio") Long folio);


}
