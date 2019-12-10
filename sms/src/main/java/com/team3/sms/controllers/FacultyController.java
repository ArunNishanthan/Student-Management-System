package com.team3.sms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@RequestMapping("/faculty")
@Controller
@SessionAttributes("usersession")
public class FacultyController {

	@GetMapping("/home")
	public String getHome(Model model) {
		model.addAttribute("user", "faculty");
		return "welcome";
	}
}
