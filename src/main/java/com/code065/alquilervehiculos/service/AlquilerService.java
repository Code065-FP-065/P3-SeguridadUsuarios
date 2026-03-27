package com.code065.alquilervehiculos.service;

import com.code065.alquilervehiculos.model.Alquiler;

import java.util.List;
import java.util.Optional;

public interface AlquilerService {

    List<Alquiler> listarVehiculos();

    Optional<Alquiler> buscarVehiculoPorId(Long id);

    Alquiler guardarAlquiler(Alquiler alquiler);

    void eliminarAlquiler(Long id);
}
