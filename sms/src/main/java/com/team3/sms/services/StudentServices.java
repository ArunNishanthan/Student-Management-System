package com.team3.sms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3.sms.models.Student;
import com.team3.sms.repositories.StudentRepository;

@Service
public class StudentServices {
	@Autowired
	private StudentRepository studentRepository;

	public void saveStudent(Student student) {
		studentRepository.save(student);
	}
}
