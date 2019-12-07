package com.team3.sms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team3.sms.models.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
