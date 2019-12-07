package com.team3.sms.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.team3.sms.enums.CourseStatus;

@Entity
public class CourseSheet {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private Student student;

	@ManyToOne
	private Course course;

	private int marks;

	private CourseStatus coursestatus;
}
