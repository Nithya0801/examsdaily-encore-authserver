package com.dexter.dextest.oauth2.controller;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.social.facebook.api.Facebook;
//import org.springframework.social.facebook.api.User;
//import org.springframework.social.facebook.api.impl.FacebookTemplate;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import com.dexter.dextest.oauth2.dto.FRegisterDto;
//import com.dexter.dextest.oauth2.dto.RegisterDto;
//import com.dexter.dextest.oauth2.dto.UserDto;
//import com.dexter.dextest.oauth2.model.Verify;
//import com.dexter.dextest.oauth2.repo.VerifyRepository;
//import com.dexter.dextest.oauth2.service.EmailService;
//import com.dexter.dextest.oauth2.service.impl.UserService;
//import com.dexter.dextest.oauth2.service.impl.VerifyService;
//import com.dexter.dextest.oauth2.utilities.ContactValidator;

//@RestController
//@RequestMapping("/account")
public class FacebookController {
/*	@Autowired
	private UserService userService;
	
	@Autowired	
	private VerifyRepository verifyRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private VerifyService verifyService;
	
	@GetMapping("/facebook/status/{ftoken}")
	public ResponseEntity<?> facebookStatus(@PathVariable final String ftoken){
		Facebook facebook = new FacebookTemplate(ftoken,"examsdaily-test-app");
			if (ftoken != null) {
				String [] fields = { "id", "email",  "first_name", "last_name","name" };
				User userProfile= facebook.fetchObject ("me",User.class, fields);
					String name=userProfile.getName();
					String email=userProfile.getEmail();
					String firstname=userProfile.getFirstName();
					String lastname=userProfile.getLastName();   
					if(userProfile.getEmail()!=null  ) {
						UserDto dto = userService.findByEmail(email);	
						if(dto!=null)
							return ResponseEntity
								.status(HttpStatus.OK)
								.body(dto);
						else
							return ResponseEntity
								.status(HttpStatus.OK)
								.body(userProfile);
					}
					else  {							
						UserDto dto1 =userService.findByUsername(name);
						if(dto1!=null)
							return ResponseEntity
								.status(HttpStatus.OK)
								.body(dto1);
						else
							return ResponseEntity
								.status(HttpStatus.OK)
								.body(userProfile);
					}
					}
					return ResponseEntity
						.status(HttpStatus.CONFLICT)
						.body("Something went wrong, Try again");
			}
	
	@PostMapping("/facebook")
	public ResponseEntity<?> facebookEmail(@RequestBody final FRegisterDto dto){
		Facebook facebook = new FacebookTemplate(dto.getFtoken(),"app");
			if (dto.getFtoken()!= null) {
				String [] fields = { "id", "email","first_name", "last_name","name" };
				User userProfile= facebook.fetchObject ("me",User.class, fields);
					String username=userProfile.getName();
					String contact=userProfile.getEmail();
					String firstname=userProfile.getFirstName();
					String lastname=userProfile.getLastName();
			
						if(userService.findByUsername(username)!=null) {
							return ResponseEntity
								.status(HttpStatus.CONFLICT)
								.body("Username already exsits");
						}
						else if(userService.findByEmail(contact)!=null ) {
							return ResponseEntity
								.status(HttpStatus.CONFLICT)
								.body("Email already exsits");
						}
						else if(userService.findByMobile(contact)!=null ) {
							return ResponseEntity
								.status(HttpStatus.CONFLICT)
								.body("Mobile already exsits");					
						}
						else {	       	
							RegisterDto user = new RegisterDto();
								//	user.setContact(mobile);
								user.setContact(contact);		          
								user.setFirstName(firstname);
								user.setAvatar(facebook.getBaseGraphApiUrl() + userProfile.getId() + "/picture?type=small");
								user.setLastName(lastname);
								user.setPassword(dto.getPassword());
								user.setRegisterMode("FACEBOOK");	
								
								int index =contact.indexOf('@');
								String emailId = contact.substring(0,index);
								user.setUsername(emailId);							
								userService.registerUser(user);
								System.out.println("Facebook Fetching Details Saved To Database Successfully..!!");	        		
					
								SimpleMailMessage registrationEmail = new SimpleMailMessage();
								registrationEmail.setTo(contact);
								registrationEmail.setSubject("THANK YOU FOR REGISTERING<EXAMSDAILY.IN>");
								registrationEmail.setText("Dear," +user.getFirstName()+""+user.getLastName()+"\n"+
										"Welcome To ExamsDaily. Your Account Has Been Successfully Activated.\n\n" +
										"With Regards,\n" +
										"Developer Team.(ExamsDaily.in)");
								registrationEmail.setFrom("noreply@examsdaily.in");				
								emailService.sendEmail(registrationEmail);
								System.out.print("Valid ID Token..User Register By Facebook Success.");
								return ResponseEntity.ok(user);      
						}		
				}
				else {
					System.out.println("Invalid ID token.");
					return ResponseEntity
	        		  .status(HttpStatus.FORBIDDEN)
	        		  .body("Authentication failed");
				}
			}
	
	@PostMapping("/facebook/mobile")
	public ResponseEntity<?> facebook(@RequestBody final FRegisterDto dto){
		Facebook facebook = new FacebookTemplate(dto.getFtoken(),"app");
			if (dto.getFtoken()!= null) {
				String [] fields = { "id", "email","first_name", "last_name","name" };
				User userProfile= facebook.fetchObject ("me",User.class, fields);
					String username=userProfile.getName();
					String contact=userProfile.getEmail()!=null ? userProfile.getEmail():username;
					String firstname=userProfile.getFirstName();
					String lastname=userProfile.getLastName();
					Verify verify=verifyService.findByVerifyId(dto.getVerifyId()) ;
	
						if(userService.findByUsername(username)!=null && verify!=null) {
							return ResponseEntity
								.status(HttpStatus.CONFLICT)
								.body("Username already exsits");
						}
						else if(userService.findByEmail(contact)!=null && verify!=null) {
							return ResponseEntity
								.status(HttpStatus.CONFLICT)
								.body("Email already exsits");
						}
						else if(userService.findByMobile(contact)!=null && verify!=null) {
							return ResponseEntity
								.status(HttpStatus.CONFLICT)
								.body("Mobile already exsits");					
						}
						else {	       	
							RegisterDto user = new RegisterDto();
								//	user.setContact(mobile);
								user.setContact(verify.getContact());		         
								user.setFirstName(firstname);
								user.setAvatar(facebook.getBaseGraphApiUrl() + userProfile.getId() + "/picture?type=small");
								user.setLastName(lastname);
								user.setPassword(dto.getPassword());
								user.setRegisterMode("FACEBOOKMB");
								
									if(userProfile.getEmail()!=null  ) {
										if(ContactValidator.isValidEmailAddress(userProfile.getEmail())) {					
											int index = userProfile.getEmail().indexOf('@');
											String emailId = userProfile.getEmail().substring(0,index);
											user.setUsername(emailId);
										}
									}
									else {							
										user.setUsername(userProfile.getName());
										
									}
									userService.registerUser(user);
									System.out.println("Facebook Fetching Details Saved To Database Successfully..!!");	        		
									SimpleMailMessage registrationEmail = new SimpleMailMessage();
									registrationEmail.setTo(verify.getContact());
									registrationEmail.setSubject("THANK YOU FOR REGISTERING<EXAMSDAILY.IN>");
									registrationEmail.setText("Dear," +user.getFirstName()+""+user.getLastName()+"\n"+
											"Welcome To ExamsDaily. Your Account Has Been Successfully Activated.\n\n" +
											"With Regards,\n" +
											"Developer Team.(ExamsDaily.in)");
									registrationEmail.setFrom("noreply@examsdaily.in");				
									emailService.sendEmail(registrationEmail);
									System.out.print("Valid ID Token..User Register By Facebook Success.");
									return ResponseEntity.ok(user);      
						}		
				}
				else {
					System.out.println("Invalid ID token.");
					return ResponseEntity
						.status(HttpStatus.FORBIDDEN)
						.body("Authentication failed");
				}
			}
	
	@PostMapping("/contact")
	public ResponseEntity<?> profileUpdate(@RequestBody final RegisterDto dto ){
		UserDto emailExists=userService.findByEmail(dto.getContact());
			if (emailExists==null ) {			
				Verify verify =new Verify();
					if (ContactValidator.isValidEmailAddress(dto.getContact())) {
						verify.setType("email");
						verify.setContact(dto.getContact());
						verify.setVerified(false);	
					}
						verifyRepository.save(verify);
						return ResponseEntity
							.status(HttpStatus.CREATED)
							.body(verify);	
				}		
				return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body("Something went wrong, Try again");
			}
	
	*/
	
