package com.dexter.dextest.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dexter.dextest.oauth2.repo.UserRepository;
import com.dexter.dextest.oauth2.utilities.ContactValidator;



@Service("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(ContactValidator.isValidEmailAddress(username))
			return userRepository.findByEmail(username);
		else if(ContactValidator.isValidMobileNumber(username))
			return userRepository.findByMobile(username);
		else
			return userRepository.findOneByUsername(username);
	}
}