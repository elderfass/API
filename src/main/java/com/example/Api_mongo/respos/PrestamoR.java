package com.example.Api_mongo.respos;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.Api_mongo.models.PrestamoM;

@Repository
public interface PrestamoR extends MongoRepository<PrestamoM, String> {
    List<PrestamoM> findByClienteId(String clienteId);
    List<PrestamoM> findByLibroId(String libroId);
    List<PrestamoM> findByActivo(boolean activo);
    List<PrestamoM> findByClienteIdAndActivo(String clienteId, boolean activo);
    boolean existsByLibroIdAndActivo(String libroId, boolean activo);
}