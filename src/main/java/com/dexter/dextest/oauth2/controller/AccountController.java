package com.dexter.dextest.oauth2.controller;

//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.security.GeneralSecurityException;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import com.dexter.dextest.oauth2.dto.GRegisterDto;
//import com.dexter.dextest.oauth2.dto.RegisterDto;
//import com.dexter.dextest.oauth2.dto.UserDto;
//import com.dexter.dextest.oauth2.dto.VerifyDto;
//import com.dexter.dextest.oauth2.model.Contact;
//import com.dexter.dextest.oauth2.model.User;
//import com.dexter.dextest.oauth2.model.Verify;
//import com.dexter.dextest.oauth2.repo.UserRepository;
//import com.dexter.dextest.oauth2.service.EmailService;
//import com.dexter.dextest.oauth2.service.IUserService;
//import com.dexter.dextest.oauth2.utilities.ContactValidator;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;


//@Configuration
//@RestController
//@RequestMapping("/account")
public class AccountController {
	
/*	@Autowired
	private IUserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;
	
	@GetMapping("/")
	public String index() {
		return "Connecting";	
	}
		
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody final RegisterDto dto){
	
//	if(userService.findByUsername(dto.getUsername())!=null && userService.findByEmail(dto.getContact())!=null && userService.findByMobile(dto.getContact())!=null) {		if(userService.findByUsername(dto.getUsername())!=null) {
		if(userService.findByUsername(dto.getUsername())!=null) {			
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body("Username Already Exists");	
		}
		if(userService.findByEmail(dto.getContact())!=null) {
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body("Email Already Exists");				
		}
		if(userService.findByMobile(dto.getContact())!=null) {
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body("Contact number Already Exists");	
		}

		
		userService.registerUser(dto);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body("User Created Successfully");
		
	}
	@GetMapping("/google/status/{gtoken}")
	public ResponseEntity<?> googleStatus(@PathVariable final String gtoken)  throws GeneralSecurityException, IOException{
		HttpTransport transport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
		        .setAudience(Arrays.asList("281176553760-78goki7ni419ccqfvfhi1hp6ol6vfj9j.apps.googleusercontent.com"))  
		        .setIssuer("accounts.google.com")
		        .build();
		GoogleIdToken idToken = verifier.verify(gtoken);
		        if (idToken != null) {
		          Payload payload = idToken.getPayload();
		          String userId = payload.getSubject();
		          String email = payload.getEmail();
		          UserDto dto = userService.findByEmail(email);	
		          if(dto!=null)
		        	  return ResponseEntity
		        			  .status(HttpStatus.OK)
		        			  .body(dto);
		          else
		        	  return ResponseEntity
		        			  .status(HttpStatus.OK)
		        			  .body(null);
		        }
		        return ResponseEntity
		        		.status(HttpStatus.CONFLICT)
		        		.body("Something went wrong, Try again");
	}
	
	@PostMapping("/google")
	public ResponseEntity<?> google(@RequestBody final GRegisterDto dto) throws GeneralSecurityException, IOException{
//	public ResponseEntity<?> google(@RequestParam("id_token") String id_token) throws GeneralSecurityException, IOException{	
		HttpTransport transport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
		        .setAudience(Arrays.asList("281176553760-78goki7ni419ccqfvfhi1hp6ol6vfj9j.apps.googleusercontent.com"))  
		        .setIssuer("accounts.google.com")
		        .build();
//				GoogleIdToken idToken = verifier.verify(userDto.getGoogleToken());
//		        GoogleIdToken idToken = verifier.verify(userDto.getGoogleToken());
		GoogleIdToken idToken = verifier.verify(dto.getGtoken());
		        if (idToken != null) {
		          Payload payload = idToken.getPayload();
		          String userId = payload.getSubject();
		          // Get profile information from payload
		          String email = payload.getEmail();
//		          boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
		          String name = (String) payload.get("name");
		          String pictureUrl = (String) payload.get("picture");
		          String familyName = (String) payload.get("family_name");
		          String givenName = (String) payload.get("given_name");
//		          User emailExists = userService.findByEmail(email); ===>
		          UserDto emailExists = userService.findByEmail(email);
		          if(emailExists!=null) {
		        	  System.out.println("Email Already Exists!!");
		        	  return ResponseEntity.ok(emailExists);
		          } else {		        	  
		        	  
		          RegisterDto user = new RegisterDto();
//		          user.setConfirmationToken(userId);===>
//		          user.setEmail(email);===>
		          user.setContact(email);		          
		          user.setFirstName(givenName);
//		          user.setPhoto(pictureUrl);===>
		          user.setAvatar(pictureUrl);
		          user.setLastName(familyName);
		          user.setPassword(dto.getPassword());
		          user.setRegisterMode("GOOGLE");
//		          user.setMobile(userDto.getMobile());===>
		          int index = email.indexOf('@');
		          String emailId = email.substring(0,index);
		          user.setUsername(emailId);
//		          userRepo.save(user);
		          userService.registerUser(user);
		          System.out.println("Google Fetching Details Saved To Database Successfully..!!");
		        	
			        SimpleMailMessage registrationEmail = new SimpleMailMessage();
					registrationEmail.setTo(user.getContact());
					registrationEmail.setSubject("THANK YOU FOR REGISTERING<EXAMSDAILY.IN>");
					registrationEmail.setText("Dear," +user.getFirstName()+""+user.getLastName()+"\n"+
							"Welcome To ExamsDaily. Your Account Has Been Successfully Activated.\n\n" +
							"With Regards,\n" +
							"Developer Team.(ExamsDaily.in)");
					registrationEmail.setFrom("noreply@examsdaily.in");
					
					emailService.sendEmail(registrationEmail);
		          
		          System.out.println(userId+email+name);
		          System.out.print("Valid ID Token..User Register By Google Success.");
		          return ResponseEntity.ok(user);
		          	}
		          } else {
		          System.out.println("Invalid ID token.");
//		          return ResponseEntity.ok("Invalid ID Token.");
		          return ResponseEntity
		        		  .status(HttpStatus.FORBIDDEN)
		        		  .body("Authentication failed");
	    }
			
		
	}
	
	
	@PutMapping("/password/reset")
	public ResponseEntity reset(@RequestBody final RegisterDto dto) {
		// Lookup user in database by e-mail
		User user=userRepository.findByEmail(dto.getContact());
		User mobileExists=userRepository.findByMobile(dto.getContact());
		if( user==null && mobileExists==null) {
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body("Sorry!!Email Not Exists In Our Database..\\n");				
		}
					
			else {
				// Generate random 36-character string token for reset password 
				if (ContactValidator.isValidEmailAddress(dto.getContact())){
				user.setConfirmationToken(UUID.randomUUID().toString());
			
				// Email message
				String appUrl = "http://test.examsdaily.in";
				SimpleMailMessage passwordReset = new SimpleMailMessage();
				passwordReset.setTo(dto.getContact());
				passwordReset.setSubject("PASSWORD RESET");
				passwordReset.setText("Dear,"+user.getFirstName()+""+user.getLastName()+"\n"+"To Reset Your Password, please click the link below:\n"
						+ appUrl + "/#/change_password?token=" + user.getConfirmationToken()+"\n\n"
						+"With Regards,\n" +
						"Developer Team.(ExamsDaily.in)");
				passwordReset.setFrom("noreply@examsdaily.in");
				emailService.sendEmail(passwordReset);
				// Save token to database
				userRepository.save(user);
				// Add success message to view
				System.out.println("An Password Reset Link Has Been Sent to -"+dto.getContact());
				return ResponseEntity.ok("An Password Reset Link Has Been Sent to -"+dto.getContact());
			
			}
				 else if (ContactValidator.isValidMobileNumber(dto.getContact())) {
					 try {
							int randomPIN = (int)(Math.random()*9000)+1000;
							String pin = ""+randomPIN;
							mobileExists.setGeneratedOtp(pin);
							// Construct data
							String apiKey = "apikey=" + "Yn2BvW3C3fs-qmgn5rnhQKCdsTOjbsxoCAd0Ie4Lxj";
							String message = "&message=" + "To verify your Mobile number, please enter the OTP number given below :\n" + mobileExists.getGeneratedOtp();
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
				// Save token to database
				userRepository.save(mobileExists);
				return ResponseEntity.ok("An OTP Has Been Sent to -"+dto.getContact());
			}
		
				
			}

	@GetMapping("/password/reset")
	public ResponseEntity<?> passwordreset(@RequestParam("token") String token,HttpSession session) {			
			User user = userService.findByConfirmationToken(token);
			session.setAttribute("rtoken", token); 		
			if (user == null) { 
				return ResponseEntity
						.status(HttpStatus.CONFLICT)
						.body("This is an invalid confirmation link.");			
			}		
			else {		
				System.out.println("Password Reset Link Verification Success..Entering Password Reset Section");
				return ResponseEntity
						.status(HttpStatus.OK)
						.body(user);			
			}	
	}
	
	
	
	@PostMapping("/password/reset")	
	public ResponseEntity<?> reset1(@RequestBody final RegisterDto dto){
//	String rtoken =(String)session.getAttribute("rtoken");
			String rtoken=dto.getConfirmationToken();			
				User user = userService.findByConfirmationToken(rtoken); 		
				if(user!=null) {		
					BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
	      			user.setPassword(passwordEncoder.encode(dto.getPassword()));	
	      			user.setPasscode(dto.getPassword());
				
					userRepository.save(user);
					SimpleMailMessage registrationEmail = new SimpleMailMessage();
					for(Contact c :user.getContacts()) {
						if(ContactValidator.isValidEmailAddress(c.getData()))
							registrationEmail.setTo(c.getData());
					}					
					registrationEmail.setSubject("SUCCESS");
					registrationEmail.setText("Dear," +user.getFirstName()+""+user.getLastName()+"\n"+
							"Your Password Has Been Reset Successfully..Now You Can Login Again..\n\n" +
							"With Regards,\n" +
							"Developer Team.(ExamsDaily.in)");
					registrationEmail.setFrom("noreply@examsdaily.in");
					emailService.sendEmail(registrationEmail);
				
					System.out.println("Your Password Reset Has Been Done Successfully!!");
					return ResponseEntity
							.status(HttpStatus.OK)
							.body("Your Password Reset Has Been Done Successfully!!");				
				}
      						
				System.out.println("Oops!  This is an invalid password reset link..!!");
				return ResponseEntity
						.status(HttpStatus.CONFLICT)
						.body("Oops!  This is an invalid password reset link...!!");	
				}	

	
	@PostMapping("/mobilePassword/otpCheck")
	public ResponseEntity<?> otpVerify1(@RequestBody final VerifyDto dto) {	
		UserDto user=userService.findByMobile(dto.getContact());	
		String existingOtp = user.getGeneratedOtp();
		String currentOtp = dto.getGeneratedOtp();
		boolean otpCheck = currentOtp.equals(existingOtp);
		System.out.println(currentOtp);
		System.out.println(existingOtp);
	
			if (user != null && otpCheck==false) { 
				return ResponseEntity
						.status(HttpStatus.CONFLICT)
						.body("This is an invalid otp.");
							
				}		
				else {
					return ResponseEntity
							.status(HttpStatus.OK)
							.body(user);			
				}
		}
	@PostMapping("/mobilePassword/otpVerify")
	public ResponseEntity<?> otpVerify(@RequestBody final RegisterDto dto){		
		User user=userService.findByUserId(dto.getUserId());
			if(user!=null){
				BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
      			user.setPassword(passwordEncoder.encode(dto.getPassword()));	
      			user.setPasscode(dto.getPassword());			
				userRepository.save(user);			
					return ResponseEntity
						.status(HttpStatus.OK)
						.body("Password Reset Successfully");
				}
					return ResponseEntity
						.status(HttpStatus.CONFLICT)
						.body("Something went wrong");							
		}	
	 */
	
}

