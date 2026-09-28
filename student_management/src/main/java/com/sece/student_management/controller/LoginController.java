package com.sece.student_management.controller;

import com.sece.student_management.entity.Alumni;
import com.sece.student_management.entity.Student;
import com.sece.student_management.repository.AlumniRepository;
import com.sece.student_management.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AlumniRepository alumniRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        if (loginRequest.getEmail() == null || loginRequest.getEmail().trim().isEmpty() ||
            loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty() ||
            loginRequest.getRole() == null || loginRequest.getRole().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Email, password and role are required");
        }

        String email = loginRequest.getEmail().trim();
        String password = loginRequest.getPassword();
        String role = loginRequest.getRole().trim().toLowerCase();

        if ("student".equals(role)) {
            Optional<Student> studentOpt = studentRepository.findByEmail(email);
            if (studentOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            Student student = studentOpt.get();
            if (!password.equals(student.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid password");
            }
            return ResponseEntity.ok(new LoginResponse(student.getId(), student.getName(), student.getEmail(), "student"));
        } else if ("alumni".equals(role)) {
            Optional<Alumni> alumniOpt = alumniRepository.findByEmail(email);
            if (alumniOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            Alumni alumni = alumniOpt.get();
            if (!password.equals(alumni.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid password");
            }
            return ResponseEntity.ok(new LoginResponse(alumni.getId(), alumni.getName(), alumni.getEmail(), "alumni"));
        } else {
            return ResponseEntity.badRequest().body("Invalid role");
        }
    }

    public static class LoginRequest {
        private String email;
        private String password;
        private String role;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }

    public static class LoginResponse {
        private Long id;
        private String name;
        private String email;
        private String role;

        public LoginResponse(Long id, String name, String email, String role) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.role = role;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }
}
