package com.team3.sms.models;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Student extends User {
	@NotNull
	@ManyToOne
	private Department department;

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@ManyToMany
	private Collection<Semester> semesters;

	public Student(String firstName, String lastName, String gender, String dateofBirth, String address, String email,
			String password, String mobileNo, com.team3.sms.models.Role role, Department department) {
		super(firstName, lastName, gender, dateofBirth, address, email, password, mobileNo, role);

		this.department = department;
	}

	public Student() {
		super();

	}

	@Override
	public String toString() {
		return "Student [department=" + department + ", semesters=" + semesters + ", toString()=" + super.toString()
				+ "]";
	}

}
