package com.code065.alquilervehiculos.controller.web;

import com.code065.alquilervehiculos.model.Cliente;
import com.code065.alquilervehiculos.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClienteViewController {

    private final ClienteService clienteService;

    public ClienteViewController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/user/clientes")
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.listaClientes());
        model.addAttribute("paginaActiva", "clientes");
        return "clientes/lista";
    }

    @GetMapping("/admin/clientes/nuevo")
    public String mostrarFormularioNuevoCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("paginaActiva", "clientes");
        return "clientes/formulario";
    }

    @GetMapping("/admin/clientes/editar/{id}")
    public String mostrarFormularioEditarCliente(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.buscarClientePorId(id).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con id: " + id));
        model.addAttribute("cliente", cliente);
        return "clientes/formulario";
    }

    @PostMapping("/admin/clientes/guardar")
    public String guardarCliente(Cliente cliente) {
        clienteService.guardarCliente(cliente);
        return "redirect:/user/clientes";
    }

    @PostMapping("/admin/clientes/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            clienteService.eliminarCliente(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Cliente eliminado correctamente.");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }

        return "redirect:/user/clientes";
    }
}
