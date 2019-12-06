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
}
