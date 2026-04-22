package com.code065.alquilervehiculos.repository;

import com.code065.alquilervehiculos.model.UsuarioRol;
import com.code065.alquilervehiculos.model.UsuarioRolId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, UsuarioRolId> {
}