package com.code065.alquilervehiculos.controller.web;

import com.code065.alquilervehiculos.model.EstadoVehiculo;
import com.code065.alquilervehiculos.model.Vehiculo;
import com.code065.alquilervehiculos.service.VehiculoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VehiculoViewController {

    private final VehiculoService vehiculoService;

    public VehiculoViewController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @GetMapping("/vehiculos")
    public String listarVehiculos(Model model) {
        model.addAttribute("vehiculos", vehiculoService.listarVehiculos());
        return "vehiculos/lista";
    }

    @GetMapping("/vehiculos/nuevo")
    public String mostrarFormularioNuevoVehiculo(Model model) {
        model.addAttribute("vehiculo", new Vehiculo());
        model.addAttribute("estadosVehiculo", EstadoVehiculo.values());
        return "vehiculos/formulario";
    }

    @GetMapping("/vehiculos/editar/{id}")
    public String editarFormularioEditarVehiculo(@PathVariable Long id, Model model) {
        Vehiculo vehiculo = vehiculoService.buscarVehiculoPorId(id).orElseThrow(() -> new IllegalArgumentException("Vehiculo no encontrado con id: " + id));
        model.addAttribute("vehiculo", vehiculo);
        model.addAttribute("estadosVehiculo", EstadoVehiculo.values());
        return "vehiculos/formulario";
    }

    @PostMapping("/vehiculos/guardar")
    public String guardarVehiculo(Vehiculo vehiculo) {
        vehiculoService.guardarVehiculo(vehiculo);
        return "redirect:/vehiculos";
    }

    @PostMapping("vehiculos/eliminar/{id}")
    public String eliminarVehiculo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            vehiculoService.eliminarVehiculo(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Vehículo eliminado correctamente.");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }

        return "redirect:/vehiculos";
    }
}
