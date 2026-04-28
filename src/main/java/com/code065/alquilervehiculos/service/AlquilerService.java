package com.code065.alquilervehiculos.service;

import com.code065.alquilervehiculos.model.Alquiler;
import com.code065.alquilervehiculos.model.Usuario;
import com.code065.alquilervehiculos.repository.AlquilerRepository;
import com.code065.alquilervehiculos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
public interface AlquilerService {

    List<Alquiler> listarAlquileres();

    Optional<Alquiler> buscarAlquilerPorId(Long id);

    Alquiler guardarAlquiler(Alquiler alquiler);

    void eliminarAlquiler(Long id);

    void registrarNuevoAlquiler(Alquiler alquiler);
}
