package com.team3.sms.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3.sms.models.Department;
import com.team3.sms.models.Student;
import com.team3.sms.repositories.StudentRepository;

@Service
public class StudentServices {
	@Autowired
	private StudentRepository studentRepository;

	public void saveStudent(Student student) {
		studentRepository.save(student);
	}

	public Student getStudentbyID(int id) {
		return studentRepository.findById(id).get();
	}

	public ArrayList<Student> getStudentsByDepartment(Department department) {
		return studentRepository.findByDepartment(department);
	}

}
