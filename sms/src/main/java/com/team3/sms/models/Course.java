package com.team3.sms.models;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	private int id;
	@NotEmpty
	private String name;
	@NotNull
	private int capacity;

	@ManyToOne
	@NotNull
	private Department department;

	@ManyToMany
	private Collection<Student> students;

	@ManyToMany(mappedBy = "courses")
	private Collection<Faculty> faculties;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Collection<Student> getStudents() {
		return students;
	}

	public void setStudents(Collection<Student> students) {
		this.students = students;
	}

	public Collection<Faculty> getFaculties() {
		return faculties;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", capacity=" + capacity + ", department=" + department
				+ ", students=" + students + ", faculties=" + faculties + "]";
	}

	public void setFaculties(Collection<Faculty> faculties) {
		this.faculties = faculties;
	}

}
