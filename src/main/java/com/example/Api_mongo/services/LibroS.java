package com.example.Api_mongo.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Api_mongo.models.LibroM;
import com.example.Api_mongo.respos.LibroR;

@Service
public class LibroS {

    @Autowired
    private LibroR libroR;

    public List<LibroM> obtenerLibros() {
        return libroR.findAll();
    }

    public Optional<LibroM> obtenerLibroPorId(String id) {
        return libroR.findById(id);
    }

    public List<LibroM> obtenerLibrosDisponibles() {
        return libroR.findByDisponible(true);
    }

    public List<LibroM> buscarPorAutor(String autor) {
        return libroR.findByAutorContainingIgnoreCase(autor);
    }

    public List<LibroM> buscarPorTitulo(String titulo) {
        return libroR.findByTituloContainingIgnoreCase(titulo);
    }

    public List<LibroM> buscarPorGenero(String genero) {
        return libroR.findByGeneroIgnoreCase(genero);
    }

    public LibroM guardarLibro(LibroM libro) {
        libro.setDisponible(true);
        return libroR.save(libro);
    }

    public LibroM actualizarLibro(String id, LibroM libroActualizado) {
        return libroR.findById(id).map(libro -> {
            libro.setTitulo(libroActualizado.getTitulo());
            libro.setAutor(libroActualizado.getAutor());
            libro.setGenero(libroActualizado.getGenero());
            libro.setAnioPublicacion(libroActualizado.getAnioPublicacion());
            return libroR.save(libro);
        }).orElseThrow(() -> new RuntimeException("Libro no encontrado con id: " + id));
    }

    public Optional<LibroM> actualizarParcial(String id, Map<String, Object> campos) {
        return libroR.findById(id).map(libro -> {
            campos.forEach((llave, valor) -> {
                switch (llave) {
                    case "titulo":
                        libro.setTitulo((String) valor);
                        break;
                    case "autor":
                        libro.setAutor((String) valor);
                        break;
                    case "genero":
                        libro.setGenero((String) valor);
                        break;
                    case "anioPublicacion":
                        if (valor instanceof Number) {
                            libro.setAnioPublicacion(((Number) valor).intValue());
                        } else if (valor instanceof String) {
                            libro.setAnioPublicacion(Integer.parseInt((String) valor));
                        }
                        break;
                    case "disponible":
                        if (valor instanceof Boolean) {
                            libro.setDisponible((Boolean) valor);
                        } else if (valor instanceof String) {
                            libro.setDisponible(Boolean.parseBoolean((String) valor));
                        }
                        break;
                    default:
                        break;
                }
            });
            return libroR.save(libro);
        });
    }

    public void eliminarLibro(String id) {
        if (!libroR.existsById(id)) {
            throw new RuntimeException("Libro no encontrado con id: " + id);
        }
        libroR.deleteById(id);
    }
}