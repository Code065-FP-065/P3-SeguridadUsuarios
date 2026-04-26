package com.code065.alquilervehiculos.service;

import com.code065.alquilervehiculos.model.Usuario;

import java.util.Optional;

public interface UsuarioService {
    Optional<Usuario> buscarPorUsername(String username);

    boolean existePorUsername(String username);

    Usuario guardar(Usuario usuario);
}
