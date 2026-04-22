package com.code065.alquilervehiculos.repository;

import com.code065.alquilervehiculos.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByDni(String dni);

    boolean existsByDni(String dni);

    Optional<Cliente> findByUsuario_Username(String username);

    Optional<Cliente> findByUsuario_IdUsuario(Long idUsuario);
}