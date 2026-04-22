package com.code065.alquilervehiculos.service.impl;

import com.code065.alquilervehiculos.model.Alquiler;
import com.code065.alquilervehiculos.repository.AlquilerRepository;
import com.code065.alquilervehiculos.service.AlquilerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlquilerServiceImpl implements AlquilerService {

    private final AlquilerRepository alquilerRepository;

    public AlquilerServiceImpl(AlquilerRepository alquilerRepository) {
        this.alquilerRepository = alquilerRepository;
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
    public List<Alquiler> buscarPorCliente(Long idCliente) {
        return alquilerRepository.findByCliente_IdCliente(idCliente);
    }

    @Override
    public List<Alquiler> buscarPorUsername(String username) {
        return alquilerRepository.findByCliente_Usuario_Username(username);
    }

    @Override
    public Alquiler guardarAlquiler(Alquiler alquiler) {
        return alquilerRepository.save(alquiler);
    }

    @Override
    public void eliminarAlquiler(Long id) {
        alquilerRepository.deleteById(id);
    }
}