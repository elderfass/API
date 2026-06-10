package com.example.Api_mongo.respos;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.Api_mongo.models.LibroM;

@Repository
public interface LibroR extends MongoRepository<LibroM, String> {
    List<LibroM> findByDisponible(boolean disponible);
    List<LibroM> findByAutorContainingIgnoreCase(String autor);
    List<LibroM> findByTituloContainingIgnoreCase(String titulo);
    List<LibroM> findByGeneroIgnoreCase(String genero);
}