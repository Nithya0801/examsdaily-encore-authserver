package com.dexter.dextest.oauth2.service.impl;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dexter.dextest.oauth2.dto.RegisterDto;
import com.dexter.dextest.oauth2.dto.UserDto;
import com.dexter.dextest.oauth2.dto.VerifyDto;
import com.dexter.dextest.oauth2.model.Contact;
import com.dexter.dextest.oauth2.model.User;
import com.dexter.dextest.oauth2.model.Role;
import com.dexter.dextest.oauth2.repo.ContactRepository;
import com.dexter.dextest.oauth2.repo.RoleRepository;
import com.dexter.dextest.oauth2.repo.UserRepository;
import com.dexter.dextest.oauth2.service.IUserService;
import com.dexter.dextest.oauth2.utilities.ContactValidator;

@Service
@Transactional
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired 
	private RoleRepository roleRepository;

	@Override
	public void registerUser(RegisterDto dto) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User user = new User();
		user.setUsername(dto.getUsername());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setFirstName("xxx");
		user.setLastName("yyy");
		user.setMobileNumber(dto.getMobileNumber());
		user.setEnabled(true);
		user.setAvatar(dto.getAvatar());
		user.setRegistrationMode(dto.getRegisterMode());
		user.setCreatedAt(LocalDateTime.now());
//		if(dto.getRegisterMode().equals("GOOGLE"))
//			user.setPasscode(dto.getPassword());
//		if(dto.getRegisterMode().equals("FACEBOOK"))
//			user.setPasscode(dto.getPassword());
//		if(dto.getRegisterMode().equals("FACEBOOKMB"))
//			user.setPasscode(dto.getPassword());
		if(dto.getRegisterMode().equals("DIRECT"))
			user.setPasscode(dto.getPassword());
		System.out.println("User service RegisterDTO*-*-*-*--"+dto.getRoleType());
		
		Role role=roleRepository.findByRole(dto.getRoleType());
		System.out.println("Role from DB-==========="+role);
		if(role==null) {
			role=new Role();
			role.setRoleName(dto.getRoleType());
			roleRepository.save(role);
		}
		
/*		Role role=roleRepository.findByRole("ROLE_USER");
		if(role==null) {
			role=new Role();
			role.setRoleName("ROLE_USER");
			roleRepository.save(role);
		}
*/

	/*
		Role role=roleRepository.findByRole("ROLE_ADMIN");
		if(role==null) {
			role=new Role();
			role.setRoleName("ROLE_ADMIN");
			roleRepository.save(role);
		}*/

			
			
		user.getRoles().add(role);
		
		Contact contact = new Contact();
		if (dto.getContact() != null) {
			if (ContactValidator.isValidEmailAddress(dto.getContact())) {
				contact.setType("email");
				contact.setData(dto.getContact());
				contact.setUserId(user);
				contact.setVerified(true);
		
//				if(dto.getRegisterMode().equals("FACEBOOKMB"))
//					
//					contact.setVerified(false);
			} else if (ContactValidator.isValidMobileNumber(dto.getContact())) {
				contact.setType("mobile");
				contact.setData(dto.getContact());
				contact.setUserId(user);
				contact.setVerified(true);
			}
			if (user.getUsername() == null || user.getUsername() == "") {
				user.setUsername(dto.getContact());
			}	
		
		}
		
		user.getContacts().add(contact);
		userRepository.save(user);
	}

	@Override
	public UserDto findByEmail(String email) {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			List<String> emails = new ArrayList<>();
			List<String> mobiles = new ArrayList<>();
			for (Contact c : user.getContacts()) {
				if (c.getType().equals("email"))
					emails.add(c.getData());
				else if (c.getType().equals("mobile"))
					mobiles.add(c.getData());
			}
			UserDto dto = new UserDto(user.getUserId().toString(), user.getUsername(), user.getFirstName(),
					user.getLastName(), user.isEnabled(), emails, mobiles, user.getRoles(), user.getCreatedAt());
					dto.setPassword(user.getPasscode());
					dto.setAvatar(user.getAvatar());
			return dto;
		} else {
			return null;
		}
	}

	@Override
	public UserDto findByMobile(String mobile) {
		User user = userRepository.findByMobile(mobile);
		if (user != null) {
			List<String> emails = new ArrayList<>();
			List<String> mobiles = new ArrayList<>();
			for (Contact c : user.getContacts()) {
				if (c.getType().equals("email"))
					emails.add(c.getData());
				else if (c.getType().equals("mobile"))
					mobiles.add(c.getData());
			}
			UserDto dto = new UserDto(user.getUserId().toString(), user.getUsername(), user.getFirstName(),
					user.getLastName(), user.isEnabled(), emails, mobiles, user.getRoles(), user.getCreatedAt());
					dto.setPassword(user.getPasscode());
					dto.setAvatar(user.getAvatar());
					dto.setGeneratedOtp(user.getGeneratedOtp());
			return dto;
		} else {
			return null;
		}
	}

	@Override
	public UserDto findByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if (user != null) {
			List<String> emails = new ArrayList<>();
			List<String> mobiles = new ArrayList<>();
				if(user.getContacts() != null)
				for (Contact c : user.getContacts()) {
					if(c.getData()!=null) {
						if (c.getType().equals("email"))
							emails.add(c.getData());
						else if (c.getType().equals("mobile"))
							mobiles.add(c.getData());
					}
				}
			UserDto dto = new UserDto(user.getUserId().toString(), user.getUsername(), user.getFirstName(),
					user.getLastName(), user.isEnabled(), emails, mobiles, user.getRoles(), user.getCreatedAt());
					dto.setPassword(user.getPasscode());
					dto.setAvatar(user.getAvatar());
			return dto;
		} else {
			return null;
		}
	}
	
	@Override
	public User validate(String contact, String password) {
		if (ContactValidator.isValidEmailAddress(contact) || ContactValidator.isValidMobileNumber(contact)) {

		}

		return null;
	}

	@Override
	public User findById(String uuid) {
		return userRepository.findById(uuid).get();
	}

	@Override
	public User update(User user) {
		userRepository.save(user);
		return user;
	}
	
	@Override
	public List<UserDto> findAll() {
//		List<User> users=userRepository.findAll();
		List<User> users=userRepository.findAllUsers();
		List<UserDto> dtoList=new ArrayList<>();
		for(User u : users) {
			List<String> emails = new ArrayList<>();
			List<String> mobiles = new ArrayList<>();
				if(u.getContacts() != null)
				for (Contact c : u.getContacts()) {
					if (c.getType().equals("email"))
						emails.add(c.getData());
					else if (c.getType().equals("mobile"))
						mobiles.add(c.getData());
				}			
			dtoList.add(new UserDto(u.getUserId().toString(), u.getUsername(), u.getFirstName(),
					u.getLastName(), u.isEnabled(), emails, mobiles, u.getRoles(), u.getCreatedAt()));
		}		
		return dtoList;
	}
	
	/*=============================================================
	BELOW LOGIC USES DYNAMIC PAGINATION FOR DISPLAYING THE USERS LISTS
	=============================================================*/
	
