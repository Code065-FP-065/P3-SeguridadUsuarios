package com.code065.alquilervehiculos.controller.web;

import com.code065.alquilervehiculos.model.Alquiler;
import com.code065.alquilervehiculos.model.EstadoAlquiler;
import com.code065.alquilervehiculos.model.Vehiculo;
import com.code065.alquilervehiculos.service.AlquilerService;
import com.code065.alquilervehiculos.service.ClienteService;
import com.code065.alquilervehiculos.service.VehiculoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("/admin/alquileres")
public class AdminAlquilerViewController {

    private final AlquilerService alquilerService;
    private final ClienteService clienteService;
    private final VehiculoService vehiculoService;

    public AdminAlquilerViewController(
            AlquilerService alquilerService,
            ClienteService clienteService,
            VehiculoService vehiculoService
    ) {
        this.alquilerService = alquilerService;
        this.clienteService = clienteService;
        this.vehiculoService = vehiculoService;
    }

    @GetMapping
    public String listarAlquileres(Model model) {
        model.addAttribute("paginaActiva", "alquileres-admin");
        model.addAttribute("alquileres", alquilerService.listarAlquileres());
        return "admin/alquileres/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("paginaActiva", "alquileres-admin");
        model.addAttribute("alquiler", new Alquiler());
        model.addAttribute("clientes", clienteService.listarClientes());
        model.addAttribute("vehiculos", vehiculoService.listarVehiculos());
        model.addAttribute("estados", EstadoAlquiler.values());
        return "admin/alquileres/formulario";
    }

    @PostMapping("/guardar")
    public String guardarAlquilerAdmin(
            @ModelAttribute("alquiler") Alquiler alquiler,
            Model model
    ) {
        try {
            var cliente = clienteService.buscarClientePorId(alquiler.getCliente().getIdCliente())
                    .orElseThrow(() -> new IllegalArgumentException("El cliente seleccionado no existe."));

            Vehiculo vehiculo = vehiculoService.buscarVehiculoPorId(alquiler.getVehiculo().getIdVehiculo())
                    .orElseThrow(() -> new IllegalArgumentException("El vehículo seleccionado no existe."));

            long diasCalculados = ChronoUnit.DAYS.between(alquiler.getFechaInicio(), alquiler.getFechaFin());

            if (diasCalculados <= 0) {
                throw new IllegalArgumentException("La fecha de fin debe ser posterior a la fecha de inicio.");
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
            return "redirect:/admin/alquileres";

        } catch (IllegalArgumentException e) {
            model.addAttribute("paginaActiva", "alquileres-admin");
            model.addAttribute("mensajeError", e.getMessage());
            model.addAttribute("clientes", clienteService.listarClientes());
            model.addAttribute("vehiculos", vehiculoService.listarVehiculos());
            model.addAttribute("estados", EstadoAlquiler.values());
            return "admin/alquileres/formulario";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Alquiler alquiler = alquilerService.buscarAlquilerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("El alquiler no existe."));

        model.addAttribute("paginaActiva", "alquileres-admin");
        model.addAttribute("alquiler", alquiler);
        model.addAttribute("clientes", clienteService.listarClientes());
        model.addAttribute("vehiculos", vehiculoService.listarVehiculos());
        model.addAttribute("estados", EstadoAlquiler.values());
        return "admin/alquileres/formulario";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarAlquiler(@PathVariable Long id) {
        alquilerService.eliminarAlquiler(id);
        return "redirect:/admin/alquileres";
    }
}