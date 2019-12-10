package com.team3.sms.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team3.sms.models.Department;
import com.team3.sms.models.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	public Student findByEmail(String email);

	public ArrayList<Student> findByDepartment(Department department);
}
