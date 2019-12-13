package com.team3.sms.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.team3.sms.enums.LeaveStatus;

@Entity
public class StaffLeave {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date startDate;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date endDate;

	private String comments;

	@NotNull
	private LeaveStatus leaveStatus;

	public LeaveStatus getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(LeaveStatus leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	private Faculty faculty;

	public StaffLeave() {

	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}