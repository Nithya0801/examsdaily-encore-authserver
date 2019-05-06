package com.dexter.dextest.oauth2.controller;

//import java.io.BufferedReader;
//
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.dexter.dextest.oauth2.dto.ProfileDto;
//import com.dexter.dextest.oauth2.dto.UserDto;
//import com.dexter.dextest.oauth2.dto.VerifyDto;
//import com.dexter.dextest.oauth2.model.Contact;
//import com.dexter.dextest.oauth2.model.User;
//import com.dexter.dextest.oauth2.model.Verify;
//import com.dexter.dextest.oauth2.repo.UserRepository;
//import com.dexter.dextest.oauth2.repo.VerifyRepository;
//import com.dexter.dextest.oauth2.service.EmailService;
//import com.dexter.dextest.oauth2.service.impl.UserService;
//import com.dexter.dextest.oauth2.service.impl.VerifyService;
//import com.dexter.dextest.oauth2.utilities.ContactValidator;



//@Configuration
//@RestController
//@RequestMapping("/contact")
public class VerifyController {
	/*	
	@Autowired	
	private VerifyRepository verifyRepository;
	
	@Autowired	
	private UserRepository userRepository;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private VerifyService verifyService;
	
	
	@PostMapping("/email")
	public ResponseEntity<?>contactEmail(@RequestBody final VerifyDto dto){
		User user=userService.findByUserId(dto.getUserId());	
		UserDto emailExists=userService.findByEmail(dto.getContact());	
		if (user!=null && emailExists==null) {			
			Verify verify =new Verify();
			    if (ContactValidator.isValidEmailAddress(dto.getContact())) {
					verify.setType("email");
					verify.setContact(dto.getContact());
					verify.setUserId(user);
					verify.setVerified(false);
					verify.setTokenGeneratedAt(LocalDateTime.now());
					verify.setVerificationToken(UUID.randomUUID().toString());	
		
					//String appUrl = "http://test.examsdaily.in";
					String appUrl = "http://localhost";
					SimpleMailMessage emailVerification = new SimpleMailMessage();
					emailVerification.setTo(dto.getContact());
					emailVerification.setSubject("EMAIL VERIFICATION");
					emailVerification.setText("Dear,"+user.getFirstName()+""+user.getLastName()+"\n"+"To Verify Your Email, please click the link below:\n"
							+ appUrl + ":9088/contact/tokenVerify?token=" + verify.getVerificationToken()+"\n\n"
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
		}				
		return ResponseEntity
			.status(HttpStatus.CONFLICT)
			.body("Email Already Exists/Please provide valid Email Address");		
	}
	
	@PostMapping("/mobile")
	public ResponseEntity<?>contactMobile(@RequestBody final VerifyDto dto){		
		User user=userService.findByUserId(dto.getUserId());
		UserDto userExists=userService.findByMobile(dto.getContact());		
		if (user!=null && userExists == null ) {			
			Verify verify =new Verify();
				if (ContactValidator.isValidMobileNumber(dto.getContact())) {
					verify.setType("mobile");
					verify.setContact(dto.getContact());
					verify.setUserId(user);
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
				verifyRepository.save(verify);			
				return ResponseEntity
					.status(HttpStatus.CREATED)
					.body("Mobile Number Updated Successfully!!!");		
			}
		}
		return ResponseEntity
			.status(HttpStatus.CONFLICT)
			.body("Mobile Number Already Exists/Please provide valid Mobile number");
		}
		
	@PostMapping("/otpverify")
	public ResponseEntity<?> otpVerify(@RequestBody final VerifyDto dto){		
		Verify otpVerify=verifyService.findByGeneratedOtp(dto.getGeneratedOtp());
		User user=userService.findByUserId(otpVerify.getUserId().getUserId());
			if(user!=null && otpVerify!=null){
				otpVerify.setVerified(true);
				verifyRepository.save(otpVerify);
				Contact contact = new Contact();				
					contact.setType("mobile");
					contact.setData(otpVerify.getContact());
					contact.setUserId(user);				
					user.getContacts().add(contact);						
					userRepository.save(user);				
					return ResponseEntity
						.status(HttpStatus.OK)
						.body("OTP number Verified Successfully");
				}
					return ResponseEntity
						.status(HttpStatus.CONFLICT)
						.body("Invalid OTP Entered");							
		}	
	
	@GetMapping("/tokenVerify")
	public ResponseEntity<?> tokenVerify1(@RequestParam(name="token") final String token) {					
		Verify tokenVerify=verifyService.findByVerificationToken(token);
//			session.setAttribute("token", token); 
			if (tokenVerify == null) { 
				return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body("This is an invalid confirmation link.");			
				}		
				else {
					return ResponseEntity
						.status(HttpStatus.OK)
						.body("Valid confirmation link!!!");			
				}
		}
	
	
	@PostMapping("/tokenVerify")	
	public ResponseEntity<?> tokenVerify(@RequestParam(name="token") final String token){
		//String token=dto.getConfirmationToken();
		Verify tokenVerify=verifyService.findByVerificationToken(token);	
		User user=userService.findByUserId(tokenVerify.getUserId().getUserId());				
			if(user!=null && tokenVerify!=null) {
				tokenVerify.setVerified(true);
				verifyRepository.save(tokenVerify);
				Contact contact = new Contact();				
					contact.setType("email");					
					contact.setData(tokenVerify.getContact());																		
					contact.setUserId(user);					
				    user.getContacts().add(contact);				
					userRepository.save(user);
				    return ResponseEntity
						.status(HttpStatus.OK)
						.body("User Verified Successfully");	
				}
				else {
					return ResponseEntity
						.status(HttpStatus.CONFLICT)
						.body("This is an invalid confirmation link.");					
				}
		}*/
	}


