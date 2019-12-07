package com.team3.sms.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team3.sms.models.Department;
import com.team3.sms.models.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

	ArrayList<Faculty> findByDepartment(Department department);

}
