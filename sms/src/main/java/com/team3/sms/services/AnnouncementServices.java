package com.team3.sms.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3.sms.models.Announcement;
import com.team3.sms.models.Course;
import com.team3.sms.repositories.AnnouncementRepository;

@Service
public class AnnouncementServices {
	@Autowired
	private AnnouncementRepository announcementRepository;

	public void SaveAnnouncement(Announcement announcement) {
		announcementRepository.save(announcement);
	}

	public ArrayList<Announcement> getAnnouncementbyFaculty(String fname) {
		return announcementRepository.findByfacultyname(fname);
	}

	public ArrayList<Announcement> getAnnouncementbyCourse(Course course) {
		return announcementRepository.findByCourse(course);
	}

}
