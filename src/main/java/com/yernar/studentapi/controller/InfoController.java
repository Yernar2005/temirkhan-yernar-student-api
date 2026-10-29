package com.yernar.studentapi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class InfoController {

    @Value("${app.student.name}")
    private String studentName;

    @Value("${app.student.id}")
    private String studentId;

    @Value("${app.version}")
    private String version;

    @GetMapping("/api/info")
    public Map<String, String> info() {
        return Map.of(
                "application", "Student API",
                "student", studentName,
                "studentId", studentId,
                "version", version);
    }
}
