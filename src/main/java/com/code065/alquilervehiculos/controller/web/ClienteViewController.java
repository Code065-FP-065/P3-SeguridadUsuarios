package com.code065.alquilervehiculos.controller.web;

import com.code065.alquilervehiculos.model.Cliente;
import com.code065.alquilervehiculos.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/clientes")
public class ClienteViewController {

    private final ClienteService clienteService;

    public ClienteViewController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("paginaActiva", "clientes");
        model.addAttribute("clientes", clienteService.listarClientes());
        return "admin/clientes/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("paginaActiva", "clientes");
        model.addAttribute("cliente", new Cliente());
        return "admin/clientes/formulario";
    }

    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente, Model model) {
        try {
            clienteService.guardarCliente(cliente);
            return "redirect:/admin/clientes";
        } catch (Exception e) {
            model.addAttribute("paginaActiva", "clientes");
            model.addAttribute("mensajeError", e.getMessage());
            model.addAttribute("cliente", cliente);
            return "admin/clientes/formulario";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.buscarClientePorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado."));

        model.addAttribute("paginaActiva", "clientes");
        model.addAttribute("cliente", cliente);
        return "admin/clientes/formulario";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return "redirect:/admin/clientes";
    }
}