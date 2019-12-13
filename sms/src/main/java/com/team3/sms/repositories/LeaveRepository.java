package com.team3.sms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.team3.sms.models.Faculty;
import com.team3.sms.models.StaffLeave;

public interface LeaveRepository extends JpaRepository<StaffLeave, Integer> {
	
	@Query("select sl from StaffLeave sl where sl.leaveStatus=0")
	public List<StaffLeave> findPendingLeaves();
	
	@Query("select sl from StaffLeave sl where sl.leaveStatus = 1")
	public List<StaffLeave> findApprovedLeaves();
	
	@Query("select sl from StaffLeave sl where sl.leaveStatus = 2")
	public List<StaffLeave> findRejectedLeaves();
	
	@Query("select f from Faculty f JOIN f.staffLeaves sl where sl.id = ?1")
	public Faculty findFacultyByLeaveId(int id);
	

   public List<StaffLeave> findLeavesByFaculty(Faculty faculty);
	

}
