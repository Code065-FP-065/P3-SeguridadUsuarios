package com.code065.alquilervehiculos.service;

import com.code065.alquilervehiculos.model.Rol;
import com.code065.alquilervehiculos.model.Usuario;
import com.code065.alquilervehiculos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Usamos el Repository (y su Query implícita) para buscar al usuario
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // 2. Traducimos nuestro Usuario a algo que Spring Security entienda
        // Aquí es donde Spring leerá automáticamente tu campo 'enabled'
        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPasswordHash()) // La contraseña cifrada
                .disabled(!usuario.isEnabled())     // ¡Aquí se hace la magia!
                .authorities(mapRolesToAuthorities(usuario.getRoles()))
                .build();
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Rol> roles) {
        return roles.stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getNombre()))
                .collect(Collectors.toList());
    }
}