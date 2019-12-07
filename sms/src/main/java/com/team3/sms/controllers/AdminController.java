package com.team3.sms.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team3.sms.enums.Role;
import com.team3.sms.models.Faculty;
import com.team3.sms.models.Student;
import com.team3.sms.services.FacultyServices;
import com.team3.sms.services.FormServices;
import com.team3.sms.services.StudentServices;

@RequestMapping("/admin")
@Controller
public class AdminController {

	@Autowired
	private StudentServices studentservices;
	@Autowired
	private FormServices formservices;
	@Autowired
	private FacultyServices facultyServices;

	@GetMapping("/createstudent")
	public String getStudentPage(Model model) {
		Student student = new Student();
		model.addAttribute("student", student);
		model.addAttribute("departments", formservices.getDepartments());
		return "StudentForm";
	}

	@GetMapping("/createfaculty")
	public String getLoginPage(Model model) {
		Faculty faculty = new Faculty();
		model.addAttribute("faculty", faculty);
		model.addAttribute("departments", formservices.getDepartments());
		return "FacultyForm";
	}

	@PostMapping("/checkstudent")
	public String submitStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("departments", formservices.getDepartments());
			return "StudentForm";
		} else {
			student.setPassword(student.getFirstName());
			student.setRole(Role.ISSTUDENT);
			studentservices.saveStudent(student);

			return "redirect:/createstudent";
		}
	}

	@PostMapping("/checkfaculty")
	public String submitFaculty(@Valid @ModelAttribute("faculty") Faculty faculty, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("departments", formservices.getDepartments());
			return "FacultyForm";
		} else {
			faculty.setPassword(faculty.getFirstName());
			faculty.setRole(Role.ISFACULTY);
			System.out.println(faculty);
			facultyServices.saveStudent(faculty);
			return "redirect:/admin/createstudent";
		}
	}

	@GetMapping("/assigncourse")
	public String LoadAssignPage(Model model) {
		return "AssignCourseToFaculty";
	}
}
