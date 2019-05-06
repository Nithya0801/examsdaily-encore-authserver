package com.dexter.dextest.oauth2.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dexter.dextest.oauth2.dto.UserDto;
import com.dexter.dextest.oauth2.dto.VerifyDto;
import com.dexter.dextest.oauth2.model.Contact;
import com.dexter.dextest.oauth2.model.User;
import com.dexter.dextest.oauth2.model.Verify;
import com.dexter.dextest.oauth2.repo.UserRepository;
import com.dexter.dextest.oauth2.repo.VerifyRepository;
import com.dexter.dextest.oauth2.service.IUserService;
import com.dexter.dextest.oauth2.service.IVerifyService;
@Service
@Transactional
public class VerifyService implements IVerifyService{
	
	@Autowired
	private VerifyRepository verifyRepository;

	

	@Override
	public Verify findByGeneratedOtp(String generatedOtp) {
	
		return verifyRepository.findByGeneratedOtp(generatedOtp);
	}

	@Override
	public Verify findByUserId(String userId) {
		
		return verifyRepository.findByUserId(userId);
	}

	@Override
	public Verify findByVerificationToken(String verificationToken) {
		
		return verifyRepository.findByVerificationToken(verificationToken);
	}

	@Override
	public Verify findByVerifyId(String verifyId) {
		
		return verifyRepository.findByVerifyId(verifyId);
	}
}
