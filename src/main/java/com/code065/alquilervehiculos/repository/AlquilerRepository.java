package com.code065.alquilervehiculos.repository;

import com.code065.alquilervehiculos.model.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlquilerRepository extends JpaRepository<Alquiler, Long> {

    boolean existsByCliente_IdCliente(Long idCliente);

    boolean existsByVehiculo_IdVehiculo(Long idVehiculo);

    @Query("SELECT a FROM Alquiler a WHERE a.cliente.usuario.username = :un")
    List<Alquiler> findByUsername(@Param("un") String username);
}
