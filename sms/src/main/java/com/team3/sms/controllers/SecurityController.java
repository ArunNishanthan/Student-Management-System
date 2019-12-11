package com.team3.sms.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.team3.sms.enums.Role;
import com.team3.sms.models.LoginUser;
import com.team3.sms.models.User;
import com.team3.sms.models.UserPrincipal;
import com.team3.sms.services.FormServices;

@Controller
@SessionAttributes("usersession")
public class SecurityController {
	@Autowired
	private FormServices formServices;

	@RequestMapping("/login")
	public String getLoginPage(Model model, SessionStatus status) {
		status.setComplete();
		model.addAttribute("user", new LoginUser());
		return "login";
	}

	@GetMapping("/logout")
	public String getLogoutPage(SessionStatus status) {
		status.setComplete();
		return "home";
	}

	@GetMapping("/403")
	public String Unauth() {
		return "403";
	}

	@RequestMapping("/")
	public String route(@AuthenticationPrincipal UserPrincipal currentUser, HttpServletRequest request, Model model,
			HttpSession session) {
		String email = currentUser.getUsername();

		User user = formServices.getUserbyEmail(email);

		session.setAttribute("usersession", user);
		if (user.getRole() == Role.ISADMIN) {
			return "redirect:admin/home";
		}
		if (user.getRole() == Role.ISFACULTY) {
			return "redirect:faculty/home";
		}
		session.setAttribute("usersession", user);
		return "redirect:student/home";

	}
}
