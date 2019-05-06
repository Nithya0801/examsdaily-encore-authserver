package com.dexter.dextest.oauth2.service;



import com.dexter.dextest.oauth2.model.Verify;

public interface IVerifyService {
	
	Verify  findByVerificationToken(String verificationToken);
	
	Verify findByGeneratedOtp(String generatedOtp);
	
	Verify findByUserId(String userId);
	
	Verify findByVerifyId(String verifyId);
	

}
