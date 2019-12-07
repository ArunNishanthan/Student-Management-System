package com.team3.sms.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3.sms.models.Department;
import com.team3.sms.repositories.DepartmentRepository;

@Service
public class FormServices {
	@Autowired
	private DepartmentRepository departmentRepository;

	public ArrayList<Department> getDepartments() {
		return (ArrayList<Department>) departmentRepository.findAll();
	}
}
