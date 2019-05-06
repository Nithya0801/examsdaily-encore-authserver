package com.dexter.dextest.oauth2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dexter.dextest.oauth2.model.Student;
import com.dexter.dextest.oauth2.repo.StudentRepository;
import com.dexter.dextest.oauth2.service.impl.StudentService;

@Configuration
@RestController
//@RequestMapping("/account")
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@PostMapping(value = "/insertStudent")
	public ResponseEntity<?> insertStudent(@RequestBody Student student) {
		System.out.println("******* "+student.getStudentAddress().getCity());
		studentService.insertStudent(student);
		return new ResponseEntity<String>("Success", HttpStatus.CREATED);
	}
	
//	@GetMapping(value = "/getStudent/{id}")
//	public ResponseEntity<Student> getStudentById(@PathVariable("id") int id) {
//		Student student = studentService.getById(id);
//		if (student == null)
//			return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
//		return new ResponseEntity<Student>(student, HttpStatus.OK);
//	}
}
