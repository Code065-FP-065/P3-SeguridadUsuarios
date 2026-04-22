package com.code065.alquilervehiculos.controller.web;

import com.code065.alquilervehiculos.model.Alquiler;
import com.code065.alquilervehiculos.model.Cliente;
import com.code065.alquilervehiculos.model.EstadoAlquiler;
import com.code065.alquilervehiculos.model.Vehiculo;
import com.code065.alquilervehiculos.service.AlquilerService;
import com.code065.alquilervehiculos.service.ClienteService;
import com.code065.alquilervehiculos.service.VehiculoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserAlquilerViewController {

    private final ClienteService clienteService;
    private final AlquilerService alquilerService;
    private final VehiculoService vehiculoService;

    public UserAlquilerViewController(
            ClienteService clienteService,
            AlquilerService alquilerService,
            VehiculoService vehiculoService
    ) {
        this.clienteService = clienteService;
        this.alquilerService = alquilerService;
        this.vehiculoService = vehiculoService;
    }

    @GetMapping("/mis-alquileres")
    public String verMisAlquileres(Principal principal, Model model) {
        List<Alquiler> alquileres = alquilerService.buscarPorUsername(principal.getName());

        model.addAttribute("paginaActiva", "mis-alquileres");
        model.addAttribute("alquileres", alquileres);
        return "user/alquileres/lista";
    }

    @GetMapping("/alquileres/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("paginaActiva", "nuevo-alquiler");
        model.addAttribute("alquiler", new Alquiler());
        model.addAttribute("vehiculos", vehiculoService.listarVehiculos());
        return "user/alquileres/formulario";
    }

    @PostMapping("/alquileres/guardar")
    public String guardarAlquilerUsuario(
            @ModelAttribute("alquiler") Alquiler alquiler,
            Principal principal,
            Model model
    ) {
        Cliente cliente = clienteService.buscarPorUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("No existe un cliente asociado al usuario autenticado."));

        Vehiculo vehiculo = vehiculoService.buscarVehiculoPorId(alquiler.getVehiculo().getIdVehiculo())
                .orElseThrow(() -> new IllegalArgumentException("El vehículo seleccionado no existe."));

        long diasCalculados = ChronoUnit.DAYS.between(alquiler.getFechaInicio(), alquiler.getFechaFin());

        if (diasCalculados <= 0) {
            model.addAttribute("paginaActiva", "nuevo-alquiler");
            model.addAttribute("mensajeError", "La fecha de fin debe ser posterior a la fecha de inicio.");
            model.addAttribute("vehiculos", vehiculoService.listarVehiculos());
            return "user/alquileres/formulario";
        }

        int dias = (int) diasCalculados;
        BigDecimal precioDiaAplicado = vehiculo.getPrecioDia();
        BigDecimal total = precioDiaAplicado.multiply(BigDecimal.valueOf(dias));

        alquiler.setCliente(cliente);
        alquiler.setVehiculo(vehiculo);
        alquiler.setDias(dias);
        alquiler.setPrecioDiaAplicado(precioDiaAplicado);

        if (alquiler.getEstado() == null) {
            alquiler.setEstado(EstadoAlquiler.PENDIENTE);
        }

        alquiler.setTotal(total);

        alquilerService.guardarAlquiler(alquiler);

        return "redirect:/user/mis-alquileres";
    }
}