//	@PostMapping("/login")
//	public ResponseEntity<User> login(@RequestParam("contact") String contact, @RequestParam("password") String password){		
//		return null;
//	}

	
	/*
	@PostMapping("/register")
	public ResponseEntity<Void> registerUser(@RequestBody  loginModel, UriComponentsBuilder ucBuilder) {

		if (userDetailDAO.getUserByEmail(loginModel.getRegisterEmail()) != null) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		userDetail = new UserDetail();
		userDetail.setUserId(IdGenerator.generateId("USR"));
		userDetail.setStatus('N');
		userDetail.setEmail(loginModel.getRegisterEmail());
		userDetail.setName(loginModel.getRegisterName());
		userDetail.setGender(loginModel.getRegisterGender());
		userDetail.setPassword(loginModel.getRegisterPassword());
		userDetailDAO.saveOrUpdateUserDetail(userDetail);

		users.setUserDetail(userDetail);
		users.setEmail(userDetail.getEmail());
		users.setPassword(userDetail.getPassword());
		users.setEnabled(true);
		userDAO.saveOrUpdateUser(users);

		userAuthorities.setUserDetail(userDetail);
		userAuthorities.setAuthority(loginModel.getRegisterRole());
		//.setAuthority("ROLE_ADMIN");
		userAuthorities.setEmail(userDetail.getEmail());
		userAuthoritiesDAO.saveOrUpdateUserAuthority(userAuthorities);

		String message = "Hello " + userDetail.getName() + " you're successfully registered with us, Thanks !";
		try {			
			emailsender.sendEmail(userDetail.getEmail(), "Registration Successfull", message);
		} catch (Exception e) {

		}
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ucBuilder.path("/register/{userId}").buildAndExpand(userDetail.getUserId()).toUri());
		return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);
	}*/
	

