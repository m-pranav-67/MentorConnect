package com.sece.student_management.controller;

import com.sece.student_management.entity.Alumni;
import com.sece.student_management.entity.MentorshipPair;
import com.sece.student_management.entity.Session;
import com.sece.student_management.entity.Student;
import com.sece.student_management.service.MentorMatchDTO;
import com.sece.student_management.service.MentorshipService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class MentorConnectController {

    private final MentorshipService mentorshipService;

    public MentorConnectController(MentorshipService mentorshipService) {
        this.mentorshipService = mentorshipService;
    }

    @PostMapping("/alumni")
    public ResponseEntity<Alumni> registerAlumni(@Valid @RequestBody Alumni alumni) {
        return ResponseEntity.ok(mentorshipService.registerAlumni(alumni));
    }

    @PostMapping("/students")
    public ResponseEntity<Student> registerStudent(@Valid @RequestBody Student student) {
        return ResponseEntity.ok(mentorshipService.registerStudent(student));
    }

    @GetMapping("/students/{studentId}/matches")
    public ResponseEntity<List<MentorMatchDTO>> getMentorSuggestions(@PathVariable Long studentId) {
        return ResponseEntity.ok(mentorshipService.getMentorSuggestions(studentId));
    }

    @PostMapping("/mentorship")
    public ResponseEntity<MentorshipPair> createMentorship(@RequestParam Long alumniId, @RequestParam Long studentId) {
        return ResponseEntity.ok(mentorshipService.createMentorship(alumniId, studentId));
    }
    
    @GetMapping("/students/{studentId}/mentorships")
    public ResponseEntity<List<MentorshipPair>> getStudentMentorships(@PathVariable Long studentId) {
        return ResponseEntity.ok(mentorshipService.getStudentMentorships(studentId));
    }

    @PostMapping("/mentorship/{pairId}/sessions")
    public ResponseEntity<Session> createSession(@PathVariable Long pairId, @Valid @RequestBody Session session) {
        return ResponseEntity.ok(mentorshipService.createSession(pairId, session));
    }
    
    @PutMapping("/sessions/{sessionId}/complete")
    public ResponseEntity<Session> completeSession(@PathVariable Long sessionId) {
        return ResponseEntity.ok(mentorshipService.completeSession(sessionId));
    }
}
