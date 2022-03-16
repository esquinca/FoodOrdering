package com.foodordering.demo.dto;

public class UserDetailsDTO {
	private String username;
	private String email;
	private String phoneNo;
	
	
	public UserDetailsDTO() {

	}
	public UserDetailsDTO(String username, String email, String phoneNo) {
		super();
		this.username = username;
		this.email = email;
		this.phoneNo = phoneNo;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
}
