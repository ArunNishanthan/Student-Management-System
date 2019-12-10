package com.team3.sms.models;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Student extends User {
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	private Department department;

	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<Course> courses;

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Collection<Course> getCourses() {
		return courses;
	}

	public void setCourses(Collection<Course> courses) {
		this.courses = courses;
	}

	public Student() {
		super();

	}

	@Override
	public String toString() {
		return "Student [department=" + department + ", courses=" + courses + ", toString()=" + super.toString() + "]";
	}

}
