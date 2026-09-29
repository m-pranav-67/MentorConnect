package com.sece.student_management.repository;

import com.sece.student_management.entity.MentorshipPair;
import com.sece.student_management.entity.MentorshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorshipPairRepository extends JpaRepository<MentorshipPair, Long> {
    long countByAlumniIdAndStatus(Long alumniId, MentorshipStatus status);
    boolean existsByAlumniIdAndStudentId(Long alumniId, Long studentId);
    java.util.List<MentorshipPair> findByStudentId(Long studentId);
}
