package com.team3.sms.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3.sms.models.Course;
import com.team3.sms.models.Department;
import com.team3.sms.models.User;
import com.team3.sms.repositories.AdminRepository;
import com.team3.sms.repositories.CourseRepository;
import com.team3.sms.repositories.DepartmentRepository;
import com.team3.sms.repositories.FacultyRepository;
import com.team3.sms.repositories.StudentRepository;

@Service
public class FormServices {
	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private FacultyRepository facultyRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private AdminRepository adminRepository;

	public ArrayList<Department> getDepartments() {
		return (ArrayList<Department>) departmentRepository.findAll();
	}

	public Department getDepartment(int id) {
		return departmentRepository.findById(id).get();
	}

	public ArrayList<Course> getCoursesBasedOnDepartment(Department department) {
		return courseRepository.findByDepartment(department);
	}

	public User getUserbyEmail(String email) {
		User user = studentRepository.findByEmail(email);
		if (user == null) {
			user = facultyRepository.findByEmail(email);
			if (user == null) {
				user = adminRepository.findByEmail(email);
				if (user == null) {
					return null;
				}
			}
		}

		return user;
	}

}
