package com.code065.alquilervehiculos.service;

import com.code065.alquilervehiculos.dto.RegistroUsuarioDto;
import com.code065.alquilervehiculos.model.*;
import com.code065.alquilervehiculos.repository.ClienteRepository;
import com.code065.alquilervehiculos.repository.RolRepository;
import com.code065.alquilervehiculos.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RegistroService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistroService(UsuarioRepository usuarioRepository,
                           RolRepository rolRepository,
                           ClienteRepository clienteRepository,
                           PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registrarUsuario(RegistroUsuarioDto dto) {

        if (usuarioRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("El username ya existe.");
        }

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El email ya existe.");
        }

        if (clienteRepository.existsByDni(dto.getDni())) {
            throw new IllegalArgumentException("El DNI ya existe.");
        }

        if (!dto.getPassword().equals(dto.getRepetirPassword())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden.");
        }

        // 1. Crear usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setEmail(dto.getEmail());
        usuario.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        usuario.setEnabled(true);

        usuario = usuarioRepository.save(usuario);

        // 2. Asignar rol USER
        Rol rolUser = rolRepository.findByNombre("USER")
                .orElseThrow(() -> new IllegalArgumentException("Rol USER no existe en BD"));

        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setId(new UsuarioRolId(usuario.getIdUsuario(), rolUser.getIdRol()));
        usuarioRol.setUsuario(usuario);
        usuarioRol.setRol(rolUser);

        usuario.getUsuarioRoles().add(usuarioRol);

        // 3. Crear cliente asociado
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setApellidos(dto.getApellidos());
        cliente.setDni(dto.getDni());
        cliente.setTelefono(dto.getTelefono());
        cliente.setUsuario(usuario);

        clienteRepository.save(cliente);
        usuarioRepository.save(usuario);
    }
}