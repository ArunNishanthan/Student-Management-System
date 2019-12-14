package com.team3.sms.services;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.team3.sms.models.Faculty;
import com.team3.sms.models.Student;

public class CsvServices {

	public static void downloadStudent(PrintWriter writer, List<Student> Students) {
		writer.write("Student ID, First Name, Last Name, Address, Date of Birth, Email, Mobile No \n");
		for (Student student : Students) {
			writer.write(student.getId() + "," + student.getFirstName() + "," + student.getLastName() + ","
					+ student.getAddress() + "," + student.getDateofBirth() + "," + student.getEmail() + ","
					+ student.getMobileNo() + "\n");
		}
	}

	public static void downloadFaculty(PrintWriter writer, ArrayList<Faculty> staffByDepartment) {
		writer.write("Staff ID, First Name, Last Name, Address, Date of Birth, Email, Mobile No \n");
		for (Faculty faculty : staffByDepartment) {
			writer.write(faculty.getId() + "," + faculty.getFirstName() + "," + faculty.getLastName() + ","
					+ faculty.getAddress() + "," + faculty.getDateofBirth() + "," + faculty.getEmail() + ","
					+ faculty.getMobileNo() + "\n");
		}

	}

}
