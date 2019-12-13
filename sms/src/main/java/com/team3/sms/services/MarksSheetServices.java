package com.team3.sms.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3.sms.models.Course;
import com.team3.sms.models.MarksSheet;
import com.team3.sms.models.Student;
import com.team3.sms.repositories.MarkSheetRepository;

@Service
public class MarksSheetServices {
	@Autowired
	private MarkSheetRepository markRepo;

	public ArrayList<MarksSheet> getCompleteMarksSheet(Student student) {
		return markRepo.findByStudent(student);
	}

	public ArrayList<MarksSheet> getCompleteMarksSheetbyCourse(Course course) {
		return markRepo.findByCourse(course);
	}

	public MarksSheet getMarksSheetById(int mid) {
		return markRepo.findById(mid).get();
	}

	public void saveMarkSheet(MarksSheet marksSheet) {
		markRepo.save(marksSheet);
	}

}