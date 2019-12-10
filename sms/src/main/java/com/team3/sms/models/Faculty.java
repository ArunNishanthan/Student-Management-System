package com.team3.sms.models;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Faculty extends User {

	@ManyToOne(fetch = FetchType.EAGER)
	private Department department;

	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<Course> courses;

	public Collection<Course> getCourses() {
		return courses;
	}

	public void setCourses(Collection<Course> courses) {
		this.courses = courses;
	}

	@ManyToMany
	private Collection<StaffLeave> staffLeaves;

	public Collection<StaffLeave> getStaffLeaves() {
		return staffLeaves;
	}

	public void setStaffLeaves(Collection<StaffLeave> staffLeaves) {
		this.staffLeaves = staffLeaves;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Faculty() {
		super();
		// TODO Auto-generated constructor stub
	}

}
