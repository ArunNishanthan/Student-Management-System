package com.team3.sms.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.team3.sms.models.Announcement;
import com.team3.sms.models.Faculty;
import com.team3.sms.services.AnnouncementServices;
import com.team3.sms.services.FacultyServices;

@RequestMapping("/faculty")
@Controller
@SessionAttributes("usersession")
public class FacultyController {
	@Autowired
	private FacultyServices facultyServices;

	@Autowired
	private AnnouncementServices announcementServices;

	@GetMapping("/home")
	public String getHome(Model model) {
		model.addAttribute("user", "faculty");
		return "welcome";
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
	public String submitannouncement(@Valid @ModelAttribute("announcement") Announcement announcement, Model model,
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

}
