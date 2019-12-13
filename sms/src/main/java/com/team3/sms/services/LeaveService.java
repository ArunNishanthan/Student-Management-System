package com.team3.sms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3.sms.models.Faculty;
import com.team3.sms.models.StaffLeave;
import com.team3.sms.repositories.LeaveRepository;

@Service
public class LeaveService {

	@Autowired
	private LeaveRepository lRepo;

	public List<StaffLeave> findPendingLeaves() {

		return lRepo.findPendingLeaves();

	}

	public List<StaffLeave> findApprovedLeaves() {

		return lRepo.findApprovedLeaves();

	}

	// may may
	public List<StaffLeave> findRejectedLeaves() {
		return lRepo.findRejectedLeaves();
	}

	// may may Thunder
	public Faculty findFacultyByLeaveId(int id) {
		return lRepo.findFacultyByLeaveId(id);
	}

	public void saveLeave(StaffLeave staffLeave) {
		lRepo.save(staffLeave);
	}

	public List<StaffLeave> findLeavesByFaculty(Faculty faculty) {
		return lRepo.findLeavesByFaculty(faculty);

	}

	public StaffLeave findLeavesByLeaveId(int id) {
		return lRepo.findById(id).get();
	}

	public void deleteLeave(StaffLeave leave) {
		lRepo.delete(leave);
	}

}
