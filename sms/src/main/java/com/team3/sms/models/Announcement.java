package com.team3.sms.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class Announcement implements Comparable<Announcement> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotEmpty
	@Column(length = 500)
	private String message;

	private String date;
	private String facultyname;
	@ManyToOne
	private Course course;

	public Announcement() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFacultyname() {
		return facultyname;
	}

	public void setFacultyname(String facultyname) {
		this.facultyname = facultyname;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@Override
	public String toString() {
		return "Announcement [id=" + id + ", message=" + message + ", date=" + date + ", facultyname=" + facultyname
				+ ", course=" + course.getId() + "]";
	}

	@Override
	public int compareTo(Announcement announcement) {
		Integer One = this.id;
		Integer two = announcement.id;
		return One.compareTo(two);
	}

}
