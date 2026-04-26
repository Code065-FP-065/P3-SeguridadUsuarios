package com.code065.alquilervehiculos.repository;

import com.code065.alquilervehiculos.model.Rol;

import java.util.Optional;

public interface UsuarioRepository {

    Optional<Rol> findByUsername(String username);

    Optional<Rol> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);


}
