package com.team3.sms.models;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String name;

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

}
