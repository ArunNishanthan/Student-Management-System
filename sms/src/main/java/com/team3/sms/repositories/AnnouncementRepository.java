package com.team3.sms.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team3.sms.models.Announcement;
import com.team3.sms.models.Course;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
	public ArrayList<Announcement> findByfacultyname(String string);

	public ArrayList<Announcement> findByCourse(Course course);
}