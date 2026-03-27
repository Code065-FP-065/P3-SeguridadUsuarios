package com.code065.alquilervehiculos.controller.web;


import com.code065.alquilervehiculos.model.Alquiler;
import com.code065.alquilervehiculos.service.AlquilerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AlquilerViewController {

    private final AlquilerService alquilerService;

    public AlquilerViewController(AlquilerService alquilerService) {
        this.alquilerService = alquilerService;
    }

    @GetMapping("/alquileres")
    public String listarAlquileres(Model model) {
        model.addAttribute("alquileres", alquilerService.listarVehiculos());
        return "alquileres/lista";
    }

    @GetMapping("/alquileres/nuevo")
    public String mostrarFormularioNuevoAlquiler(Model model) {
        model.addAttribute("alquileres", alquilerService.listarVehiculos());
        return "alquileres/formulario";
    }

    @GetMapping("/alquileres/editar/{id}")
    public String mostrarFormularioEditarAlquiler(@PathVariable Long id, Model model) {
        Alquiler alquiler = alquilerService.buscarVehiculoPorId(id).orElseThrow(() -> new IllegalArgumentException("Alquiler no encontrado con id: " + id));
        model.addAttribute("alquiler", alquiler);
        return "alquileres/formulario";
    }

    @PostMapping("alquileres/guardar")
    public String guardarAlquiler(Alquiler alquiler) {
        alquilerService.guardarAlquiler(alquiler);
        return "redirect:/alquileres";
    }

    @PostMapping("/alquileres/eliminiar/{id]")
    public String eliminarAlquiler(@PathVariable Long id, Model model) {
        alquilerService.eliminarAlquiler(id);
        return "redirect:/alquileres";
    }
}
