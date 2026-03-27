package com.code065.alquilervehiculos.controller.web;

import com.code065.alquilervehiculos.model.Cliente;
import com.code065.alquilervehiculos.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClienteViewController {

    private final ClienteService clienteService;

    public ClienteViewController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/clientes")
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.listaClientes());
        return "clientes/lista";
    }

    @GetMapping("/clientes/nuevo")
    public String mostrarFormularioNuevoCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/formulario";
    }

    @GetMapping("/clientes/editar/{id}")
    public String mostrarFormularioEditarCliente(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.buscarClientePorId(id).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con id: " + id));
        model.addAttribute("cliente", cliente);
        return "clientes/formulario";
    }

    @PostMapping("/clientes/guardar")
    public String guardarCliente(Cliente cliente) {
        clienteService.guardarCliente(cliente);
        return "redirect:/clientes";
    }

    @PostMapping("/clientes/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id) {
        // Controlar la eliminación de clientes con alquiler
        clienteService.eliminarCliente(id);
        return "redirect:/clientes";
    }
}
