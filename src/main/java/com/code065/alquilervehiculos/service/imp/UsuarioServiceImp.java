package com.code065.alquilervehiculos.service.imp;

import com.code065.alquilervehiculos.model.Rol;
import com.code065.alquilervehiculos.model.Usuario;
import com.code065.alquilervehiculos.repository.RolRepository;
import com.code065.alquilervehiculos.repository.UsuarioRepository;
import com.code065.alquilervehiculos.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UsuarioServiceImp implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;

    public UsuarioServiceImp(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolRepository = rolRepository;
    }

    @Override
    @Transactional
    public void registrarNuevoUsuario(Usuario usuario) {

        usuario.setPasswordHash(passwordEncoder.encode(usuario.getPasswordHash()));

        usuario.setEnabled(true);

        usuario.setCreatedAt(LocalDateTime.now());

        Rol userRol = rolRepository.findByNombre("USER")
                .orElseThrow(() -> new RuntimeException("Error: Rol USER no encontrado"));
        usuario.getRoles().add(userRol);

        usuarioRepository.save(usuario);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void activarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        usuario.setEnabled(true);
        usuarioRepository.save(usuario);
    }
}