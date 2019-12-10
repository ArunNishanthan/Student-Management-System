package com.team3.sms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3.sms.models.Admin;
import com.team3.sms.repositories.AdminRepository;

@Service
public class AdminServices {
	@Autowired
	private AdminRepository adminRepository;

	public void saveAdmin(Admin admin) {
		adminRepository.save(admin);
	}
}
