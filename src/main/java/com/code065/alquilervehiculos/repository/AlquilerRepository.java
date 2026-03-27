package com.code065.alquilervehiculos.repository;

import com.code065.alquilervehiculos.model.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlquilerRepository extends JpaRepository<Alquiler, Long> {

    boolean existsByCliente_IdCliente(Long idCliente);

    boolean existsByVehiculo_IdVehiculo(Long idVehiculo);
}
