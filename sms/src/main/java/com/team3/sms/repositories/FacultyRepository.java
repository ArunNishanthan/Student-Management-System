package com.team3.sms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team3.sms.models.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

}
