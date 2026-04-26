package com.code065.alquilervehiculos.controller.api;

import com.code065.alquilervehiculos.model.Alquiler;
import com.code065.alquilervehiculos.repository.AlquilerRepository;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/alquileres")
public class AlquilerRestController {

    private final AlquilerRepository alquilerRepository;

    public AlquilerRestController(AlquilerRepository alquilerRepository) {
        this.alquilerRepository = alquilerRepository;
    }

    @GetMapping
    public List<AlquilerDto> listarAlquileres() {
        return alquilerRepository.findAll()
                .stream()
                .map(AlquilerDto::from)
                .toList();
    }

    public record AlquilerDto(
            Long idAlquiler,
            String cliente,
            String vehiculo,
            LocalDate fechaInicio,
            LocalDate fechaFin,
            Integer dias,
            BigDecimal precioDiaAplicado,
            BigDecimal total,
            String estado
    ) {
        public static AlquilerDto from(Alquiler alquiler) {
            String nombreCliente = alquiler.getCliente().getNombre() + " " + alquiler.getCliente().getApellidos();
            String datosVehiculo = alquiler.getVehiculo().getMatricula() + " - " + alquiler.getVehiculo().getModelo();

            return new AlquilerDto(
                    alquiler.getIdAlquiler(),
                    nombreCliente,
                    datosVehiculo,
                    alquiler.getFechaInicio(),
                    alquiler.getFechaFin(),
                    alquiler.getDias(),
                    alquiler.getPrecioDiaAplicado(),
                    alquiler.getTotal(),
                    alquiler.getEstado().name()
            );
        }
    }
}