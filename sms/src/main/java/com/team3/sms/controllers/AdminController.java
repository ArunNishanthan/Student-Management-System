package com.team3.sms.controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.team3.sms.enums.Role;
import com.team3.sms.models.Course;
import com.team3.sms.models.Department;
import com.team3.sms.models.Faculty;
import com.team3.sms.models.Student;
import com.team3.sms.services.CourseServices;
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
	@Autowired
	private CourseServices courseServices;

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
			facultyServices.saveFaculty(faculty);
			return "redirect:/admin/managecourse/" + faculty.getId();
		}
	}

	@RequestMapping("/viewcoursefaculty")
	public String LoadDepPage(@RequestParam(value = "depid", defaultValue = "0") int depid, Model model) {
		model.addAttribute("departments", formservices.getDepartments());
		if (depid == 0) {
			model.addAttribute("department", new Department());
		} else {
			Department department = formservices.getDepartment(depid);
			model.addAttribute("department", formservices.getDepartment(depid));
			model.addAttribute("faculties", facultyServices.LoadfacBasedonDep(department));
		}
		return "FacultyCourseHome";
	}

	@GetMapping("/managecourse/{id}")
	public String LoadAssignCourse(@PathVariable("id") int id, Model model) {
		Faculty faculty = facultyServices.getFaculty(id);
		ArrayList<Course> coursesFac = new ArrayList<>(faculty.getCourses());
		ArrayList<Course> coursesDb = formservices.getCoursesBasedOnDepartment(faculty.getDepartment());
		ArrayList<Course> coursesbyDep = new ArrayList<Course>();

		for (Course c : coursesDb) {
			if (!coursesFac.contains(c)) {
				coursesbyDep.add(c);
			}
		}
		model.addAttribute("faculty", faculty);
		model.addAttribute("availableCourses", coursesbyDep);

		return "managecourse";
	}

	@GetMapping("/assigncourse/{facid}/{courseid}")
	public String assignCourse(@PathVariable("facid") int facid, @PathVariable("courseid") int courseid) {
		Faculty faculty = facultyServices.getFaculty(facid);
		Course course = courseServices.getCourse(courseid);
		System.out.println(course.getName());
		ArrayList<Course> coursesFac = new ArrayList<>(faculty.getCourses());
		coursesFac.add(course);
		faculty.setCourses(coursesFac);
		facultyServices.saveFaculty(faculty);
		return "redirect:/admin/managecourse/" + facid;
	}

	@GetMapping("/removecourse/{facid}/{courseid}")
	public String removecourse(@PathVariable("facid") int facid, @PathVariable("courseid") int courseid) {
		Faculty faculty = facultyServices.getFaculty(facid);
		Course course = courseServices.getCourse(courseid);
		ArrayList<Course> coursesFac = new ArrayList<>(faculty.getCourses());
		coursesFac.remove(course);
		faculty.setCourses(coursesFac);
		facultyServices.saveFaculty(faculty);
		return "redirect:/admin/managecourse/" + facid;
	}

	@GetMapping("/updatefaculty/{facid}")
	public String getLoginPage(@PathVariable("facid") int facid, Model model) {
		Faculty faculty = facultyServices.getFaculty(facid);
		model.addAttribute("faculty", faculty);
		model.addAttribute("departments", formservices.getDepartments());
		return "FacultyForm";
	}

	@GetMapping("/deletefaculty/{facid}")
	public String deletefaculty(@PathVariable("facid") int facid) {
		Faculty faculty = facultyServices.getFaculty(facid);
		facultyServices.removeFaculty(faculty);
		return "redirect:/admin/viewcoursefaculty?depid=" + faculty.getDepartment().getId();
	}

	@GetMapping("/createcourse")
	public String getCoursePage(Model model) {
		Course course = new Course();
		model.addAttribute("course", course);
		model.addAttribute("departments", formservices.getDepartments());
		return "CourseForm";
	}

	@PostMapping("/checkcourse")
	public String submitCourse(@Valid @ModelAttribute("course") Course course, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("departments", formservices.getDepartments());
			return "CourseForm";
		} else {
			courseServices.saveCourse(course);
			return "redirect:/admin/viewcourse/?depid=" + course.getDepartment().getId();
		}
	}

	@RequestMapping("/viewcourse")
	public String viewcourse(@RequestParam(value = "depid", defaultValue = "0") int depid, Model model) {
		model.addAttribute("departments", formservices.getDepartments());
		if (depid == 0) {
			model.addAttribute("department", new Department());
		} else {
			Department department = formservices.getDepartment(depid);
			model.addAttribute("department", formservices.getDepartment(depid));
			model.addAttribute("courses", courseServices.LoadCourseBasedonDep(department));
		}
		return "CourseHome";
	}

	@GetMapping("/updatecourse/{courseid}")
	public String updatecourse(@PathVariable("courseid") int courseid, Model model) {
		Course course = courseServices.getCourse(courseid);
		model.addAttribute("course", course);
		model.addAttribute("departments", formservices.getDepartments());
		return "CourseForm";
	}

	@GetMapping("/deletecourse/{courseid}")
	public String deletecourse(@PathVariable("courseid") int courseid) {
		Course course = courseServices.getCourse(courseid);
		ArrayList<Faculty> facultiesbyCourse = new ArrayList<>(course.getFaculties());
		for (Faculty faculty : facultiesbyCourse) {
			ArrayList<Course> coursesFac = new ArrayList<>(faculty.getCourses());
			coursesFac.remove(course);
			faculty.setCourses(coursesFac);
			facultyServices.saveFaculty(faculty);
		}
		courseServices.removeCourse(course);
		return "redirect:/admin/viewcourse?depid=" + course.getDepartment().getId();
	}
}
