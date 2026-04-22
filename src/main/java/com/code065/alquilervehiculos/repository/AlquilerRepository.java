package com.code065.alquilervehiculos.repository;

import com.code065.alquilervehiculos.model.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlquilerRepository extends JpaRepository<Alquiler, Long> {

    List<Alquiler> findByCliente_IdCliente(Long idCliente);

    List<Alquiler> findByVehiculo_IdVehiculo(Long idVehiculo);

    // 🔐 CLAVE: para seguridad USER
    List<Alquiler> findByCliente_Usuario_Username(String username);

    // útil para ADMIN filtrado
    List<Alquiler> findByCliente_Dni(String dni);
}