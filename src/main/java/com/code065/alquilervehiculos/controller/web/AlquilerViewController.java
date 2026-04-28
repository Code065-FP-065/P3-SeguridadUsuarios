package com.code065.alquilervehiculos.controller.web;


import com.code065.alquilervehiculos.model.Alquiler;
import com.code065.alquilervehiculos.model.Cliente;
import com.code065.alquilervehiculos.model.EstadoAlquiler;
import com.code065.alquilervehiculos.model.Vehiculo;
import com.code065.alquilervehiculos.service.AlquilerService;
import com.code065.alquilervehiculos.service.ClienteService;
import com.code065.alquilervehiculos.service.VehiculoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Controller
public class AlquilerViewController {

    private final AlquilerService alquilerService;
    private final ClienteService clienteService;
    private final VehiculoService vehiculoService;

    public AlquilerViewController(AlquilerService alquilerService, ClienteService clienteService, VehiculoService vehiculoService) {
        this.alquilerService = alquilerService;
        this.clienteService = clienteService;
        this.vehiculoService = vehiculoService;
    }

    @GetMapping("/alquileres")
    public String listarAlquileres(Model model) {
        model.addAttribute("alquileres", alquilerService.listarAlquileres());
        model.addAttribute("paginaActiva", "alquileres");
        return "alquileres/lista";
    }

    @GetMapping("/alquileres/nuevo")
    public String mostrarFormularioNuevoAlquiler(Model model) {
        Alquiler alquiler = new Alquiler();
        alquiler.setCliente(new Cliente());
        alquiler.setVehiculo(new Vehiculo());

        model.addAttribute("alquiler", alquiler);
        model.addAttribute("clientes", clienteService.listaClientes());
        model.addAttribute("vehiculos", vehiculoService.listarVehiculos());
        model.addAttribute("estadosAlquiler", EstadoAlquiler.values());
        model.addAttribute("paginaActiva", "alquileres");
        return "alquileres/formulario";
    }

    @GetMapping("/alquileres/editar/{id}")
    public String mostrarFormularioEditarAlquiler(@PathVariable Long id, Model model) {
        Alquiler alquiler = alquilerService.buscarAlquilerPorId(id).orElseThrow(() -> new IllegalArgumentException("Alquiler no encontrado con id: " + id));
        if (alquiler.getCliente() == null) {
            alquiler.setCliente(new Cliente());
        }

        if (alquiler.getVehiculo() == null) {
            alquiler.setVehiculo(new Vehiculo());
        }

        model.addAttribute("alquiler", alquiler);
        model.addAttribute("clientes", clienteService.listaClientes());
        model.addAttribute("vehiculos", vehiculoService.listarVehiculos());
        model.addAttribute("estadosAlquiler", EstadoAlquiler.values());
        model.addAttribute("paginaActiva", "alquileres");
        return "alquileres/formulario";
    }

    @PostMapping("/alquileres/guardar")
    public String guardarAlquiler(Alquiler alquiler, Model model) {
        if (alquiler.getFechaInicio() == null || alquiler.getFechaFin() == null) {
            model.addAttribute("mensajeError", "Debes indicar la fecha de inicio y la fecha de fin.");
            model.addAttribute("clientes", clienteService.listaClientes());
            model.addAttribute("vehiculos", vehiculoService.listarVehiculos());
            model.addAttribute("estadosAlquiler", EstadoAlquiler.values());
            return "alquileres/formulario";
        }

        long diasCalculados = ChronoUnit.DAYS.between(alquiler.getFechaInicio(), alquiler.getFechaFin());

        if (diasCalculados <= 0) {
            model.addAttribute("mensajeError", "La fecha de fin debe ser posterior a la fecha de inicio.");
            model.addAttribute("clientes", clienteService.listaClientes());
            model.addAttribute("vehiculos", vehiculoService.listarVehiculos());
            model.addAttribute("estadosAlquiler", EstadoAlquiler.values());
            return "alquileres/formulario";
        }

        Cliente cliente = clienteService.buscarClientePorId(alquiler.getCliente().getIdCliente()).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        Vehiculo vehiculo = vehiculoService.buscarVehiculoPorId(alquiler.getVehiculo().getIdVehiculo()).orElseThrow(() -> new IllegalArgumentException("Vehiculo no encontrado"));

        int dias = (int) diasCalculados;
        BigDecimal precioDiaAplicado = vehiculo.getPrecioDia();
        BigDecimal total = precioDiaAplicado.multiply(BigDecimal.valueOf(dias));

        alquiler.setCliente(cliente);
        alquiler.setVehiculo(vehiculo);
        alquiler.setDias(dias);
        alquiler.setPrecioDiaAplicado(precioDiaAplicado);
        alquiler.setTotal(total);

        alquilerService.guardarAlquiler(alquiler);
        return "redirect:/alquileres";
    }

    @PostMapping("/alquileres/eliminar/{id}")
    public String eliminarAlquiler(@PathVariable Long id) {
        alquilerService.eliminarAlquiler(id);
        return "redirect:/alquileres";
    }
}
