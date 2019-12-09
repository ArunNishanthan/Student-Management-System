package com.team3.sms.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class Announcement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotEmpty
	private String message;

	@NotEmpty
	private String date;
	@NotEmpty
	private String facultyname;
	@ManyToOne
	private Course course;

	public Announcement() {
		super();
	}

}
