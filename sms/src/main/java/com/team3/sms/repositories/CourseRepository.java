package com.team3.sms.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team3.sms.models.Course;
import com.team3.sms.models.Department;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	ArrayList<Course> findByDepartment(Department department);
}
