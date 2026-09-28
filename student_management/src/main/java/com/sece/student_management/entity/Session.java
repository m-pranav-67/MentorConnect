package com.sece.student_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mentorship_pair_id")
    private MentorshipPair mentorshipPair;

    @NotNull
    private LocalDateTime scheduledTime;

    private boolean completed = false;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public MentorshipPair getMentorshipPair() { return mentorshipPair; }
    public void setMentorshipPair(MentorshipPair mentorshipPair) { this.mentorshipPair = mentorshipPair; }
    public LocalDateTime getScheduledTime() { return scheduledTime; }
    public void setScheduledTime(LocalDateTime scheduledTime) { this.scheduledTime = scheduledTime; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}
