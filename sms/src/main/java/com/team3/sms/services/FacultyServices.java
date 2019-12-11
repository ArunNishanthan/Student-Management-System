package com.team3.sms.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3.sms.models.Department;
import com.team3.sms.models.Faculty;
import com.team3.sms.repositories.FacultyRepository;

@Service
public class FacultyServices {
	@Autowired
	private FacultyRepository facultyRepository;

	public void saveFaculty(Faculty faculty) {
		facultyRepository.save(faculty);
	}

	public ArrayList<Faculty> LoadfacBasedonDep(Department department) {
		return facultyRepository.findByDepartment(department);

	}

	public Faculty getFaculty(int id) {
		return facultyRepository.findById(id).get();
	}

	public void removeFaculty(Faculty faculty) {
		facultyRepository.delete(faculty);
	}

	public long totalFaculty() {
		return facultyRepository.count();
	}

}
