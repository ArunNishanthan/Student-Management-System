package com.team3.sms.models;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
	@ManyToMany
	private Collection<Department> departments;

	public Announcement(int id, @NotEmpty String message, @NotEmpty String date, @NotEmpty String facultyname,
			Collection<Department> departments) {
		super();
		this.id = id;
		this.message = message;
		this.date = date;
		this.facultyname = facultyname;
		this.departments = departments;
	}

	public Announcement() {
		super();
	}

	@Override
	public String toString() {
		return "Announcement [id=" + id + ", message=" + message + ", date=" + date + ", facultyname=" + facultyname
				+ ", departments=" + departments + "]";
	}

}
