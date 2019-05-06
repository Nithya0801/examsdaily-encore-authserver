package com.dexter.dextest.oauth2.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dexter.dextest.oauth2.model.Contact;


public interface ContactRepository extends JpaRepository<Contact, Long> {
	
	boolean findByData(String data); 
}
