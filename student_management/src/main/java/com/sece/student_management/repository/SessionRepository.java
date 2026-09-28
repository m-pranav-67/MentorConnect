package com.sece.student_management.repository;

import com.sece.student_management.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {}
