package com.team3.sms.models;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Student extends User {

	@ManyToOne
	private Department department;
	@ManyToMany
	private Collection<Semester> semesters;

}
