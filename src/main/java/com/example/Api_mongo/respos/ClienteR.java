package com.example.Api_mongo.respos;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.Api_mongo.models.ClienteM;

@Repository
public interface ClienteR extends MongoRepository<ClienteM, String> {
    Optional<ClienteM> findByEmail(String email);
    boolean existsByEmail(String email);
}