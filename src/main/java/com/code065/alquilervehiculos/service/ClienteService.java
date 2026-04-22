package com.code065.alquilervehiculos.service;

import com.code065.alquilervehiculos.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    List<Cliente> listarClientes();

    Optional<Cliente> buscarClientePorId(Long id);

    Optional<Cliente> buscarPorUsername(String username);

    Cliente guardarCliente(Cliente cliente);

    void eliminarCliente(Long id);
}