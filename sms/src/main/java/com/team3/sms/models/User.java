package com.team3.sms.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

@MappedSuperclass
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotEmpty
	@Length(min = 3, max = 20)
	private String firstName;
	private String lastName;
	@NotEmpty
	private String gender;
	@NotEmpty
	private String dateofBirth;
	@NotEmpty
	@Length(min = 6, max = 50)
	private String address;
	@NotEmpty
	private String email;
	private String password;
	@NotEmpty
	private String mobileNo;
	private Role Role;

	public User() {
	}

	public User(String firstName, String lastName, String gender, String dateofBirth, String address, String email,
			String password, String mobileNo, com.team3.sms.models.Role role) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.dateofBirth = dateofBirth;
		this.address = address;
		this.email = email;
		this.password = password;
		this.mobileNo = mobileNo;
		Role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateofBirth() {
		return dateofBirth;
	}

	public void setDateofBirth(String dateofBirth) {
		this.dateofBirth = dateofBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Role getRole() {
		return Role;
	}

	public void setRole(Role role) {
		Role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender
				+ ", dateofBirth=" + dateofBirth + ", address=" + address + ", email=" + email + ", password="
				+ password + ", mobileNo=" + mobileNo + ", Role=" + Role + "]";
	}

}