	/*@PostMapping("/facebookMobile")
	public ResponseEntity<?> fb(@RequestBody final FRegisterDto dto){
		Facebook facebook = new FacebookTemplate(dto.getFtoken(),"app");
			if (dto.getFtoken()!= null) {
				String [] fields = { "id", "email","first_name", "last_name","name" };
				User userProfile= facebook.fetchObject ("me",User.class, fields);
					String username=userProfile.getName();
					String contact=userProfile.getEmail()!=null ? userProfile.getEmail():username;
					String firstname=userProfile.getFirstName();
					String lastname=userProfile.getLastName();
				
						if(userService.findByUsername(username)!=null) {
							return ResponseEntity
								.status(HttpStatus.CONFLICT)
								.body("Username already exsits");
						}
						else if(userService.findByEmail(contact)!=null ) {
							return ResponseEntity
								.status(HttpStatus.CONFLICT)
								.body("Email already exsits");
						}
						else if(userService.findByMobile(contact)!=null ) {
							return ResponseEntity
								.status(HttpStatus.CONFLICT)
								.body("Mobile already exsits");					
						}
						else {	
							
							
							RegisterDto user = new RegisterDto();
								//	user.setContact(mobile);
								user.setContact(dto.getContact());		          
								user.setFirstName(firstname);
								user.setAvatar(facebook.getBaseGraphApiUrl() + userProfile.getId() + "/photo.jpg");
								user.setLastName(lastname);
								user.setPassword(dto.getPassword());
								user.setRegisterMode("FACEBOOK");	
								
								if(userProfile.getEmail()!=null  ) {
									if(ContactValidator.isValidEmailAddress(userProfile.getEmail())) {					
										int index = userProfile.getEmail().indexOf('@');
										String emailId = userProfile.getEmail().substring(0,index);
										user.setUsername(emailId);
									}
								}
								else {							
									user.setUsername(userProfile.getName());
								}					
								userService.registerUser(user);
								System.out.println("Facebook Fetching Details Saved To Database Successfully..!!");	        		
					
								SimpleMailMessage registrationEmail = new SimpleMailMessage();
								registrationEmail.setTo(dto.getContact());
								registrationEmail.setSubject("THANK YOU FOR REGISTERING<EXAMSDAILY.IN>");
								registrationEmail.setText("Dear," +user.getFirstName()+""+user.getLastName()+"\n"+
										"Welcome To ExamsDaily. Your Account Has Been Successfully Activated.\n\n" +
										"With Regards,\n" +
										"Developer Team.(ExamsDaily.in)");
								registrationEmail.setFrom("noreply@examsdaily.in");				
								emailService.sendEmail(registrationEmail);
								System.out.print("Valid ID Token..User Register By Facebook Success.");
								return ResponseEntity.ok(user);      
						}		
				}
				else {
					System.out.println("Invalid ID token.");
					return ResponseEntity
	        		  .status(HttpStatus.FORBIDDEN)
	        		  .body("Authentication failed");
				}
			}*/

		}


