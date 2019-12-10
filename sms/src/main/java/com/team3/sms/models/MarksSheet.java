package com.team3.sms.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class MarksSheet {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.EAGER)
	private Student student;

	@ManyToOne(fetch = FetchType.EAGER)
	private Course course;

	private int marks;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public int getMarks() {
		return marks;
	}

	public void setMarks(int marks) {
		this.marks = marks;
	}

	@Override
	public String toString() {
		return "MarksSheet [id=" + id + ", student=" + student + ", course=" + course + ", marks=" + marks + "]";
	}

	public MarksSheet(int id, Student student, Course course, int marks) {
		super();
		this.id = id;
		this.student = student;
		this.course = course;
		this.marks = marks;
	}

	public MarksSheet() {
		super();
	}
}
