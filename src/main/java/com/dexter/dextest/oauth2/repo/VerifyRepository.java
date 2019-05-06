package com.dexter.dextest.oauth2.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.dexter.dextest.oauth2.model.Verify;

public interface VerifyRepository extends JpaRepository<Verify, String>{
	
	
	@Query("SELECT v from Verify v where v.verificationToken=?1")	
	 Verify findByVerificationToken(String verificationToken);
	
	@Query("SELECT u from User u where u.userId=?1")	
	Verify findByUserId(String userId);
	
	@Query("SELECT v from Verify v where v.generatedOtp=?1")	
	 Verify findByGeneratedOtp(String generatedOtp);
	
	@Query("SELECT v from Verify v where v.verifyId=?1")	
	 Verify findByVerifyId(String verifyId);

}
