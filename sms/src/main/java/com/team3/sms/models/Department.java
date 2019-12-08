package com.team3.sms.models;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	@ManyToMany(mappedBy = "departments")
	private Collection<Announcement> announcements;

	@OneToMany(targetEntity = Student.class, mappedBy = "department")
	private Collection<Student> students;

	@OneToMany(targetEntity = Faculty.class, mappedBy = "department")
	private Collection<Faculty> faculties;

	@OneToMany(targetEntity = Course.class, mappedBy = "department")
	private Collection<Course> courses;

	public Department() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department(String name) {
		super();
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + "]";
	}

}
