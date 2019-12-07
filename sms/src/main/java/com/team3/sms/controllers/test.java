package com.team3.sms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.team3.sms.repositories.DepartmentRepository;
import com.team3.sms.repositories.StudentRepository;

@Controller
public class test {
	@Autowired
	private StudentRepository srepo;
	@Autowired
	private DepartmentRepository drepo;

	@GetMapping("/")
	public String list(Model model) {
//		Department d = new Department("ff");
//		Student s = new Student("firstName", "l", 'm', "k", "s", "s", "d", 11l, Role.ISSTUDENT, d);
//		System.out.println(s);
//		drepo.save(d);
//		srepo.save(s);
//		model.addAttribute("view", s);
		return "studenthome";
	}
}
