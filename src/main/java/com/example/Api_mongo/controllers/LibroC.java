package com.example.Api_mongo.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Api_mongo.models.LibroM;
import com.example.Api_mongo.services.LibroS;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/libros")
public class LibroC {

    @Autowired
    private LibroS libroS;

    // GET /libros - Obtener todos los libros
    @GetMapping
    public ResponseEntity<List<LibroM>> getLibros() {
        return ResponseEntity.ok(libroS.obtenerLibros());
    }

    // GET /libros/{id} - Obtener libro por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getLibroById(@PathVariable String id) {
        return libroS.obtenerLibroPorId(id)
            .<ResponseEntity<?>>map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Libro no encontrado con id: " + id));
    }

    // GET /libros/disponibles - Obtener libros disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<LibroM>> getLibrosDisponibles() {
        return ResponseEntity.ok(libroS.obtenerLibrosDisponibles());
    }

    // GET /libros/buscar/autor?nombre=xxx
    @GetMapping("/buscar/autor")
    public ResponseEntity<List<LibroM>> buscarPorAutor(@RequestParam String nombre) {
        return ResponseEntity.ok(libroS.buscarPorAutor(nombre));
    }

    // GET /libros/buscar/titulo?nombre=xxx
    @GetMapping("/buscar/titulo")
    public ResponseEntity<List<LibroM>> buscarPorTitulo(@RequestParam String nombre) {
        return ResponseEntity.ok(libroS.buscarPorTitulo(nombre));
    }

    // GET /libros/buscar/genero?nombre=xxx
    @GetMapping("/buscar/genero")
    public ResponseEntity<List<LibroM>> buscarPorGenero(@RequestParam String nombre) {
        return ResponseEntity.ok(libroS.buscarPorGenero(nombre));
    }

    // POST /libros - Registrar nuevo libro
    @PostMapping
    public ResponseEntity<LibroM> postLibro(@RequestBody LibroM libro) {
        return ResponseEntity.status(HttpStatus.CREATED).body(libroS.guardarLibro(libro));
    }

    // PUT /libros/{id} - Actualizar libro
    @PutMapping("/{id}")
    public ResponseEntity<?> putLibro(@PathVariable String id, @RequestBody LibroM libro) {
        try {
            return ResponseEntity.ok(libroS.actualizarLibro(id, libro));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // PATCH /libros/{id} - Actualizar libro parcialmente
    @PatchMapping("/{id}")
    public ResponseEntity<?> patchLibro(@PathVariable String id, @RequestBody Map<String, Object> campos) {
        return libroS.actualizarParcial(id, campos)
            .<ResponseEntity<?>>map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Libro no encontrado con id: " + id));
    }

    // DELETE /libros/{id} - Eliminar libro
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLibro(@PathVariable String id) {
        try {
            libroS.eliminarLibro(id);
            return ResponseEntity.ok("Libro eliminado correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}