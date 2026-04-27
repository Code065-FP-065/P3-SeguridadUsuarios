package com.code065.alquilervehiculos.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthViewController {

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("paginaActiva", "login");
        return "auth/login";
    }
}
