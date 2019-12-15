package com.team3.sms.controllers;

import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.team3.sms.models.Announcement;
import com.team3.sms.models.Course;
import com.team3.sms.models.MarksSheet;
import com.team3.sms.models.Password;
import com.team3.sms.models.Student;
import com.team3.sms.services.AnnouncementServices;
import com.team3.sms.services.CourseServices;
import com.team3.sms.services.MarksSheetServices;
import com.team3.sms.services.StudentServices;

@RequestMapping("/student")
@SessionAttributes("usersession")

@Controller
public class StudentController {
	@Autowired
	private StudentServices stuService;
	@Autowired
	private CourseServices courseService;
	@Autowired
	private MarksSheetServices markService;
	@Autowired
	private AnnouncementServices announcementServices;

	@GetMapping("/home")
	public String Home(Model model, HttpSession session) {
		Student userstudent = (Student) session.getAttribute("usersession");
		Student student = stuService.getStudentbyID(userstudent.getId());
		ArrayList<Course> courseList = new ArrayList<Course>(student.getCourses());
		ArrayList<Announcement> announcements = new ArrayList<Announcement>();
		for (Course course : courseList) {
			announcements.addAll(announcementServices.getAnnouncementbyCourse(course));
		}
		Collections.sort(announcements, Collections.reverseOrder());
		model.addAttribute("announcements", announcements);
		return "studenthome";
	}

	@GetMapping("/choseAvailableCourse")
	public String LoadAssignCourse(Model model, HttpSession session) {
		Student userstudent = (Student) session.getAttribute("usersession");
		Student student = stuService.getStudentbyID(userstudent.getId());
		ArrayList<Course> courseList = new ArrayList<Course>();
		courseList = courseService.LoadCourseBasedonDep(student.getDepartment());// course list in login user's
		ArrayList<MarksSheet> completeCourseList = new ArrayList<MarksSheet>();
		completeCourseList = markService.getCompleteMarksSheet(student); // department
		ArrayList<Course> availCourse = new ArrayList<Course>();
		ArrayList<Course> unAvailCourse = new ArrayList<Course>();
		ArrayList<Course> uncompleteCourseList = new ArrayList<Course>(courseList);

		if (courseList.size() != 0) {
			for (Course c : courseList) {
				if (completeCourseList.size() != 0) {
					for (MarksSheet complete : completeCourseList) {
						if (complete.getCourse().getId() == c.getId()) {
							uncompleteCourseList.remove(c);
						}
					}
				}

			}

			for (Course course : uncompleteCourseList) {// unavailabel course
				System.out.println("student.getCourses().size()");
				if (course.getCapacity() <= course.getStudents().size() || student.getCourses().size() >= 4) {
					if (!course.getStudents().contains(student))
						unAvailCourse.add(course);
				} else if (!student.getCourses().contains(course)) {
					availCourse.add(course);
				}

			}
		}

		System.out.println(student.getCourses().size());
		model.addAttribute("student", student);
		model.addAttribute("availableCourses", availCourse);
		model.addAttribute("unAvailableCourses", unAvailCourse);
		return "ChooseStudentCourse";
	}

	@GetMapping("/assignChosenCourse/{cid}")
	public String assignChosenCourse(@PathVariable("cid") int cid, HttpSession session) {
		Student userstudent = (Student) session.getAttribute("usersession");
		Student student = stuService.getStudentbyID(userstudent.getId());
		Course course = courseService.getCourse(cid);
		ArrayList<Course> courseStudentList = new ArrayList<>(student.getCourses());
		courseStudentList.add(course);
		student.setCourses(courseStudentList);

		stuService.saveStudent(student);
		String studentemail = student.getEmail();
		sendEmail(studentemail, course.getName());
		return "redirect:/student/choseAvailableCourse";
	}

	@GetMapping("/removeEnrollCourseFromChoose/{cid}")
	public String removeEnrollCourseFromChoose(@PathVariable("cid") int cid, HttpSession session) {
		removeEnrollCourseMethod(cid, session);
		return "redirect:/student/choseAvailableCourse";
	}

	@GetMapping("/viewStudentCourses")
	public String viewStudentCourses(Model model, HttpSession session) {
		Student userstudent = (Student) session.getAttribute("usersession");
		Student student = new Student();
		student = stuService.getStudentbyID(userstudent.getId());

		ArrayList<MarksSheet> completeCourseList = new ArrayList<MarksSheet>();
		completeCourseList = markService.getCompleteMarksSheet(student);

		System.out.println(student.getCourses().size());
		model.addAttribute("student", student);
		model.addAttribute("completeCourses", completeCourseList);
		return "ViewStudentCourse";
	}

	@GetMapping("/removeEnrollCourseFromView/{cid}")
	public String removeEnrollCourseFromView(@PathVariable("cid") int cid, HttpSession session) {

		removeEnrollCourseMethod(cid, session);
		return "redirect:/student/viewStudentCourses";
	}

	public void removeEnrollCourseMethod(int cid, HttpSession session) {
		Student userstudent = (Student) session.getAttribute("usersession");
		Student student = new Student();
		student = stuService.getStudentbyID(userstudent.getId());
		Course course = new Course();
		course = courseService.getCourse(cid);
		ArrayList<Course> courseStudentList = new ArrayList<>(student.getCourses());
		if (courseStudentList.contains(course)) {
			courseStudentList.remove(course);
		}

		student.setCourses(courseStudentList);
		stuService.saveStudent(student);
	}

