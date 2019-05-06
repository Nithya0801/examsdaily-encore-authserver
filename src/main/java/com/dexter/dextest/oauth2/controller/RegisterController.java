package com.dexter.dextest.oauth2.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpSession;

//import org.mockito.verification.VerificationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dexter.dextest.oauth2.dto.PasswordResetDto;
import com.dexter.dextest.oauth2.dto.RegisterDto;
import com.dexter.dextest.oauth2.dto.UserDto;
import com.dexter.dextest.oauth2.dto.VerifyDto;
import com.dexter.dextest.oauth2.model.Contact;
import com.dexter.dextest.oauth2.model.Role;
import com.dexter.dextest.oauth2.model.User;
import com.dexter.dextest.oauth2.model.Verify;
import com.dexter.dextest.oauth2.repo.RoleRepository;
import com.dexter.dextest.oauth2.repo.UserRepository;
import com.dexter.dextest.oauth2.repo.VerifyRepository;
import com.dexter.dextest.oauth2.service.EmailService;
import com.dexter.dextest.oauth2.service.impl.UserService;
import com.dexter.dextest.oauth2.service.impl.VerifyService;
import com.dexter.dextest.oauth2.utilities.ContactValidator;

@Configuration
@RestController
@RequestMapping("/account")
public class RegisterController {
	@Autowired
	private UserService userService;
	
	@Autowired	
	private VerifyRepository verifyRepository;
	
	@Autowired	
	private UserRepository userRepository;
	
	@Autowired	
	private RoleRepository roleRepository;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private VerifyService verifyService;
	
	@PostMapping("/userRegister")
	public ResponseEntity<?>contactEmail(@RequestBody final RegisterDto dto){
		UserDto usernameExists=userService.findByUsername(dto.getUsername());
		UserDto emailExists=userService.findByEmail(dto.getContact());
		UserDto mobileExists=userService.findByMobile(dto.getContact());
	
		if (emailExists==null && mobileExists==null && usernameExists==null) {	
			Verify verify =new Verify();
			if (ContactValidator.isValidEmailAddress(dto.getContact())){
					verify.setType("email");					
					verify.setContact(dto.getContact());
					verify.setUsername(dto.getUsername());
					/*int index = verify.getContact().indexOf('@');
			        String emailId = verify.getContact().substring(0,index);
			        verify.setUsername(emailId);*/					
					verify.setFirstName(dto.getFirstName());
					verify.setLastName(dto.getLastName());
					verify.setVerified(false);
					verify.setTokenGeneratedAt(LocalDateTime.now());
					verify.setVerificationToken(UUID.randomUUID().toString());	
				
//					String appUrl = "http://test.examsdaily.in";
					String appUrl = "http://localhost:8080";
					SimpleMailMessage emailVerification = new SimpleMailMessage();
					emailVerification.setTo(dto.getContact());
					emailVerification.setSubject("EMAIL VERIFICATION");
					emailVerification.setText("Dear,"+verify.getFirstName()+""+verify.getLastName()+"\n"+"To Verify Your Email, please click the link below:\n"
							+ appUrl + "/#/mail_password?token=" + verify.getVerificationToken()+"\n\n"
							+"With Regards,\n" +
							"Developer Team.(ExamsDaily.in)");
					emailVerification.setFrom("noreply@examsdaily.in");
					emailService.sendEmail(emailVerification);
					verifyRepository.save(verify);
					System.out.println("An Activation Link Has Been Sent TO Your EmailID :"+dto.getContact());			
					return ResponseEntity
						.status(HttpStatus.OK)
						.body("An Activation Link For Re-Verification Has Been Sent TO Your EmailID : "+dto.getContact());
			    }
		
			    else if (ContactValidator.isValidMobileNumber(dto.getContact())) {
			    	verify.setType("mobile");
					verify.setUsername(dto.getUsername());
					verify.setContact(dto.getContact());
					verify.setFirstName(dto.getFirstName());
					verify.setLastName(dto.getLastName());
					verify.setVerified(false);	
					verify.setOtpGeneratedAt(LocalDateTime.now());
				try {
					int randomPIN = (int)(Math.random()*9000)+1000;
					String pin = ""+randomPIN;
					verify.setGeneratedOtp(pin);
					// Construct data
					String apiKey = "apikey=" + "Yn2BvW3C3fs-qmgn5rnhQKCdsTOjbsxoCAd0Ie4Lxj";
					String message = "&message=" + "To verify your Mobile number, please enter the OTP number given below :\n" + verify.getGeneratedOtp();
					String sender = "&sender=" + "TXTLCL";			
					String numbers = "&numbers=" + (dto.getContact());			
					// Send data
					HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
					String data = apiKey + numbers + message + sender;
					conn.setDoOutput(true);
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
					conn.getOutputStream().write(data.getBytes("UTF-8"));
					final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					final StringBuffer stringBuffer = new StringBuffer();
					String line;
					while ((line = rd.readLine()) != null) {
						stringBuffer.append(line);
					}
					rd.close();
					System.out.println(stringBuffer.toString());				
				} 
				catch (Exception e) {
				System.out.println("Error SMS "+e);	
			}			
			}
		
			verifyRepository.save(verify);
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(verify);
	}
			
		return ResponseEntity
			.status(HttpStatus.CONFLICT)
			.body("Email/Mobile/Username Already Exists");	
		
	}
	
