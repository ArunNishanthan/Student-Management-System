package com.team3.sms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/student")
@Controller
public class StudentController {
	@GetMapping("/home")
	public String getHome(Model model) {
		model.addAttribute("user", "student");
		return "welcome";
	}
}
