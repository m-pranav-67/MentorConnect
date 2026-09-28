package com.sece.student_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class MentorshipPair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "alumni_id")
    private Alumni alumni;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Enumerated(EnumType.STRING)
    private MentorshipStatus status = MentorshipStatus.ACTIVE;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Alumni getAlumni() { return alumni; }
    public void setAlumni(Alumni alumni) { this.alumni = alumni; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public MentorshipStatus getStatus() { return status; }
    public void setStatus(MentorshipStatus status) { this.status = status; }
}