	@GetMapping("/tokenVerify")
	public ResponseEntity<?> tokenVerify1(@RequestParam(name="token") final String token, HttpSession session) {					
		Verify tokenVerify=verifyService.findByVerificationToken(token);
		session.setAttribute("token", token); 
			if (tokenVerify == null) { 
				return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body("This is an invalid confirmation link.");			
				}		
				else {
					return ResponseEntity
						.status(HttpStatus.OK)
						.body(tokenVerify);			
				}
		}
	
	@PostMapping("/tokenVerify")	
	public ResponseEntity<?> tokenVerify(@RequestBody final RegisterDto dto){
		String token=dto.getVerificationToken();
		Verify tokenVerify=verifyService.findByVerificationToken(token);	
		//User user=userService.findByUserId(tokenVerify.getUserId().getUserId());				
			if(tokenVerify!=null) {
				tokenVerify.setVerified(true);
				verifyRepository.save(tokenVerify);
/*				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				User user = new User();
				user.setUsername(tokenVerify.getUsername());
				user.setFirstName(tokenVerify.getFirstName());
				user.setLastName(tokenVerify.getLastName());
				user.setEnabled(true);
				user.setPassword(passwordEncoder.encode(dto.getPassword()));
				user.setRegistrationMode("DIRECT");
				user.setCreatedAt(LocalDateTime.now());
				user.setPasscode(dto.getPassword());
				Role role=roleRepository.findByRole("ROLE_USER");
				if(role==null) {
					role=new Role();
					role.setRoleName("ROLE_USER");
					roleRepository.save(role);
				}
			
				Role role=roleRepository.findByRole("ROLE_ADMIN");
				if(role==null) {
					role=new Role();
					role.setRoleName("ROLE_ADMIN");
					roleRepository.save(role);
				}
					
				user.getRoles().add(role);
				Contact contact = new Contact();				
					contact.setType("email");					
					contact.setData(tokenVerify.getContact());																		
					contact.setUserId(user);
				    user.getContacts().add(contact);				
					userRepository.save(user);
					*/
				
				 RegisterDto user = new RegisterDto();
		          user.setContact(tokenVerify.getContact());		          
		          user.setFirstName(tokenVerify.getFirstName());
		          user.setLastName(tokenVerify.getLastName());
		          user.setPassword(dto.getPassword());
		          user.setRegisterMode("DIRECT");
		          user.setUsername(tokenVerify.getUsername());
		         /* int index = tokenVerify.getContact().indexOf('@');
		          String emailId = tokenVerify.getContact().substring(0,index);
		          user.setUsername(emailId);*/
		          userService.registerUser(user);
				    return ResponseEntity
						.status(HttpStatus.OK)
						.body(user);	
				}
				else {
					return ResponseEntity
						.status(HttpStatus.CONFLICT)
						.body("This is an invalid confirmation link.");					
				}
		}
	
