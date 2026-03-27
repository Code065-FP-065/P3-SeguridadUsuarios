package com.code065.alquilervehiculos.service.imp;

import com.code065.alquilervehiculos.model.Alquiler;
import com.code065.alquilervehiculos.repository.AlquilerRepository;
import com.code065.alquilervehiculos.service.AlquilerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlquilerServiceImp  implements AlquilerService {

    private final AlquilerRepository alquilerRepository;

    public AlquilerServiceImp(AlquilerRepository alquilerRepository) {
        this.alquilerRepository = alquilerRepository;
    }

    @Override
    public List<Alquiler> listarVehiculos() {
        return alquilerRepository.findAll();
    }

    @Override
    public Optional<Alquiler> buscarVehiculoPorId(Long id) {
        return alquilerRepository.findById(id);
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