//	@SuppressWarnings("deprecation")
	@Override
	public List<UserDto> getAll(int page,int count) {
//		List<User> users=userRepository.findAll();
		List<User> users=userRepository.getAllUsers(new PageRequest(page,count));
//		System.out.println(page +"-"+count);
		List<UserDto> dtoList=new ArrayList<>();
		for(User u : users) {
			List<String> emails = new ArrayList<>();
			List<String> mobiles = new ArrayList<>();
				if(u.getContacts() != null)
				for (Contact c : u.getContacts()) {
					if (c.getType().equals("email"))
						emails.add(c.getData());
					else if (c.getType().equals("mobile"))
						mobiles.add(c.getData());
				}			
			dtoList.add(new UserDto(u.getUserId().toString(), u.getUsername(), u.getFirstName(),
					u.getLastName(), u.isEnabled(), emails, mobiles, u.getRoles(), u.getCreatedAt()));
		}		
		return dtoList;
	}
	

	@Override
	public boolean deleteUser(String userId) {
		User user=userRepository.findById(userId).get();
		if(user!=null) {
			userRepository.delete(user);
			return true;
		}	
		return false;
	}

	
	@Override
	public boolean enableUser(String userId) {
		User user=userRepository.findById(userId).get();
		if(user!=null) {
			user.setEnabled(true);
			userRepository.saveAndFlush(user);
			return true;
		}	
		return false;
	}

	@Override
	public boolean disableUser(String userId) {
		User user=userRepository.findById(userId).get();
		if(user!=null) {
			user.setEnabled(false);
			userRepository.saveAndFlush(user);
			return true;
		}	
		return false;
	}
	
	@Override
	public User findByConfirmationToken(String confirmationToken) {
		return userRepository.findByConfirmationToken(confirmationToken);
		
	}

	@Override
	public User findByUserId(String userId) {
		
		return userRepository.findByUserId(userId);
	}

	@Override
	public User findByGeneratedOtp(String generatedOtp) {
		
		return userRepository.findByGeneratedOtp(generatedOtp);
	}

	@Override
	public Long getUsersCount() {
		Long usersCount = userRepository.getUsersCount();
		return usersCount;
	}

	
}
