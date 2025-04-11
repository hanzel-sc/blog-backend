package com.example.blog;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogApplication {
    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        // Set as system properties so Spring can resolve ${...}
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        SpringApplication.run(BlogApplication.class, args);
    }
}
