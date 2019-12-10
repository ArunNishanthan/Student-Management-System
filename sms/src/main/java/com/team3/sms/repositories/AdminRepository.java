package com.team3.sms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team3.sms.models.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
	public Admin findByEmail(String email);

}
