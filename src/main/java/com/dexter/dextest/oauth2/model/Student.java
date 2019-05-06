package com.dexter.dextest.oauth2.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="Students")
public class Student /*implements Serializable*/{

//	private static final long serialVersionUID = 1L;
	
	@Id	
//	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="studSeq", sequenceName="adm_no", initialValue=1, allocationSize=1)
	@GeneratedValue(generator="studSeq")
	private int studentId;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date applicationDate;
	
	private String studentFirstName;
	private String studentLastName;
	private String fatherName;
	private String motherName;
	
//	@Temporal(TemporalType.DATE)
//	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date studentDob;
//	private String studentDob;
	private String studentGender;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "addressId")
//	@JoinTable(name="address_id",joinColumns = @JoinColumn(name="studentId", referencedColumnName = "studentId"),inverseJoinColumns = @JoinColumn(name = "addressId", referencedColumnName = "addressId" ))
	private Address studentAddress;
//	private String studentAddress;
	private String studentMobile;
	private String studentEmail;
	private String fatherMobile;
	private String motherMobile;
	private String studentQualification;
		
	//	public Student() {
//		this.studentAddress = new Address();
//	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getStudentFirstName() {
		return studentFirstName;
	}
	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}
	public String getStudentLastName() {
		return studentLastName;
	}
	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getMotherName() {
		return motherName;
	}
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
//	public String getStudentDob() {
//		return studentDob;
//	}
//	public void setStudentDob(String studentDob) {
//		this.studentDob = studentDob;
//	}
	public Date getStudentDob() {
		return studentDob;
	}
	public void setStudentDob(Date studentDob) {
		this.studentDob = studentDob;
	}
	public String getStudentGender() {
		return studentGender;
	}
	public void setStudentGender(String studentGender) {
		this.studentGender = studentGender;
	}
//	public Address getStudentAddress() {
//		return studentAddress;
//	}
//	public void setStudentAddress(Address studentAddress) {
//		this.studentAddress = studentAddress;
//	}
	public Address getStudentAddress() {
		return studentAddress;
	}
	public void setStudentAddress(Address studentAddress) {
		this.studentAddress = studentAddress;
	}
	public String getStudentMobile() {
		return studentMobile;
	}
	public void setStudentMobile(String studentMobile) {
		this.studentMobile = studentMobile;
	}
	public String getStudentEmail() {
		return studentEmail;
	}
	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}
	public String getFatherMobile() {
		return fatherMobile;
	}
	public void setFatherMobile(String fatherMobile) {
		this.fatherMobile = fatherMobile;
	}
	public String getMotherMobile() {
		return motherMobile;
	}
	public void setMotherMobile(String motherMobile) {
		this.motherMobile = motherMobile;
	}
	public String getStudentQualification() {
		return studentQualification;
	}
	public void setStudentQualification(String studentQualification) {
		this.studentQualification = studentQualification;
	}
	public Date getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}
	
	
}
