package com.dexter.dextest.oauth2.dto;

import java.time.LocalDateTime;
import java.util.List;

public class VerifyDto {
	public String userId;
	public String verifyId;
	public String contact;	
	private String firstName;
	private String lastName;
	public String password;	
	public boolean verified;
	public String verificationToken;
	public String generatedOtp;
	public LocalDateTime tokenGeneratedAt;        
    public LocalDateTime otpGeneratedAt;
   
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public String getVerificationToken() {
		return verificationToken;
	}
	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}
	public String getGeneratedOtp() {
		return generatedOtp;
	}
	public void setGeneratedOtp(String generatedOtp) {
		this.generatedOtp = generatedOtp;
	}

	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public LocalDateTime getTokenGeneratedAt() {
		return tokenGeneratedAt;
	}
	public void setTokenGeneratedAt(LocalDateTime tokenGeneratedAt) {
		this.tokenGeneratedAt = tokenGeneratedAt;
	}
	public LocalDateTime getOtpGeneratedAt() {
		return otpGeneratedAt;
	}
	public void setOtpGeneratedAt(LocalDateTime otpGeneratedAt) {
		this.otpGeneratedAt = otpGeneratedAt;
	}
	public String getVerifyId() {
		return verifyId;
	}
	public void setVerifyId(String verifyId) {
		this.verifyId = verifyId;
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
	
	

}
