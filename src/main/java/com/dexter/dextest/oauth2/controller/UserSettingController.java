package com.dexter.dextest.oauth2.controller;

//import java.awt.image.BufferedImage;
//
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.UUID;
//
//import javax.imageio.IIOImage;
//import javax.imageio.ImageIO;
//import javax.imageio.ImageWriteParam;
//import javax.imageio.ImageWriter;
//import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
//import javax.imageio.stream.FileImageOutputStream;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.dexter.dextest.oauth2.dto.PasswordResetDto;
//import com.dexter.dextest.oauth2.dto.ProfileDto;
//import com.dexter.dextest.oauth2.dto.RegisterDto;
//import com.dexter.dextest.oauth2.model.User;
//import com.dexter.dextest.oauth2.repo.UserRepository;
//
//import com.dexter.dextest.oauth2.service.impl.UserService;
//import com.fasterxml.uuid.Generators;

//@Configuration
//@RestController
//@RequestMapping("/setting")

public class UserSettingController {
	
/*	@Autowired
	private Environment env;
	
	@Autowired	
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/profile")
	public ResponseEntity<?> profileUpdate(@RequestBody final ProfileDto dto){
		User user=userService.findByUserId(dto.getUserId());		
	if(user!=null ) {
//		user.setFirstName(dto.getFirstName());
//		user.setLastName(dto.getLastName());
//		userRepository.save(user);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body("Profile Updated Successfully!!!");				
	}
	return ResponseEntity
			.status(HttpStatus.CONFLICT)
			.body("Something went wrong, Try again");
	}
	@PostMapping("/security")
	public ResponseEntity<?>profileSecurity(@RequestBody final PasswordResetDto dto){
		User user=userService.findByUserId(dto.getUserId());		
		BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();	
		System.out.println(passwordEncoder.matches(dto.getOldPassword(), user.getPassword()));
		if(user!=null && passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
  			user.setPassword(passwordEncoder.encode(dto.getNewPassword()));	
  			user.setPasscode(dto.getNewPassword());	
			userRepository.saveAndFlush(user);
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body("Password change Successfully!!!");		
	}
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body("Something went wrong, Try again!!");
	}	
	@PostMapping("/uploadImage/{userId}")
    public ResponseEntity<?> uploadImage(@PathVariable(name="userId") final String userId, @RequestParam("file") MultipartFile uploadimage)  {
        String picture_url = null;
		if (uploadimage.isEmpty()) {
            return new ResponseEntity<>("Please select a file!", HttpStatus.OK);
        }
	try {
		byte[] picData=uploadimage.getBytes();	
        BufferedImage imag = ImageIO.read(new ByteArrayInputStream(picData));      
		final ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
		UUID uuid2 = Generators.randomBasedGenerator().generate();	
		//ImageIO.write(imag, "jpg", new File("./uploads/avatars" +"/"+ uuid2.toString()+uploadimage.getOriginalFilename()));
//		writer.setOutput(new FileImageOutputStream(new File("./uploads/avatars" +"/"+ uuid2.toString() + uploadimage.getOriginalFilename())));
		
		writer.setOutput(new FileImageOutputStream(new File(env.getProperty("resource.uploads.location") + "/avatars" +"/"+ uuid2.toString() + uploadimage.getOriginalFilename())));		
//		writer.setOutput(new FileImageOutputStream(new File("/home/dexter/workspace/uploads/avatars" +"/"+ uuid2.toString() + uploadimage.getOriginalFilename())));
//		writer.setOutput(new FileImageOutputStream(new File("/home/dexter/wspace/uploads/avatars" +"/"+ uuid2.toString() + uploadimage.getOriginalFilename())));
		JPEGImageWriteParam jpegParams = new JPEGImageWriteParam(null);
		jpegParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);	
		jpegParams.setCompressionQuality(0.1F);
		writer.write(null, new IIOImage(imag, null, null), jpegParams);
		User user=userRepository.findById(userId).get();
		picture_url = "__dexters_resource_location_9836758498731097845__" + uuid2.toString()+ uploadimage.getOriginalFilename() +"?access_token=__dexters_access_token_9836758498731097845__";
		if(user!=null) {			
		    user.setAvatar(picture_url);
			userRepository.save(user);
		}		
		System.out.println(user);
	} catch (IOException e) {
		System.out.println(e.getMessage());
	}
	   return ResponseEntity
       		.status(HttpStatus.OK)
       		.body(picture_url);	
	}*/
}





		