	@GetMapping("/ViewCompleteCourseDetail/{mid}")
	public String ViewCompleteCourseDetail(@PathVariable("mid") int mid, Model model) {
		MarksSheet completeCourse = new MarksSheet();
		completeCourse = markService.getMarksSheetById(mid);
		model.addAttribute("completeCourse", completeCourse);

		return "ViewDetailCourse";
	}

//Thandar's part

	@GetMapping("/edurecord")
	public String getRecordPage1(ModelMap model, HttpSession session) {
		String grade[] = new String[] { "A+", "A", "A-", "B+", "B", "B-", "C+", "C", "D+", "D", "F" };
		String des[] = new String[] { "Excellent", "Excellent", "Excellent", "Very Good", "Very Good", "Very Good",
				"Good", "Satisfactory", "Probationary Grade", "Probationary Grade", "Fail" };
		double gradept[] = new double[] { 5.0, 5.0, 4.5, 4.0, 3.5, 3.0, 2.5, 2.0, 1.5, 1.0, 0 };
		ArrayList<String> g = new ArrayList();
		ArrayList<String> d = new ArrayList();
		double gp = 0.0;
		double cpa = 0.0;

		ArrayList<MarksSheet> ms = new ArrayList<>();
		Student student = (Student) session.getAttribute("usersession");
		ms = markService.getCompleteMarksSheet(student);

		for (int i = 0; i < ms.size(); i++) {
			System.out.println(ms.get(i).getMarks());
			if (ms.get(i).getMarks() >= 84) {
				g.add(grade[0]);
				d.add(des[0]);
				gp = gradept[0];
			} else if (ms.get(i).getMarks() <= 83 && ms.get(i).getMarks() >= 80) {
				g.add(grade[1]);
				d.add(des[1]);
				gp = gradept[1];
			} else if (ms.get(i).getMarks() <= 79 && ms.get(i).getMarks() >= 75) {
				g.add(grade[2]);
				d.add(des[2]);
				gp = gradept[2];
			} else if (ms.get(i).getMarks() <= 74 && ms.get(i).getMarks() >= 70) {
				g.add(grade[3]);
				d.add(des[3]);
				gp = gradept[3];
			} else if (ms.get(i).getMarks() <= 69 && ms.get(i).getMarks() >= 65) {
				g.add(grade[4]);
				d.add(des[4]);
				gp = gradept[4];
			} else if (ms.get(i).getMarks() <= 64 && ms.get(i).getMarks() >= 60) {
				g.add(grade[5]);
				d.add(des[5]);
				gp = gradept[5];
			} else if (ms.get(i).getMarks() <= 59 && ms.get(i).getMarks() >= 55) {
				g.add(grade[6]);
				d.add(des[6]);
				gp = gradept[6];
			} else if (ms.get(i).getMarks() <= 54 && ms.get(i).getMarks() >= 50) {
				g.add(grade[7]);
				d.add(des[7]);
				gp = gradept[7];
			} else if (ms.get(i).getMarks() <= 49 && ms.get(i).getMarks() >= 45) {
				g.add(grade[8]);
				d.add(des[8]);
				gp = gradept[8];
			} else if (ms.get(i).getMarks() <= 44 && ms.get(i).getMarks() >= 40) {
				g.add(grade[9]);
				d.add(des[9]);
				gp = gradept[9];
			} else {
				g.add(grade[10]);
				d.add(des[10]);
				gp = gradept[10];
			}
			System.out.println("GradePoint:" + gp);
			cpa += (gp * 6.0);

		}
		System.out.println("CPA:" + cpa);
		cpa = cpa / (ms.size() * 6.0);

		// Map<String,String> map=new HashMap<>();
		// map.put(g, d);
		model.addAttribute("marksheet", ms).addAttribute("grade", g).addAttribute("des", d).addAttribute("cpa", cpa);
		// model.mergeAttributes(map);

		return "EduRecord";
	}

	@GetMapping("/personalrecord")
	public String getRecordPage2(Model model, HttpSession session) {
		Student student = (Student) session.getAttribute("usersession");
		model.addAttribute("student", student);
		return "PersonalRecord";
	}

	@PostMapping("/savePRecord")
	public String savePRecord(Student student, HttpSession session) {
		Student studentDb = (Student) session.getAttribute("usersession");
		studentDb.setAddress(student.getAddress());
		studentDb.setMobileNo(student.getMobileNo());
		studentDb.setPassword(student.getPassword());
		stuService.saveStudent(studentDb);
		return "StudentHome";
	}

	@GetMapping("/passwordreset")
	public String getPasswordReset(Model model) {
		model.addAttribute("password", new Password());
		model.addAttribute("role", "student");
		return "ConfirmPassword";
	}

	@PostMapping("/updatepassword")
	public String updatePassword(Password password, Model model, HttpSession session) {
		Student student = (Student) session.getAttribute("usersession");
		student = stuService.getStudentbyID(student.getId());
		model.addAttribute("password", password);
		model.addAttribute("role", "student");
		if (password.getNewPassword().equals(student.getPassword())) {
			model.addAttribute("message", "You have typed old password");
			return "ConfirmPassword";
		}

		if (password.getOldPassword().equals(student.getPassword())) {
			student.setPassword(password.getNewPassword());
			stuService.saveStudent(student);
			return "redirect:/student/home";
		} else {
			model.addAttribute("message", "Old password does not match");
		}
		return "ConfirmPassword";
	}

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendEmail(String studentemail, String coursename) {

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("sms-do-not-reply@u.nus.edu.com");
		msg.setSubject("You have successfully applied " + coursename);
		msg.setTo(studentemail);
		msg.setText("Course name:" + coursename);
		msg.setText("You have successfully applied " + coursename
				+ ".This is a system generated email, please do not reply.");

		javaMailSender.send(msg);
	}
}
