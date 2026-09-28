package com.sece.student_management.repository;

import com.sece.student_management.entity.Alumni;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumniRepository extends JpaRepository<Alumni, Long> {}
