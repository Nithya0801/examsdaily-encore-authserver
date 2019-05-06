package com.dexter.dextest.oauth2.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.dexter.dextest.oauth2.dto.RegisterDto;
import com.dexter.dextest.oauth2.dto.UserDto;
import com.dexter.dextest.oauth2.dto.VerifyDto;
import com.dexter.dextest.oauth2.model.User;


public interface IUserService {
	
	void registerUser(RegisterDto dto);	

	UserDto findByEmail(String email);
	
	UserDto findByMobile(String mobile);
	
	UserDto findByUsername(String username);
	
	User findById(String uuid);
	
	List<UserDto> findAll();
	
	User validate(String contact, String password);
	
	User update(User user);
	
	boolean deleteUser(String userId);
	
	boolean enableUser(String userId);
	
	boolean disableUser(String userId);
	
//	boolean deleteSelectedUser(UserDto dto);

	User findByConfirmationToken(String confirmationToken);
	
	User findByGeneratedOtp(String generatedOtp);
	
	User findByUserId(String userId);

	List<UserDto> getAll(int page, int count);
	
	Long getUsersCount();


}
