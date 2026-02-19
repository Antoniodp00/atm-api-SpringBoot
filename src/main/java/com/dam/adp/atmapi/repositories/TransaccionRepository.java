package com.dam.adp.atmapi.repositories;

import com.dam.adp.atmapi.models.Transaccion;
import com.dam.adp.atmapi.models.enums.TipoTransaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    List<Transaccion> findByAtmIdOrderByFechaHoraDesc(String cajeroId);

    long countByAtmId(String cajeroId);

    @Query("SELECT COALESCE(SUM(t.monto), 0.0) FROM Transaccion t WHERE t.atm.id = :cajeroId AND t.tipo = :tipo")
    Double sumarMontoPorCajeroYTipo(@Param("cajeroId") String cajeroId, @Param("tipo") TipoTransaccion tipo);
}
