package com.team3.sms.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/faculty")
@Controller

public class FacultyController {

	@PreAuthorize("hasRole('ISFACULTY')")
	@GetMapping("/home")
	public String getHome(Model model) {
		model.addAttribute("user", "faculty");
		return "welcome";
	}
}
