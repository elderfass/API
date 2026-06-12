package com.example.Api_mongo.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Api_mongo.models.ClienteM;
import com.example.Api_mongo.respos.ClienteR;

@Service
public class ClienteS {

    @Autowired
    private ClienteR clienteR;

    public List<ClienteM> obtenerClientes() {
        return clienteR.findAll();
    }

    public Optional<ClienteM> obtenerClientePorId(String id) {
        return clienteR.findById(id);
    }

    // Búsqueda parcial por nombre → GET /clientes?nombre=ana
    public List<ClienteM> obtenerClientesPorNombre(String nombre) {
        return clienteR.findByNombreContainingIgnoreCase(nombre);
    }

    // Búsqueda exacta por nombre → GET /clientes/Ana García
    public Optional<ClienteM> obtenerClientePorNombreExacto(String nombre) {
        return clienteR.findByNombreIgnoreCase(nombre);
    }

    public ClienteM guardarCliente(ClienteM cliente) {
        if (clienteR.existsByEmail(cliente.getEmail())) {
            throw new RuntimeException("Ya existe un cliente con el email: " + cliente.getEmail());
        }
        return clienteR.save(cliente);
    }

    public ClienteM actualizarCliente(String id, ClienteM clienteActualizado) {
        return clienteR.findById(id).map(cliente -> {
            cliente.setNombre(clienteActualizado.getNombre());
            cliente.setEmail(clienteActualizado.getEmail());
            cliente.setTelefono(clienteActualizado.getTelefono());
            cliente.setDireccion(clienteActualizado.getDireccion());
            return clienteR.save(cliente);
        }).orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
    }

    public Optional<ClienteM> actualizarParcial(String id, Map<String, Object> campos) {
        return clienteR.findById(id).map(cliente -> {
            campos.forEach((llave, valor) -> {
                switch (llave) {
                    case "nombre":   cliente.setNombre((String) valor);    break;
                    case "email":    cliente.setEmail((String) valor);     break;
                    case "telefono": cliente.setTelefono((String) valor);  break;
                    case "direccion":cliente.setDireccion((String) valor); break;
                    default: break;
                }
            });
            return clienteR.save(cliente);
        });
    }

    public void eliminarCliente(String id) {
        if (!clienteR.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado con id: " + id);
        }
        clienteR.deleteById(id);
    }
}