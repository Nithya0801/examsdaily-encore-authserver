package com.dexter.dextest.oauth2.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dexter.dextest.oauth2.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{

}
