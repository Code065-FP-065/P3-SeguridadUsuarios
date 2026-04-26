package com.code065.alquilervehiculos.controller.web;

import com.code065.alquilervehiculos.controller.web.dto.RegistroForm;
import com.code065.alquilervehiculos.model.Rol;
import com.code065.alquilervehiculos.model.Usuario;
import com.code065.alquilervehiculos.service.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthViewController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public AuthViewController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "auth/login";
    }

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("registroForm", new RegistroForm());
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(RegistroForm registroForm, Model model) {
        if (usuarioService.existePorUsername(registroForm.getUsername())) {
            model.addAttribute("mensajeError", "El nombre de usuario ya existe.");
            model.addAttribute("registroForm", registroForm);
            return "auth/registro";
        }

        if (registroForm.getPassword() == null
            || !registroForm.getPassword().equals(registroForm.getConfirmarPassword())) {
            model.addAttribute("mensajeError", "Las contrasenas no coinciden.");
            model.addAttribute("registroForm", registroForm);
            return "auth/registro";
        }

        Usuario usuario = new Usuario(
            registroForm.getUsername(),
            registroForm.getNombre(),
            passwordEncoder.encode(registroForm.getPassword()),
            Rol.USER
        );

        usuarioService.guardar(usuario);
        return "redirect:/login?registroOk";
    }
}
