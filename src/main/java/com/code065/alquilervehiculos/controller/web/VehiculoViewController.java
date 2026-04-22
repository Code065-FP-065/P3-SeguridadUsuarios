package com.code065.alquilervehiculos.controller.web;

import com.code065.alquilervehiculos.model.EstadoVehiculo;
import com.code065.alquilervehiculos.model.Vehiculo;
import com.code065.alquilervehiculos.service.VehiculoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/vehiculos")
public class VehiculoViewController {

    private final VehiculoService vehiculoService;

    public VehiculoViewController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @GetMapping
    public String listarVehiculos(Model model) {
        model.addAttribute("paginaActiva", "vehiculos");
        model.addAttribute("vehiculos", vehiculoService.listarVehiculos());
        return "admin/vehiculos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("paginaActiva", "vehiculos");
        model.addAttribute("vehiculo", new Vehiculo());
        model.addAttribute("estados", EstadoVehiculo.values());
        return "admin/vehiculos/formulario";
    }

    @PostMapping("/guardar")
    public String guardarVehiculo(@ModelAttribute Vehiculo vehiculo, Model model) {
        try {
            vehiculoService.guardarVehiculo(vehiculo);
            return "redirect:/admin/vehiculos";
        } catch (Exception e) {
            model.addAttribute("paginaActiva", "vehiculos");
            model.addAttribute("mensajeError", e.getMessage());
            model.addAttribute("vehiculo", vehiculo);
            model.addAttribute("estados", EstadoVehiculo.values());
            return "admin/vehiculos/formulario";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Vehiculo vehiculo = vehiculoService.buscarVehiculoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado."));

        model.addAttribute("paginaActiva", "vehiculos");
        model.addAttribute("vehiculo", vehiculo);
        model.addAttribute("estados", EstadoVehiculo.values());
        return "admin/vehiculos/formulario";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarVehiculo(@PathVariable Long id) {
        vehiculoService.eliminarVehiculo(id);
        return "redirect:/admin/vehiculos";
    }
}
