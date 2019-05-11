package com.dexter.dextest.oauth2.dto;

import java.io.Serializable;

public class Status implements Serializable  {
	private String username;
	private String contact;
	private String password;
	private String status;
	private String info;
	public Status() {
		
	}
	public Status(String username, String contact, String password, String status, String info) {
		this.username=username;
		this.contact=contact;
		this.password=password;
		this.status=status;
		this.info=info;
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
		this.contact = contact;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
}	