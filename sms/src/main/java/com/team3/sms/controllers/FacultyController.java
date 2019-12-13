package com.team3.sms.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.team3.sms.models.Announcement;
import com.team3.sms.models.Course;
import com.team3.sms.models.Faculty;
import com.team3.sms.models.MarksSheet;
import com.team3.sms.models.Student;
import com.team3.sms.services.AnnouncementServices;
import com.team3.sms.services.CourseServices;
import com.team3.sms.services.FacultyServices;
import com.team3.sms.services.MarksSheetServices;
import com.team3.sms.services.StudentServices;

@RequestMapping("/faculty")
@Controller
@SessionAttributes("usersession")
public class FacultyController {
	@Autowired
	private FacultyServices facultyServices;
	@Autowired
	private AnnouncementServices announcementServices;
	@Autowired
	private CourseServices courseServices;
	@Autowired
	private MarksSheetServices markService;
	@Autowired
	private StudentServices studentService;

	@GetMapping("/home")
	public String getHome() {
		return "redirect:/faculty/viewcourse";
	}

	@GetMapping("/makeAnnouncement")
	public String makeAnnouncement(Model model, HttpSession session) {
		Faculty faculty = (Faculty) session.getAttribute("usersession");
		faculty = facultyServices.getFaculty(faculty.getId());
		ArrayList<Announcement> announcements = announcementServices.getAnnouncementbyFaculty(faculty.getFirstName());
		model.addAttribute("announcements", announcements);
		model.addAttribute("courses", faculty.getCourses());
		model.addAttribute("announcement", new Announcement());
		return "announcementForm";
	}

	@PostMapping("/submitannouncement")
	public String submitannouncement(@ModelAttribute("announcement") Announcement announcement, Model model,
			HttpSession session) {
		Faculty faculty = (Faculty) session.getAttribute("usersession");
		faculty = facultyServices.getFaculty(faculty.getId());
		announcement.setFacultyname(faculty.getFirstName());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now = LocalDateTime.now();
		announcement.setDate(dtf.format(now));
		announcementServices.SaveAnnouncement(announcement);
		model.addAttribute("announcement", announcement);
		return "redirect:/faculty/makeAnnouncement";
	}

	@GetMapping("/viewcourse")
	public String GetCoursed(Model model, HttpSession session) {
		Faculty faculty = (Faculty) session.getAttribute("usersession");
		faculty = facultyServices.getFaculty(faculty.getId());
		model.addAttribute("courses", faculty.getCourses());
		return "FacultyCourses";
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

		return "FacultyViewStudent";
	}

	@GetMapping("/inputmarks/{courseid}")
	public String managemarks(@PathVariable("courseid") int courseid, Model model) {
		Course course = courseServices.getCourse(courseid);
		ArrayList<Student> studentByCourse = new ArrayList<>(course.getStudents());
		model.addAttribute("studentByCourse", studentByCourse);
		return "FacultyInputMarks";
	}

	@RequestMapping("/submitmarks/{marks}/{courseid}")
	public String submitmarks(@PathVariable("courseid") int courseid, @PathVariable("marks") String marks,
			Model model) {
		marks = marks.substring(1);
		model.addAttribute("courseid", courseid);
		List<String> marksArray = Arrays.asList(marks.split("\\s*,\\s*"));
		Course course = courseServices.getCourse(courseid);
		ArrayList<Student> studentByCourse = new ArrayList<>(course.getStudents());
		int i = 0;
		for (Student student : studentByCourse) {
			MarksSheet mark = new MarksSheet();
			mark.setCourse(course);
			mark.setStudent(student);
			mark.setMarks(Integer.parseInt(marksArray.get(i)));
			markService.saveMarkSheet(mark);

			ArrayList<Course> courses = new ArrayList<>(student.getCourses());

			courses.remove(course);
			student.setCourses(courses);
			studentService.saveStudent(student);
			i++;
		}

		return "<h1>Hello</h1>";
	}

	@GetMapping("/viewmarksheet/{courseid}")
	public String viewmarksheet(@PathVariable("courseid") int courseid, Model model) {
		Course course = courseServices.getCourse(courseid);
		ArrayList<MarksSheet> markSheet = markService.getCompleteMarksSheetbyCourse(course);
		model.addAttribute("courseid", courseid);
		model.addAttribute("markSheet", markSheet);
		return "FacultyViewMarks";
	}

	@RequestMapping("/updatemarks/{marks}/{courseid}")
	public String updatemarks(@PathVariable("courseid") int courseid, @PathVariable("marks") String marks,
			Model model) {
		marks = marks.substring(1);
		List<String> marksArray = Arrays.asList(marks.split("\\s*,\\s*"));
		Course course = courseServices.getCourse(courseid);
		ArrayList<MarksSheet> markSheet = markService.getCompleteMarksSheetbyCourse(course);

		int i = 0;
		for (MarksSheet mark : markSheet) {
			mark.setMarks(Integer.parseInt(marksArray.get(i)));

			markService.saveMarkSheet(mark);
			i++;
		}
		model.addAttribute("courseid", courseid);
		return "<h1>Hello</h1>";
	}

