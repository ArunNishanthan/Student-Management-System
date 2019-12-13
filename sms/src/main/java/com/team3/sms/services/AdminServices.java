package com.team3.sms.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3.sms.models.Admin;
import com.team3.sms.repositories.AdminRepository;

@Service
public class AdminServices {
	@Autowired
	private AdminRepository adminRepository;

	public Admin getAdmin(int id) {
		return adminRepository.getOne(id);
	}

	public void saveAdmin(Admin admin) {
		adminRepository.save(admin);
	}

	public long totalAdmin() {
		return adminRepository.count();
	}

	public ArrayList<Admin> getAdmins() {
		return (ArrayList<Admin>) adminRepository.findAll();
	}

	public void removeAdmin(Admin admin) {
		adminRepository.delete(admin);
	}
}
