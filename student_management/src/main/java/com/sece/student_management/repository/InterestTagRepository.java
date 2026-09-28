package com.sece.student_management.repository;

import com.sece.student_management.entity.InterestTag;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InterestTagRepository extends JpaRepository<InterestTag, Long> {
    Optional<InterestTag> findByName(String name);
}
