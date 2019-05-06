package com.dexter.dextest.oauth2.model;



import java.io.Serializable;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
@Entity
@Table(name="Confirmations")
public class Verify implements Serializable{
	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;

	
	//@GeneratedValue(strategy=GenerationType.AUTO)	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "verifyId", updatable = false, nullable = false)
	private String verifyId;
	
	//@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JsonManagedReference
	@ManyToOne
	private User userId;
	
	private String type;
	
	private String contact; 
	
	private String username;
	
	private String firstName;

	private String lastName;
	
	private String generatedOtp;
	
	private String verificationToken;
	
	private boolean verified;
	
	private LocalDateTime tokenGeneratedAt;        
	    
	private LocalDateTime otpGeneratedAt;
	


	public String getVerifyId() {
		return verifyId;
	}

	public void setVerifyId(String verifyId) {
		this.verifyId = verifyId;
	}

	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

//	public static long getSerialversionuid() {
//		return serialVersionUID;
//	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	

}
