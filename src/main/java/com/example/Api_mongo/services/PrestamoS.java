package com.example.Api_mongo.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Api_mongo.models.LibroM;
import com.example.Api_mongo.models.PrestamoM;
import com.example.Api_mongo.respos.ClienteR;
import com.example.Api_mongo.respos.LibroR;
import com.example.Api_mongo.respos.PrestamoR;

@Service
public class PrestamoS {

    @Autowired
    private PrestamoR prestamoR;

    @Autowired
    private ClienteR clienteR;

    @Autowired
    private LibroR libroR;

    // Registrar un nuevo préstamo
    public PrestamoM crearPrestamo(PrestamoM prestamo) {
        String clienteId = prestamo.getClienteId();
        String libroId = prestamo.getLibroId();

        // Validar que el cliente existe y cargar su información
        var cliente = clienteR.findById(clienteId)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + clienteId));

        // Validar que el libro existe y está disponible
        LibroM libro = libroR.findById(libroId)
            .orElseThrow(() -> new RuntimeException("Libro no encontrado con id: " + libroId));

        if (!libro.isDisponible()) {
            throw new RuntimeException("El libro '" + libro.getTitulo() + "' no está disponible actualmente.");
        }

        // Asegurar estado inicial correcto
        prestamo.setActivo(true);
        if (prestamo.getFechaPrestamo() == null) {
            prestamo.setFechaPrestamo(LocalDateTime.now());
        }

        // Guardar snapshot de cliente y libro para aprovechar el modelo documental de MongoDB
        prestamo.setCliente(new PrestamoM.ClienteInfo(
            cliente.getId(),
            cliente.getNombre(),
            cliente.getEmail(),
            cliente.getTelefono(),
            cliente.getDireccion()));

        prestamo.setLibro(new PrestamoM.LibroInfo(
            libro.getId(),
            libro.getTitulo(),
            libro.getAutor(),
            libro.getGenero()));

        // Marcar el libro como no disponible
        libro.setDisponible(false);
        libroR.save(libro);

        return prestamoR.save(prestamo);
    }

    // Devolver un libro (cerrar préstamo)
    public PrestamoM devolverLibro(String prestamoId) {
        PrestamoM prestamo = prestamoR.findById(prestamoId)
            .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con id: " + prestamoId));

        if (!prestamo.isActivo()) {
            throw new RuntimeException("Este préstamo ya fue cerrado.");
        }

        // Cerrar el préstamo
        prestamo.setActivo(false);
        prestamo.setFechaDevolucion(LocalDateTime.now());

        // Marcar el libro como disponible nuevamente
        libroR.findById(prestamo.getLibroId()).ifPresent(libro -> {
            libro.setDisponible(true);
            libroR.save(libro);
        });

        return prestamoR.save(prestamo);
    }

    // Obtener todos los préstamos
    public List<PrestamoM> obtenerTodos() {
        return prestamoR.findAll();
    }

    // Obtener préstamos activos
    public List<PrestamoM> obtenerPrestamosActivos() {
        return prestamoR.findByActivo(true);
    }

    // Obtener préstamos por cliente
    public List<PrestamoM> obtenerPrestamosPorCliente(String clienteId) {
        if (!clienteR.existsById(clienteId)) {
            throw new RuntimeException("Cliente no encontrado con id: " + clienteId);
        }
        return prestamoR.findByClienteId(clienteId);
    }

    // Obtener préstamos activos de un cliente (libros que tiene prestados)
    public List<PrestamoM> obtenerLibrosPrestadosPorCliente(String clienteId) {
        if (!clienteR.existsById(clienteId)) {
            throw new RuntimeException("Cliente no encontrado con id: " + clienteId);
        }
        return prestamoR.findByClienteIdAndActivo(clienteId, true);
    }

    // Historial de préstamos de un libro
    public List<PrestamoM> obtenerHistorialPorLibro(String libroId) {
        if (!libroR.existsById(libroId)) {
            throw new RuntimeException("Libro no encontrado con id: " + libroId);
        }
        return prestamoR.findByLibroId(libroId);
    }
}