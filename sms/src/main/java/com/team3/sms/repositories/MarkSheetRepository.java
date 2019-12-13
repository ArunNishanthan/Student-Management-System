package com.team3.sms.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team3.sms.models.Course;
import com.team3.sms.models.MarksSheet;
import com.team3.sms.models.Student;

public interface MarkSheetRepository extends JpaRepository<MarksSheet, Integer> {
	public ArrayList<MarksSheet> findByStudent(Student student);

	public ArrayList<MarksSheet> findByCourse(Course course);

}