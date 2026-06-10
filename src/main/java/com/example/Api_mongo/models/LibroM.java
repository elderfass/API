package com.example.Api_mongo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "libros")
public class LibroM {

    @Id
    private String id;
    private String titulo;
    private String autor;
    private String genero;
    private int anioPublicacion;
    private boolean disponible;

    public LibroM() {
        this.disponible = true;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public int getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(int anioPublicacion) { this.anioPublicacion = anioPublicacion; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}