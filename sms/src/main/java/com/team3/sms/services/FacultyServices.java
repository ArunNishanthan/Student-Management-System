package com.team3.sms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3.sms.models.Faculty;
import com.team3.sms.repositories.FacultyRepository;

@Service
public class FacultyServices {
	@Autowired
	private FacultyRepository facultyRepository;

	public void saveStudent(Faculty faculty) {
		facultyRepository.save(faculty);
	}
}
