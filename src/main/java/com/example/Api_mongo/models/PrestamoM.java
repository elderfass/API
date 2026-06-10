package com.example.Api_mongo.models;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "prestamos")
public class PrestamoM {

    @Id
    private String id;
    private String clienteId;
    private String libroId;
    private ClienteInfo cliente;
    private LibroInfo libro;
    private LocalDateTime fechaPrestamo;
    private LocalDateTime fechaDevolucion;
    private boolean activo;

    public PrestamoM() {
        this.fechaPrestamo = LocalDateTime.now();
        this.activo = true;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getClienteId() { return clienteId; }
    public void setClienteId(String clienteId) { this.clienteId = clienteId; }

    public String getLibroId() { return libroId; }
    public void setLibroId(String libroId) { this.libroId = libroId; }

    public ClienteInfo getCliente() { return cliente; }
    public void setCliente(ClienteInfo cliente) { this.cliente = cliente; }

    public LibroInfo getLibro() { return libro; }
    public void setLibro(LibroInfo libro) { this.libro = libro; }

    public LocalDateTime getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDateTime fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    public LocalDateTime getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDateTime fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public static class ClienteInfo {
        private String id;
        private String nombre;
        private String email;
        private String telefono;
        private String direccion;

        public ClienteInfo() {}

        public ClienteInfo(String id, String nombre, String email, String telefono, String direccion) {
            this.id = id;
            this.nombre = nombre;
            this.email = email;
            this.telefono = telefono;
            this.direccion = direccion;
        }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getTelefono() { return telefono; }
        public void setTelefono(String telefono) { this.telefono = telefono; }

        public String getDireccion() { return direccion; }
        public void setDireccion(String direccion) { this.direccion = direccion; }
    }

    public static class LibroInfo {
        private String id;
        private String titulo;
        private String autor;
        private String genero;

        public LibroInfo() {}

        public LibroInfo(String id, String titulo, String autor, String genero) {
            this.id = id;
            this.titulo = titulo;
            this.autor = autor;
            this.genero = genero;
        }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getTitulo() { return titulo; }
        public void setTitulo(String titulo) { this.titulo = titulo; }

        public String getAutor() { return autor; }
        public void setAutor(String autor) { this.autor = autor; }

        public String getGenero() { return genero; }
        public void setGenero(String genero) { this.genero = genero; }
    }
}