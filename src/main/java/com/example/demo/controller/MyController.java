package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MyController {

    private final StudentService studentService;

    public MyController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/welcome")
    public String welcome(@RequestParam String name) {
        return "Welcome " + name;
    }


    @PostMapping("/students")
    public String addStudents(@RequestBody List<Student> students) {
        List<Student> allStudents = studentService.addStudents(students);

        return allStudents.stream()
                .map(s -> s.getFirstName() + " " + s.getLastName())
                .collect(Collectors.joining(", "));
    }

    @GetMapping("/students")
    public String getStudents(@RequestHeader(value = "Accept", required = false) String accept) {

        if ("text/plain".equals(accept)) {
            return studentService.getStudents().stream()
                    .map(s -> s.getFirstName() + " " + s.getLastName())
                    .collect(Collectors.joining(", "));
        } else {
            return "Format non supporté.";
        }
    }
}