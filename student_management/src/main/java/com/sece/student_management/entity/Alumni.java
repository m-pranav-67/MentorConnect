package com.sece.student_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Alumni {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Valid email is required")
    @NotBlank
    @Column(unique = true)
    private String email;

    @Min(value = 1, message = "Max mentees must be at least 1")
    private int maxMentees;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "alumni_tags",
        joinColumns = @JoinColumn(name = "alumni_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<InterestTag> expertiseTags = new HashSet<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getMaxMentees() { return maxMentees; }
    public void setMaxMentees(int maxMentees) { this.maxMentees = maxMentees; }
    public Set<InterestTag> getExpertiseTags() { return expertiseTags; }
    public void setExpertiseTags(Set<InterestTag> expertiseTags) { this.expertiseTags = expertiseTags; }
}
