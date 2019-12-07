package com.team3.sms.models;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Student extends User {
	@NotNull
	@ManyToOne
	private Department department;

	@OneToMany
	private Collection<CourseSheet> courseSheets;

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Student(String firstName, String lastName, String gender, String dateofBirth, String address, String email,
			String password, String mobileNo, com.team3.sms.enums.Role role, Department department) {
		super(firstName, lastName, gender, dateofBirth, address, email, password, mobileNo, role);

		this.department = department;
	}

	public Student() {
		super();

	}

}
