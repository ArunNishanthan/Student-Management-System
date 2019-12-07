package com.team3.sms.models;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Faculty extends User {

	private Boolean isAdmin;

	@ManyToOne
	private Department department;

	@ManyToMany
	private Collection<Course> courses;

	@ManyToMany
	private Collection<StaffLeave> staffLeaves;

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
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

	@Override
	public String toString() {
		return "Faculty [isAdmin=" + isAdmin + ", department=" + department + ", courses=" + courses + ", staffLeaves="
				+ staffLeaves + ", toString()=" + super.toString() + "]";
	}

}
