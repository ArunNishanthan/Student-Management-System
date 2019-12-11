package com.team3.sms.controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.team3.sms.enums.Role;
import com.team3.sms.models.Admin;
import com.team3.sms.models.Course;
import com.team3.sms.models.Department;
import com.team3.sms.models.Faculty;
import com.team3.sms.models.MarksSheet;
import com.team3.sms.models.Student;
import com.team3.sms.services.AdminServices;
import com.team3.sms.services.CourseServices;
import com.team3.sms.services.FacultyServices;
import com.team3.sms.services.FormServices;
import com.team3.sms.services.MarksSheetServices;
import com.team3.sms.services.StudentServices;

@RequestMapping("/admin")
@SessionAttributes("usersession")
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
	@Autowired
	private AdminServices adminServices;
	@Autowired
	private MarksSheetServices markService;

	@GetMapping("/home")
	public String getHome(Model model) {
		model.addAttribute("students", studentservices.totalStudents());
		model.addAttribute("courses", courseServices.totalCourse());
		model.addAttribute("lecturers", facultyServices.totalFaculty());
		model.addAttribute("admins", adminServices.totalAdmin());
		return "AdminHome";
	}

	@GetMapping("/createuser")
	public String getuserform(Model model) {
		model.addAttribute("faculty", new Faculty());
		model.addAttribute("student", new Student());
		model.addAttribute("admin", new Admin());
		model.addAttribute("departments", formservices.getDepartments());
		return "UserForm";
	}

	@PostMapping("/checkstudent")
	public String submitStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors());
			model.addAttribute("faculty", new Faculty());
			model.addAttribute("admin", new Admin());
			model.addAttribute("departments", formservices.getDepartments());
			if (student.getId() == 0) {
				return "UserForm";
			}
			return "StudentForm";
		} else {
			if (student.getId() == 0) {
				student.setPassword(student.getFirstName());

			} else {
				Student studentDb = studentservices.getStudentbyID(student.getId());
				student.setPassword(studentDb.getPassword());
				student.setCourses(studentDb.getCourses());
			}
			student.setRole(Role.ISSTUDENT);
			studentservices.saveStudent(student);

			return "redirect:/admin/studentViewDepartment/?depid=" + student.getDepartment().getId();
		}
	}

	@GetMapping("/deletestudent/{studentid}")
	public String deletestudent(@PathVariable("studentid") int studentid) {
		Student student = studentservices.getStudentbyID(studentid);
		studentservices.deleteStudent(student);
		return "redirect:/admin/studentViewDepartment/?depid=" + student.getDepartment().getId();
	}

	@GetMapping("/updatestudent/{studentid}")
	public String updatestudent(@PathVariable("studentid") int studentid, Model model) {
		Student student = studentservices.getStudentbyID(studentid);
		model.addAttribute("student", student);
		model.addAttribute("departments", formservices.getDepartments());
		return "StudentForm";
	}

	@PostMapping("/checkfaculty")
	public String submitFaculty(@Valid @ModelAttribute("faculty") Faculty faculty, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("departments", formservices.getDepartments());
			model.addAttribute("student", new Student());
			model.addAttribute("admin", new Admin());
			if (faculty.getId() == 0) {
				return "UserForm";
			}
			return "FacultyForm";
		} else {
			if (faculty.getId() == 0) {
				faculty.setPassword(faculty.getFirstName());
			} else {
				Faculty facultyDb = facultyServices.getFaculty(faculty.getId());
				faculty.setPassword(facultyDb.getPassword());
				faculty.setCourses(facultyDb.getCourses());
				faculty.setStaffLeaves(faculty.getStaffLeaves());
			}

			faculty.setRole(Role.ISFACULTY);
			facultyServices.saveFaculty(faculty);
			return "redirect:/admin/managecourse/" + faculty.getId();
		}
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

	@PostMapping("/checkadmin")
	public String checkadmin(@Valid @ModelAttribute("admin") Admin admin, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("faculty", new Faculty());
			model.addAttribute("student", new Student());
			model.addAttribute("departments", formservices.getDepartments());
			return "UserForm";
		} else {
			admin.setPassword(admin.getFirstName());
			admin.setRole(Role.ISADMIN);
			adminServices.saveAdmin(admin);
			return "redirect:/admin/createuser";
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
		ArrayList<Student> studentsbyCourse = new ArrayList<Student>(course.getStudents());

		for (Student student : studentsbyCourse) {
			ArrayList<Course> coursesstu = new ArrayList<>(student.getCourses());
			coursesstu.remove(course);
			student.setCourses(coursesstu);
			studentservices.saveStudent(student);
		}

		for (Faculty faculty : facultiesbyCourse) {
			ArrayList<Course> coursesFac = new ArrayList<>(faculty.getCourses());
			coursesFac.remove(course);
			faculty.setCourses(coursesFac);
			facultyServices.saveFaculty(faculty);
		}
		courseServices.removeCourse(course);
		return "redirect:/admin/viewcourse?depid=" + course.getDepartment().getId();
	}

	@GetMapping("/viewstudent/{courseid}/{pageNo}")
	public String viewstudent(@PathVariable("courseid") int courseid, Model model,
			@PathVariable(value = "pageNo", required = false) String pageNum) {
		Course course = courseServices.getCourse(courseid);
		ArrayList<Student> studentByCourse = new ArrayList<>(course.getStudents());
		PagedListHolder page = new PagedListHolder(studentByCourse);
		page.setPageSize(10);
		int pageNo = 0;
		if (pageNum != null) {
			pageNo = Integer.parseInt(pageNum);
		}
		page.setPage(pageNo);
		model.addAttribute("courseid", courseid);
		model.addAttribute("currentpage", pageNo);
		model.addAttribute("pageCount", page.getPageCount());
		model.addAttribute("studentByCourse", page.getPageList());

		return "ViewStudent";
	}

	@RequestMapping("/viewstaff/{courseid}")
	public String viewstaff(@PathVariable("courseid") int courseid, Model model) {
		Course course = courseServices.getCourse(courseid);
		ArrayList<Faculty> assignedFaculty = new ArrayList<>(course.getFaculties());
		ArrayList<Faculty> allFacultyByDepartment = new ArrayList<>(
				facultyServices.LoadfacBasedonDep(course.getDepartment()));
		ArrayList<Faculty> availablefaculties = new ArrayList<>();
		for (Faculty faculty : allFacultyByDepartment) {
			if (!assignedFaculty.contains(faculty)) {
				availablefaculties.add(faculty);
			}
		}
		model.addAttribute("course", course);
		model.addAttribute("availableFaculties", availablefaculties);
		return "StaffHome";
	}

	@GetMapping("/assignfaculty/{facultyid}/{courseid}")
	public String assignfaculty(@PathVariable("facultyid") int facultyid, @PathVariable("courseid") int courseid,
			Model model) {
		Course course = courseServices.getCourse(courseid);
		Faculty faculty = facultyServices.getFaculty(facultyid);
		ArrayList<Course> courses = new ArrayList<>(faculty.getCourses());
		courses.add(course);
		faculty.setCourses(courses);
		facultyServices.saveFaculty(faculty);
		return "redirect:/admin/viewstaff/" + courseid;
	}

	@GetMapping("/removefaculty/{facultyid}/{courseid}")
	public String removefaculty(@PathVariable("facultyid") int facultyid, @PathVariable("courseid") int courseid,
			Model model) {
		Course course = courseServices.getCourse(courseid);
		Faculty faculty = facultyServices.getFaculty(facultyid);
		ArrayList<Course> courses = new ArrayList<>(faculty.getCourses());
		courses.remove(course);
		faculty.setCourses(courses);
		facultyServices.saveFaculty(faculty);
		return "redirect:/admin/viewstaff/" + courseid;
	}

	@GetMapping("/removestudent/{courseid}/{studentid}/{pageNo}")
	public String RemoveStudentCourse(@PathVariable("courseid") int courseid, @PathVariable("studentid") int studentid,
			@PathVariable("pageNo") int pageNo, Model model) {
		Course course = courseServices.getCourse(courseid);
		Student student = studentservices.getStudentbyID(studentid);
		ArrayList<Course> studentByCourse = new ArrayList<>(student.getCourses());
		studentByCourse.remove(course);
		student.setCourses(studentByCourse);
		studentservices.saveStudent(student);
		return "redirect:/admin/viewstudent/" + courseid + "/" + pageNo;
	}

	@RequestMapping("/studentViewDepartment")
	public String studentViewDepartment(@RequestParam(value = "depid", defaultValue = "0") int depid, Model model) {
		model.addAttribute("departments", formservices.getDepartments());
		if (depid == 0) {
			model.addAttribute("department", new Department());
		} else {
			Department department = formservices.getDepartment(depid);
			model.addAttribute("department", department);
			model.addAttribute("students", studentservices.getStudentsByDepartment(department));
		}
		return "StudentCourseHome";
	}

	@GetMapping("/viewstudentdetails/{sid}")
	public String viewstudentdetails(@PathVariable("sid") int sid, Model model) {
		Student student = studentservices.getStudentbyID(sid);
		ArrayList<MarksSheet> completeCourse = markService.getCompleteMarksSheet(student);
		model.addAttribute("student", student);
		model.addAttribute("completeCourses", completeCourse);
		return "ViewStudentDetails";
	}

	@GetMapping("/removeEnrollCourseFromAdmin/{cid}/{sid}")
	public String removeEnrollCourseFromAdmin(@PathVariable("cid") int cid, @PathVariable("sid") int sid) {

		removeEnrollCourseMethod(cid, sid);
		return "redirect:/admin/viewstudentdetails/" + sid;
	}

	public void removeEnrollCourseMethod(int cid, int sid) {

		Student student = new Student();
		student = studentservices.getStudentbyID(sid);
		Course course = new Course();
		course = courseServices.getCourse(cid);
		ArrayList<Course> courseStudentList = new ArrayList<>(student.getCourses());
		if (courseStudentList.contains(course)) {
			courseStudentList.remove(course);
		}

		student.setCourses(courseStudentList);
		studentservices.saveStudent(student);
	}

	@RequestMapping("/viewadmins")
	public String LoadAdmins(Model model) {

		return "FacultyCourseHome";
	}

}
