package com.code065.alquilervehiculos.controller.api;

import com.code065.alquilervehiculos.repository.ClienteRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

    private final ClienteRepository clienteRepository;

    public ClienteRestController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public List<ClienteDto> listarClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(cliente -> new ClienteDto(
                        cliente.getIdCliente(),
                        cliente.getNombre(),
                        cliente.getApellidos(),
                        cliente.getDni(),
                        cliente.getTelefono()
                ))
                .toList();
    }

    public record ClienteDto(
            Long idCliente,
            String nombre,
            String apellidos,
            String dni,
            String telefono
    ) {
    }
}