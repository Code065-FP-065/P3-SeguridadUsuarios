package com.code065.alquilervehiculos.service.imp;

import com.code065.alquilervehiculos.model.Alquiler;
import com.code065.alquilervehiculos.model.Usuario;
import com.code065.alquilervehiculos.repository.AlquilerRepository;
import com.code065.alquilervehiculos.repository.UsuarioRepository;
import com.code065.alquilervehiculos.service.AlquilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AlquilerServiceImp implements AlquilerService {

    private final AlquilerRepository alquilerRepository;
    private final UsuarioRepository usuarioRepository;

    public AlquilerServiceImp(AlquilerRepository alquilerRepository, UsuarioRepository usuarioRepository) {
        this.alquilerRepository = alquilerRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public void registrarNuevoAlquiler(Alquiler alquiler) {
        // Obtenemos el nombre del usuario de la sesión actual de Spring Security
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario operador = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        alquiler.setUsuario(operador);
        alquilerRepository.save(alquiler);
    }

    @Override
    public List<Alquiler> listarAlquileres() {
        return alquilerRepository.findAll();
    }

    @Override
    public Optional<Alquiler> buscarAlquilerPorId(Long id) {
        return alquilerRepository.findById(id);
    }

    @Override
    public Alquiler guardarAlquiler(Alquiler alquiler) {
        return alquilerRepository.save(alquiler);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void eliminarAlquiler(Long id) {
        alquilerRepository.deleteById(id);
    }
}