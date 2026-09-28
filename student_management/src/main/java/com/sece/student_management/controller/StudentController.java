package com.sece.student_management.controller;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class StudentController {
//    @GetMapping("/students")
//    public String getStudents() {
//        return "All students";
//    }
//    @PostMapping("/hello")
//    public String hello() {
//        return "Hello World";
//    }
//    @PostMapping("/map")
//    public Integer map() {
//        return 10;
//    }
//}

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sece.student_management.service.StudentService;

@RestController 
public class StudentController{
    private final StudentService studentService;
    
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    
    @GetMapping("/students")
    public String createStudent() {
        return studentService.createStudent();
    }

    @PostMapping ("/hello")
    public String hello() {
        return "Hello World";
    }
}
    