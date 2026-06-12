package com.example.Api_mongo.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Api_mongo.models.ClienteM;
import com.example.Api_mongo.services.ClienteS;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/clientes")
public class ClienteC {

    @Autowired
    private ClienteS clienteS;

    // GET /clientes                 → todos
    // GET /clientes?nombre=ana      → búsqueda parcial por nombre
    @GetMapping
    public ResponseEntity<List<ClienteM>> getClientes(
            @RequestParam(required = false) String nombre) {

        if (nombre != null && !nombre.isBlank()) {
            return ResponseEntity.ok(clienteS.obtenerClientesPorNombre(nombre));
        }
        return ResponseEntity.ok(clienteS.obtenerClientes());
    }

    // GET /clientes/buscar?nombre=Carlos Pez  → ruta dedicada, sin ambigüedad con /{id}
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorNombre(@RequestParam String nombre) {
        List<ClienteM> resultado = clienteS.obtenerClientesPorNombre(nombre);
        if (resultado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontraron clientes con nombre: " + nombre);
        }
        return ResponseEntity.ok(resultado);
    }

    // GET /clientes/{id}  → solo por ID de MongoDB
    @GetMapping("/{id}")
    public ResponseEntity<?> getClienteById(@PathVariable String id) {
        return clienteS.obtenerClientePorId(id)
            .<ResponseEntity<?>>map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Cliente no encontrado con id: " + id));
    }

    // POST /clientes
    @PostMapping
    public ResponseEntity<?> postCliente(@RequestBody ClienteM cliente) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(clienteS.guardarCliente(cliente));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // PUT /clientes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> putCliente(
            @PathVariable String id, @RequestBody ClienteM cliente) {
        try {
            return ResponseEntity.ok(clienteS.actualizarCliente(id, cliente));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // PATCH /clientes/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<?> patchCliente(
            @PathVariable String id, @RequestBody Map<String, Object> campos) {
        return clienteS.actualizarParcial(id, campos)
            .<ResponseEntity<?>>map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Cliente no encontrado con id: " + id));
    }

    // DELETE /clientes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable String id) {
        try {
            clienteS.eliminarCliente(id);
            return ResponseEntity.ok("Cliente eliminado correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}