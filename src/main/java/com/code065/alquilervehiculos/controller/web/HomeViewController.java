package com.code065.alquilervehiculos.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeViewController {

    @GetMapping({"/", "/dashboard"})
    public String mostrarInicio(Model model) {
        model.addAttribute("paginaActiva", "dashboard");
        return "index";
    }
}