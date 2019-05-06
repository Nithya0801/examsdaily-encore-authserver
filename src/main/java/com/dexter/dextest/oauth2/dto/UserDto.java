package com.dexter.dextest.oauth2.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dexter.dextest.oauth2.model.Role;

public class UserDto {
	public String id;
	public String username;
	public String firstName;
	public String lastName;
	public String avatar;
	public boolean enabled;
	public List<String> email;
	public List<String> mobile;
	public Collection<Role> roles;
	public String confirmationToken;
	public String generatedOtp;
	public String googleToken;
	public LocalDateTime createdAt;
	public String registerMode;
	public String password;
	public List<VerifyDto> users = new ArrayList<>();
	
	
	public UserDto() {
		super();		
	}
	public UserDto(String id, String username, String firstName, String lastName, boolean enabled, List<String> email, List<String> mobile, Collection<Role> roles ,LocalDateTime createdAt) {		
		super();
		this.id=id;
		this.username=username;
		this.firstName=firstName;
		this.lastName=lastName;
		this.enabled=enabled;
		this.email=email;
		this.mobile=mobile;
		this.roles=roles;
		this.createdAt=createdAt;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public List<String> getEmail() {
		return email;
	}
	public void setEmail(List<String> email) {
		this.email = email;
	}
	public void setEnabled(boolean enabled) {
		this.enabled=enabled;
	}
	public boolean getEnabled() {
		return enabled;
	}
	public List<String> getMobile() {
		return mobile;
	}
	public void setMobile(List<String> mobile) {
		this.mobile = mobile;
	}
	public Collection<Role> getRoles(){
		return roles;	
	}
	public void setRoles(Collection<Role> roles) {
		this.roles=roles;
	}
	public String getConfirmationToken() {
		return confirmationToken;
	}
	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}
	
	public String getGoogleToken() {
		return googleToken;
	}
	public void setGoogleToken(String googleToken) {
		this.googleToken = googleToken;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public String getRegisterMode() {
		return registerMode;
	}
	public void setRegisterMode(String registerMode) {
		this.registerMode = registerMode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getGeneratedOtp() {
		return generatedOtp;
	}
	public void setGeneratedOtp(String generatedOtp) {
		this.generatedOtp = generatedOtp;
	}
	public List<VerifyDto> getUsers() {
		return users;
	}
	public void setUsers(List<VerifyDto> users) {
		this.users = users;
	}
	
	
	
	

}
