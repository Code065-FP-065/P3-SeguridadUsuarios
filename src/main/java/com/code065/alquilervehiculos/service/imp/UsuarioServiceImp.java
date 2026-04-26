package com.code065.alquilervehiculos.service.imp;

import com.code065.alquilervehiculos.model.Rol;
import com.code065.alquilervehiculos.model.Usuario;
import com.code065.alquilervehiculos.repository.RolRepository;
import com.code065.alquilervehiculos.repository.UsuarioRepository;
import com.code065.alquilervehiculos.service.UsuarioService;

import java.util.List;
import java.util.Optional;

public class UsuarioServiceImp implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public UsuarioServiceImp(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public boolean existeUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    @Override
    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario asignarRolPorNombre(Usuario usuario, String nombreRol) {
        Rol rol = rolRepository.findByNombre(nombreRol).orElseThrow(() -> new IllegalArgumentException("No existe el rol:" + nombreRol));
        usuario.setRol(rol);
        return usuario;
    }
}