	@PostMapping("/otpCheck")
	public ResponseEntity<?> otpVerify1(@RequestBody final VerifyDto dto) {	
		Verify verify=verifyService.findByVerifyId(dto.getVerifyId());
		String existingOtp = verify.getGeneratedOtp();
		String currentOtp = dto.getGeneratedOtp();
		boolean otpCheck = currentOtp.matches(existingOtp);
		System.out.println(currentOtp);
		System.out.println(existingOtp);
	
			if (verify != null && otpCheck==false) { 
				return ResponseEntity
						.status(HttpStatus.CONFLICT)
						.body("This is an invalid otp.");
							
				}		
				else {
					verify.setVerified(true);	
					verifyRepository.save(verify);
					return ResponseEntity
							.status(HttpStatus.OK)
							.body(verify);			
				}
		}
	
	@PostMapping("/otpVerify")
	public ResponseEntity<?> otpVerify(@RequestBody final VerifyDto dto){		
//		Verify otpVerify=verifyService.findByGeneratedOtp(dto.getGeneratedOtp());
		Verify otpVerify=verifyService.findByVerifyId(dto.getVerifyId());
			if(otpVerify!=null){
/*				otpVerify.setVerified(true);
				verifyRepository.save(otpVerify);
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				User user = new User();
				user.setUsername(otpVerify.getUsername());
				user.setFirstName(otpVerify.getFirstName());
				user.setLastName(otpVerify.getLastName());
				user.setEnabled(true);
				user.setPassword(passwordEncoder.encode(dto.getPassword()));
				user.setRegistrationMode("DIRECT");
				user.setCreatedAt(LocalDateTime.now());
				user.setPasscode(dto.getPassword());
				Role role=roleRepository.findByRole("ROLE_USER");
				if(role==null) {
					role=new Role();
					role.setRoleName("ROLE_USER");
					roleRepository.save(role);
				}
			
//				Role role=roleRepository.findByRole("ROLE_ADMIN");
//				if(role==null) {
//					role=new Role();
//					role.setRoleName("ROLE_ADMIN");
//					roleRepository.save(role);
//				}
					
				user.getRoles().add(role);			
				Contact contact = new Contact();				
					contact.setType("mobile");
					contact.setData(otpVerify.getContact());
					contact.setUserId(user);				
					user.getContacts().add(contact);						
					userRepository.save(user);	*/
				  RegisterDto user = new RegisterDto();
		          user.setContact(otpVerify.getContact());		          
		          user.setFirstName(otpVerify.getFirstName());
		          user.setLastName(otpVerify.getLastName());
		          user.setPassword(dto.getPassword());
		          user.setRegisterMode("DIRECT");
		          user.setUsername(otpVerify.getUsername());
		          userService.registerUser(user);
					return ResponseEntity
						.status(HttpStatus.OK)
						.body(user);
				}
					return ResponseEntity
						.status(HttpStatus.CONFLICT)
						.body("Invalid OTP Entered");							
		}	
	
/*	@PostMapping("/userCheck")
	public ResponseEntity register(@RequestBody final RegisterDto dto) {
		if(userService.findByUsername(dto.getContact())==null && userService.findByEmail(dto.getContact())==null && userService.findByMobile(dto.getContact())==null) {			
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(true);	
		}		
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(false);		
	}
	
	@PostMapping("/userCheckPassword")
	public ResponseEntity check(@RequestBody final PasswordResetDto dto){
		User user=userService.findByUserId(dto.getUserId());	
		BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();	
		if(user!=null && passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {	
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(true);	
		}		
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(false);		
	}
	@PostMapping("/reportSend")
	public ResponseEntity message(@RequestBody final RegisterDto dto) {
		User user=userService.findByUserId(dto.getUserId());
		Set<Contact> contacts = null;
		if(user!=null)
			contacts = user.getContacts();
		for(Contact c : contacts) {
			c.getData();
			if(ContactValidator.isValidEmailAddress(c.getData())){
//				String appUrl = "http://test.examsdaily.in";
//				String appUrl = "http://localhost";
				SimpleMailMessage testReport = new SimpleMailMessage();
				testReport.setTo(c.getData());
				testReport.setSubject("Test Report");
				testReport.setText("Your Test Report Has Been Send");
				testReport.setFrom("noreply@examsdaily.in");
				emailService.sendEmail(testReport);		
				return ResponseEntity
					.status(HttpStatus.OK)
					.body("Report Send");
				
			} else if (ContactValidator.isValidMobileNumber(c.getData())){	
				try {			
					// Construct data
					String apiKey = "apikey=" + "Yn2BvW3C3fs-qmgn5rnhQKCdsTOjbsxoCAd0Ie4Lxj";
					String message = "&message=" + "Your Test Report Has Been Send";
					String sender = "&sender=" + "TXTLCL";			
					String numbers = "&numbers=" + (c.getData());			
					// Send data
					HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
					String data = apiKey + numbers + message + sender;
					conn.setDoOutput(true);
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
					conn.getOutputStream().write(data.getBytes("UTF-8"));
					final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					final StringBuffer stringBuffer = new StringBuffer();
					String line;
					while ((line = rd.readLine()) != null) {
						stringBuffer.append(line);
					}
					rd.close();
					System.out.println(stringBuffer.toString());				
				} 
				catch (Exception e) {
				System.out.println("Error SMS "+e);	
			}			
			}
			
		}
		return ResponseEntity
				.status(HttpStatus.OK)
				.body("Report Send");
		
		
//		System.out.println("User :"+user);
//		if(user!=null  ) {
//			return ResponseEntity
//					.status(HttpStatus.OK)
//					.body("User Is Present");
//		}else {
//			return ResponseEntity
//					.status(HttpStatus.CONFLICT)
//					.body("User Is NULL");
//		}
//		if(user !=null && (ContactValidator.isValidEmailAddress(user.getContact())))
//		{
//			String appUrl = "http://test.examsdaily.in";
//			//String appUrl = "http://localhost";
//			SimpleMailMessage testReport = new SimpleMailMessage();
//			testReport.setTo(dto.getContact());
//			testReport.setSubject("Test Report");
//			testReport.setText("Your Test Report Has Been Send");
//			testReport.setFrom("noreply@examsdaily.in");
//			emailService.sendEmail(emailVerification);		
//			return ResponseEntity
//				.status(HttpStatus.OK)
//				.body("Report Send");
//		}
//		 else if (ContactValidator.isValidMobileNumber(user.getContact())) {		    	
//			try {			
//				// Construct data
//				String apiKey = "apikey=" + "Yn2BvW3C3fs-qmgn5rnhQKCdsTOjbsxoCAd0Ie4Lxj";
//				String message = "&message=" + "Your Test Report Has Been Send";
//				String sender = "&sender=" + "TXTLCL";			
//				String numbers = "&numbers=" + (dto.getContact());			
//				// Send data
//				HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
//				String data = apiKey + numbers + message + sender;
//				conn.setDoOutput(true);
//				conn.setRequestMethod("POST");
//				conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
//				conn.getOutputStream().write(data.getBytes("UTF-8"));
//				final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//				final StringBuffer stringBuffer = new StringBuffer();
//				String line;
//				while ((line = rd.readLine()) != null) {
//					stringBuffer.append(line);
//				}
//				rd.close();
//				System.out.println(stringBuffer.toString());				
//			} 
//			catch (Exception e) {
//			System.out.println("Error SMS "+e);	
//		}			
//		}
//		return ResponseEntity
//				.status(HttpStatus.OK)
//				.body("Report Send");
//		
	}*/
}
	
