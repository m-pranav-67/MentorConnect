package com.sece.student_management.service;

import com.sece.student_management.entity.*;
import com.sece.student_management.exception.BusinessException;
import com.sece.student_management.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class MentorshipService {

    private final AlumniRepository alumniRepository;
    private final StudentRepository studentRepository;
    private final MentorshipPairRepository mentorshipPairRepository;
    private final InterestTagRepository interestTagRepository;
    private final SessionRepository sessionRepository;

    public MentorshipService(AlumniRepository alumniRepository, StudentRepository studentRepository, 
                             MentorshipPairRepository mentorshipPairRepository, 
                             InterestTagRepository interestTagRepository, 
                             SessionRepository sessionRepository) {
        this.alumniRepository = alumniRepository;
        this.studentRepository = studentRepository;
        this.mentorshipPairRepository = mentorshipPairRepository;
        this.interestTagRepository = interestTagRepository;
        this.sessionRepository = sessionRepository;
    }

    private Set<InterestTag> resolveTags(Set<InterestTag> tags) {
        Set<InterestTag> resolved = new HashSet<>();
        for (InterestTag tag : tags) {
            interestTagRepository.findByName(tag.getName())
                    .ifPresentOrElse(resolved::add, () -> resolved.add(interestTagRepository.save(tag)));
        }
        return resolved;
    }

    public Alumni registerAlumni(Alumni alumni) {
        alumni.setExpertiseTags(resolveTags(alumni.getExpertiseTags()));
        return alumniRepository.save(alumni);
    }

    public Student registerStudent(Student student) {
        student.setInterestTags(resolveTags(student.getInterestTags()));
        return studentRepository.save(student);
    }

    public List<MentorMatchDTO> getMentorSuggestions(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Student not found"));

        Set<InterestTag> studentTags = student.getInterestTags();
        List<Alumni> allAlumni = alumniRepository.findAll();

        return allAlumni.stream()
                .map(alumni -> {
                    long sharedCount = alumni.getExpertiseTags().stream()
                            .filter(tag -> studentTags.stream().anyMatch(st -> st.getName().equals(tag.getName())))
                            .count();
                    return new MentorMatchDTO(alumni, sharedCount);
                })
                .filter(match -> match.getSharedTagsCount() > 0)
                // Filter out mentors who are already at capacity
                .filter(match -> mentorshipPairRepository.countByAlumniIdAndStatus(match.getMentor().getId(), MentorshipStatus.ACTIVE) < match.getMentor().getMaxMentees())
                .sorted(Comparator.comparingLong(MentorMatchDTO::getSharedTagsCount).reversed())
                .collect(Collectors.toList());
    }

    public MentorshipPair createMentorship(Long alumniId, Long studentId) {
        if (mentorshipPairRepository.existsByAlumniIdAndStudentId(alumniId, studentId)) {
            throw new BusinessException("Mentorship already exists");
        }

        Alumni alumni = alumniRepository.findById(alumniId)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Alumni not found"));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Student not found"));

        long activeMentees = mentorshipPairRepository.countByAlumniIdAndStatus(alumniId, MentorshipStatus.ACTIVE);
        if (activeMentees >= alumni.getMaxMentees()) {
            throw new BusinessException("Mentor capacity reached");
        }

        MentorshipPair pair = new MentorshipPair();
        pair.setAlumni(alumni);
        pair.setStudent(student);
        pair.setStatus(MentorshipStatus.ACTIVE);
        return mentorshipPairRepository.save(pair);
    }

    public Session createSession(Long pairId, Session session) {
        MentorshipPair pair = mentorshipPairRepository.findById(pairId)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Mentorship not found"));
        
        session.setMentorshipPair(pair);
        session.setCompleted(false);
        return sessionRepository.save(session);
    }
    
    public Session completeSession(Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Session not found"));
        session.setCompleted(true);
        return sessionRepository.save(session);
    }
}
