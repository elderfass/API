package com.example.Api_mongo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Api_mongo.models.PrestamoM;
import com.example.Api_mongo.services.PrestamoS;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/prestamos")
public class PrestamoC {

    @Autowired
    private PrestamoS prestamoS;

    // GET /prestamos - Todos los préstamos
    @GetMapping
    public ResponseEntity<List<PrestamoM>> getTodos() {
        return ResponseEntity.ok(prestamoS.obtenerTodos());
    }

    // GET /prestamos/activos - Préstamos activos
    @GetMapping("/activos")
    public ResponseEntity<List<PrestamoM>> getActivos() {
        return ResponseEntity.ok(prestamoS.obtenerPrestamosActivos());
    }

    // POST /prestamos - Crear nuevo préstamo
    // Body: { "clienteId": "xxx", "libroId": "yyy", "id": "customId" }
    @PostMapping
    public ResponseEntity<?> crearPrestamo(@RequestBody PrestamoM request) {
        try {
            PrestamoM nuevo = prestamoS.crearPrestamo(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // PUT /prestamos/{id}/devolver - Devolver un libro
    @PutMapping("/{id}/devolver")
    public ResponseEntity<?> devolverLibro(@PathVariable String id) {
        try {
            return ResponseEntity.ok(prestamoS.devolverLibro(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // GET /prestamos/cliente/{clienteId} - Historial completo de un cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<?> getPrestamosPorCliente(@PathVariable String clienteId) {
        try {
            return ResponseEntity.ok(prestamoS.obtenerPrestamosPorCliente(clienteId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // GET /prestamos/cliente/{clienteId}/activos - Libros que tiene prestados ahora
    @GetMapping("/cliente/{clienteId}/activos")
    public ResponseEntity<?> getLibrosPrestadosPorCliente(@PathVariable String clienteId) {
        try {
            return ResponseEntity.ok(prestamoS.obtenerLibrosPrestadosPorCliente(clienteId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // GET /prestamos/libro/{libroId} - Historial de préstamos de un libro
    @GetMapping("/libro/{libroId}")
    public ResponseEntity<?> getHistorialPorLibro(@PathVariable String libroId) {
        try {
            return ResponseEntity.ok(prestamoS.obtenerHistorialPorLibro(libroId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}