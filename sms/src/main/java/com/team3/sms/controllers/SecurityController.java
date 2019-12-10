package com.team3.sms.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.team3.sms.enums.Role;
import com.team3.sms.models.LoginUser;
import com.team3.sms.models.User;
import com.team3.sms.models.UserPrincipal;
import com.team3.sms.services.FormServices;

@Controller
public class SecurityController {
	@Autowired
	private FormServices formServices;

	@RequestMapping("/login")
	public String getLoginPage(Model model) {
		model.addAttribute("user", new LoginUser());
		return "login";
	}

	@GetMapping("/logout")
	public String getLogoutPage(SessionStatus status) {
		status.setComplete();
		return "home";
	}

	@RequestMapping("/")
	public String route(@AuthenticationPrincipal UserPrincipal currentUser, HttpServletRequest request, Model model) {
		System.out.println(currentUser.getAuthorities());

		String email = currentUser.getUsername();
		User user = formServices.getUserbyEmail(email);
		if (user.getRole() == Role.ISADMIN) {
			return "redirect:admin/home";
		}
		if (user.getRole() == Role.ISFACULTY) {
			return "redirect:faculty/home";
		}
		return "redirect:student/home";

	}
}
