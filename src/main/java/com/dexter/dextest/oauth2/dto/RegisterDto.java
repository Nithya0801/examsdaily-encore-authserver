package com.dexter.dextest.oauth2.dto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import com.dexter.dextest.oauth2.model.Role;

public class RegisterDto {
	private String userId;
	private String username;
	private String contact;
	private String password;
	private String firstName;
	private String lastName;
	public String avatar;
	public String registerMode;	
	public String confirmationToken;
	public String verificationToken;
	public String generatedOtp;
	private String roleType;
	private String mobileNumber;
	
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact= contact;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getRegisterMode() {
		return registerMode;
	}
	public void setRegisterMode(String registerMode) {
		this.registerMode = registerMode;
	}
	public String getConfirmationToken() {
		return confirmationToken;
	}
	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGeneratedOtp() {
		return generatedOtp;
	}
	public void setGeneratedOtp(String generatedOtp) {
		this.generatedOtp = generatedOtp;
	}
	public String getVerificationToken() {
		return verificationToken;
	}
	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}
	
		
	
	
}