	@GetMapping("/gradereport")
	public String getgradereport(Model model, HttpSession session) {
		Faculty faculty = (Faculty) session.getAttribute("usersession");
		faculty = facultyServices.getFaculty(faculty.getId());
		ArrayList<Course> courses = new ArrayList<>(faculty.getCourses());
		model.addAttribute("courses", courses);
		return "StudentGradeReport";
	}

	@RequestMapping("/getgradereport")
	public String getStuGradeReport(@RequestParam(value = "courseid", defaultValue = "0") int cid,
			@RequestParam(value = "marks", defaultValue = "-1") int mark, Model model, HttpSession session) {
		Map<Integer, String> hm = new HashMap<Integer, String>();
		hm.put(85, "A+");
		hm.put(84, "A");
		hm.put(79, "A-");
		hm.put(74, "B+");
		hm.put(69, "B");
		hm.put(64, "B-");
		hm.put(59, "C+");
		hm.put(54, "C");
		hm.put(49, "D+");
		hm.put(44, "D");
		hm.put(40, "F");
		String grade = hm.get(mark);

		Faculty faculty = (Faculty) session.getAttribute("usersession");
		faculty = facultyServices.getFaculty(faculty.getId());
		ArrayList<Course> courses = new ArrayList<>(faculty.getCourses());

		Course course = courseServices.getCourse(cid);
		ArrayList<MarksSheet> markByCourse = markService.getCompleteMarksSheetbyCourse(course);
		ArrayList<Student> selectedstudent = new ArrayList<>();
		ArrayList<Double> marksList = new ArrayList<>();

		if (mark >= 85) {
			for (MarksSheet ms : markByCourse) {
				if (ms.getMarks() >= 85) {
					selectedstudent.add(ms.getStudent());
					marksList.add((double) ms.getMarks());
				}
			}
		} else if (mark <= 40) {
			for (MarksSheet ms : markByCourse) {
				if (ms.getMarks() < 40) {
					selectedstudent.add(ms.getStudent());
					marksList.add((double) ms.getMarks());
				}
			}
		} else {
			for (MarksSheet ms : markByCourse) {
				if (ms.getMarks() >= mark - 4 && ms.getMarks() <= mark) {
					selectedstudent.add(ms.getStudent());
					marksList.add((double) ms.getMarks());
				}
			}
		}
		for (Student student : selectedstudent) {
			System.out.println(student.getFirstName());
		}

		model.addAttribute("courses", courses);
		model.addAttribute("students", selectedstudent).addAttribute("marksList", marksList)
				.addAttribute("coursename", course.getName()).addAttribute("grade", grade);
		return "StudentGradeReport";
	}

	@GetMapping("/studentsreport")
	public String getstudentsreport(Model model, HttpSession session) {
		Faculty faculty = (Faculty) session.getAttribute("usersession");
		faculty = facultyServices.getFaculty(faculty.getId());
		ArrayList<Course> courses = new ArrayList<>(faculty.getCourses());

		model.addAttribute("courses", courses);
		return "StudentsReport";
	}

	@RequestMapping("/getstudentsreport")
	public String getStudentsReport(@RequestParam(value = "courseid", defaultValue = "0") int cid, Model model,
			HttpSession session) {
		Faculty faculty = (Faculty) session.getAttribute("usersession");
		faculty = facultyServices.getFaculty(faculty.getId());
		ArrayList<Course> courses = new ArrayList<>(faculty.getCourses());

		Course course = courseServices.getCourse(cid);
		ArrayList<MarksSheet> markByCourse = markService.getCompleteMarksSheetbyCourse(course);
		ArrayList<Student> studentsList = new ArrayList<>();
		ArrayList<Double> marksList = new ArrayList<>();
		for (MarksSheet ms : markByCourse) {
			studentsList.add(ms.getStudent());
			marksList.add((double) ms.getMarks());
		}
		model.addAttribute("studentsList", studentsList).addAttribute("marksList", marksList)
				.addAttribute("coursename", course.getName()).addAttribute("courses", courses);
		return "StudentsReport";
	}

	@GetMapping("/viewstudent/{sid}")
	public String viewstudent(@PathVariable("sid") int sid, Model model) {
		Student student = studentService.getStudentbyID(sid);
		ArrayList<MarksSheet> completeCourse = markService.getCompleteMarksSheet(student);
		model.addAttribute("student", student);
		model.addAttribute("completeCourses", completeCourse);
		return "ViewStudentFull";
	}
}