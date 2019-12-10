package com.team3.sms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.team3.sms.models.User;
import com.team3.sms.models.UserPrincipal;
import com.team3.sms.repositories.AdminRepository;
import com.team3.sms.repositories.FacultyRepository;
import com.team3.sms.repositories.StudentRepository;

@Service
public class LoginUserDetailsService implements UserDetailsService {

	@Autowired
	private FacultyRepository facultyRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private AdminRepository adminRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = studentRepository.findByEmail(email);
		if (user == null) {
			user = facultyRepository.findByEmail(email);
			if (user == null) {
				user = adminRepository.findByEmail(email);
				if (user == null) {
					System.out.println("User not found");
					throw new UsernameNotFoundException("User 404");
				}
			}
		}
		return new UserPrincipal(user);
	}

}
