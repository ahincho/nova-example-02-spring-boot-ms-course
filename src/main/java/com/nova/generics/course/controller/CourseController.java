package com.nova.generics.course.controller;

import org.springframework.web.bind.annotation.*;
import pe.edu.nova.java.libs.observability.annotation.Traced;
import pe.edu.nova.java.libs.observability.annotation.Metered;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @GetMapping
    public List<Map<String, Object>> listCourses() {
        return List.of(
            Map.of("id", 1, "name", "Java Avanzado", "instructor", "Carlos López"),
            Map.of("id", 2, "name", "Spring Boot 4", "instructor", "María García"),
            Map.of("id", 3, "name", "Microservicios", "instructor", "Juan Pérez")
        );
    }

    @GetMapping("/{id}")
    @Traced("course.findById")
    public Map<String, Object> getCourse(@PathVariable int id) {
        // Simula latencia variable
        simulateLatency(50, 200);
        if (id > 100) {
            throw new IllegalArgumentException("Curso no encontrado: " + id);
        }
        return Map.of("id", id, "name", "Java Avanzado", "instructor", "Carlos López", "students", 42);
    }

    @PostMapping
    @Metered("course.create")
    public Map<String, Object> createCourse(@RequestBody Map<String, String> body) {
        simulateLatency(100, 500);
        return Map.of("id", ThreadLocalRandom.current().nextInt(1000), "name", body.get("name"), "status", "created");
    }

    @GetMapping("/{id}/students")
    @Traced
    public List<Map<String, Object>> getStudents(@PathVariable int id) {
        simulateLatency(30, 150);
        return List.of(
            Map.of("id", 101, "name", "Ana Torres", "email", "ana@example.com"),
            Map.of("id", 102, "name", "Pedro Ruiz", "email", "pedro@example.com")
        );
    }

    private void simulateLatency(int minMs, int maxMs) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(minMs, maxMs));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
