package com.code065.alquilervehiculos.security;

import com.code065.alquilervehiculos.model.Usuario;
import com.code065.alquilervehiculos.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        Collection<GrantedAuthority> authorities = usuario.getUsuarioRoles()
                .stream()
                .map(usuarioRol -> (GrantedAuthority) new SimpleGrantedAuthority(
                        "ROLE_" + usuarioRol.getRol().getNombre()))
                .collect(Collectors.toList());

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPasswordHash())
                .disabled(!Boolean.TRUE.equals(usuario.getEnabled()))
                .authorities(authorities)
                .build();
    }
}