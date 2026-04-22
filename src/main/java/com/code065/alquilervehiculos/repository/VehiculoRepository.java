package com.code065.alquilervehiculos.repository;

import com.code065.alquilervehiculos.model.Vehiculo;
import com.code065.alquilervehiculos.model.EstadoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    List<Vehiculo> findByEstado(EstadoVehiculo estado);

    boolean existsByMatricula(String matricula);
}