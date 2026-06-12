package com.example.Api_mongo.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/debug")
public class DebugC {

    @Value("${spring.data.mongodb.uri:NO_CONFIGURADA}")
    private String mongoUri;

    @GetMapping("/mongo-uri")
    public String verUri() {
        // Oculta la contraseña por seguridad, solo muestra estructura
        String env = System.getenv("MONGODB_URI");
        String envInfo = (env == null) ? "MONGODB_URI = NULL (no existe la variable de entorno)" 
                                        : "MONGODB_URI existe, longitud=" + env.length() + ", empieza con: " + env.substring(0, Math.min(15, env.length()));

        String propInfo = "spring.data.mongodb.uri resuelto, longitud=" + mongoUri.length() + ", empieza con: " + mongoUri.substring(0, Math.min(15, mongoUri.length()));

        return envInfo + " | " + propInfo;
    }
}
