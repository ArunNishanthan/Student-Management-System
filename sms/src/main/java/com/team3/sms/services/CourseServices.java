package com.team3.sms.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3.sms.models.Course;
import com.team3.sms.models.Department;
import com.team3.sms.repositories.CourseRepository;

@Service
public class CourseServices {
	@Autowired
	private CourseRepository courseRepository;

	public Course getCourse(int id) {
		return courseRepository.findById(id).get();
	}

	public void saveCourse(Course course) {
		courseRepository.save(course);
	}

	public ArrayList<Course> LoadCourseBasedonDep(Department department) {
		return courseRepository.findByDepartment(department);
	}

	public void removeCourse(Course course) {
		courseRepository.delete(course);
	}

	public long totalCourse() {
		return courseRepository.count();
	}

}
