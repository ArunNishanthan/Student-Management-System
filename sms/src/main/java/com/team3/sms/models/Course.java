package com.team3.sms.models;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.springframework.lang.Nullable;

@Entity
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private int capacity;
	@ManyToOne
	private Department department;
	private int credits;
	@Nullable
	private String grade;

	@ManyToMany
	private Collection<Faculty> faculties;

}
