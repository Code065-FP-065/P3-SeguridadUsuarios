package com.code065.alquilervehiculos.controller.web;

import com.code065.alquilervehiculos.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/usuarios")
public class AdminUsuarioVeiwController {

    private final UsuarioService usuarioService;

    public AdminUsuarioVeiwController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarUsuarios());
        model.addAttribute("paginaActiva", "usuarios");
        return "usuarios/lista";
    }

    @PostMapping("/activar/{id}")
    public String activarUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        usuarioService.activarUsuario(id);
        redirectAttributes.addFlashAttribute("mensajeExito", "Usuario activado correctamente.");
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/desactivar/{id}")
    public String desactivarUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        usuarioService.desactivarUsuario(id);
        redirectAttributes.addFlashAttribute("mensajeExito", "Usuario desactivado correctamente.");
        return "redirect:/admin/usuarios";
    }
}
