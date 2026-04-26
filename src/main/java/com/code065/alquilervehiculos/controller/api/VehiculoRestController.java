package com.code065.alquilervehiculos.controller.api;

import com.code065.alquilervehiculos.model.Vehiculo;
import com.code065.alquilervehiculos.repository.VehiculoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoRestController {

    private final VehiculoRepository vehiculoRepository;

    public VehiculoRestController(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    @GetMapping
    public List<Vehiculo> listarVehiculos() {
        return vehiculoRepository.findAll();
    }
}