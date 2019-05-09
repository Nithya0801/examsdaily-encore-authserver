package com.dexter.dextest.oauth2.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dexter.dextest.oauth2.model.Student;
import com.dexter.dextest.oauth2.repo.StudentRepository;
import com.dexter.dextest.oauth2.service.IStudentService;

@Service
public class StudentService implements IStudentService{

	@Autowired
	private StudentRepository studentRepository;
	
	public void insertStudent(Student student) {
		studentRepository.save(student);
	}
	
//	public Student getById(int id) {
//		List<Student> studentList=studentRepository.findAll();
//		System.out.println("*****"+studentList);
//		for(Student student:studentList)
//		{
//			if(student.getStudentId()==id)
//				return student;
//		}
////		return null;
//		Student student = studentRepository.findByStudentId(id);
//		System.out.println("-------------"+student);
//		return student;
//	}
	
}
