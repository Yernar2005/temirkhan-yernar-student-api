package com.yernar.studentapi.config;

import com.yernar.studentapi.model.Student;
import com.yernar.studentapi.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner seedStudents(StudentRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Student(
                        "Yernar", "Temirkhan", "yernar.temirkhan@example.com", "IT2-2314"));
            }
        };
    }
}
