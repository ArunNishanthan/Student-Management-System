package com.team3.sms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team3.sms.models.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
