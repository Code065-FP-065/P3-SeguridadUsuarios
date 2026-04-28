package com.code065.alquilervehiculos.service;

import com.code065.alquilervehiculos.model.Usuario;

public interface UsuarioService {

    void registrarNuevoUsuario(Usuario usuario);
    void activarUsuario(Long id);
}