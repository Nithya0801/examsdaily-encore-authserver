package com.dexter.dextest.oauth2.controller;

//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//
//import com.dexter.dextest.oauth2.dto.RegisterDto;
//import com.dexter.dextest.oauth2.dto.Status;
import com.dexter.dextest.oauth2.dto.UserDto;
//import com.dexter.dextest.oauth2.dto.VerifyDto;
//import com.dexter.dextest.oauth2.model.User;
import com.dexter.dextest.oauth2.service.impl.UserService;
import com.dexter.dextest.oauth2.utilities.ContactValidator;

@RequestMapping("/manage/user")
@RestController
public class UserManagementController {
	@Autowired private UserService userService;
/*	
	@PostMapping("/register/bulk")
	public ResponseEntity<?> registerBulk(@RequestBody final List<RegisterDto> dtoList){

//		Map<Integer, String> statusResult=new HashMap<Integer, String>();
		List<Status> statusResult=new ArrayList<>();
		int count=0;
		for(RegisterDto dto : dtoList) {
			System.out.println(dto.toString());
//			String data=" username: " + dto.getUsername() + ", contact: " + dto.getContact() + ", password : " + dto.getPassword() ;
			if(userService.findByUsername(dto.getUsername())!=null) {
//				statusResult.put(count, "{index: " + count + ", staus: failed, info: Username exists, " + data + "}");
				statusResult.add(new Status(dto.getUsername(), dto.getContact(), dto.getPassword(), "Failed", "Username exists"));
				count++;
				continue;
			}
			if(userService.findByEmail(dto.getContact())!=null) {
//				statusResult.put(count, "{index: " + count + ", staus: failed, info: Email exists, " + data + "}");
				statusResult.add(new Status(dto.getUsername(), dto.getContact(), dto.getPassword(), "Failed", "Email exists"));				
				count++;
				continue;			
			}
			if(userService.findByMobile(dto.getContact())!=null) {
//				statusResult.put(count, "{index: " + count + ", staus: failed, info: Mobile exists, " + data + "}");
				statusResult.add(new Status(dto.getUsername(), dto.getContact(), dto.getPassword(), "Failed", "Mobile exists"));				
				count++;
				continue;	
			}			
			
			
			userService.registerUser(dto);
			statusResult.add(new Status(dto.getUsername(), dto.getContact(), dto.getPassword(), "Success", "User Created"));
			count++;
		}
			
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(statusResult);
	}	
	@GetMapping("/findByEmail")
	public String findbyEmail(@RequestParam("email") String email) {
		if(userService.findByEmail(email)!=null)
			return "User Found";
		else
			return "User Not Found";				
	}
	
	@GetMapping("/findByMobile")
	public String findByMobile(@RequestParam("mobile") String mobile) {
		if(userService.findByMobile(mobile)!=null)
			return "User Found";
		else
			return "User Not Found";
				
	}
	*/
	@GetMapping("/find/{contact}")	
	public ResponseEntity<?> findByContact(@PathVariable String contact) {
		UserDto user=null;
		if(ContactValidator.isValidEmailAddress(contact)) {
			user=userService.findByEmail(contact);
		}else if(ContactValidator.isValidMobileNumber(contact)) {
			user=userService.findByMobile(contact);
		}else{
			user=userService.findByUsername(contact);
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(user);
	}	
/*	
	@GetMapping("/find/all")
	public ResponseEntity<?> findAll(){
		List<UserDto> users=userService.findAll();
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(users);
	}	
	*/
	/*=============================================================
	BELOW API USES DYNAMIC PAGINATION FOR DISPLAYING THE USERS LISTS
	=============================================================*/
	
/*	@GetMapping("/all/{page}/{count}")
	public ResponseEntity<List<UserDto>> getAll(@PathVariable("page") int page, @PathVariable("count") int count, HttpServletRequest req, HttpServletResponse res) throws ServletException {
		List<UserDto> users=userService.getAll(page, count);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(users);
	}	*/
	
	/*============================================
	BELOW API DISPLAYING THE TOTAL COUNTS OF USERS
	=============================================*/
	
/*	@GetMapping("/all")
	public ResponseEntity<?> all(){
		Long usersCount = userService.getUsersCount();
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(usersCount);
	}	
	
	@GetMapping("/delete/{userId}")
	public ResponseEntity<?> delete(@PathVariable final String userId){
		if(userService.deleteUser(userId)) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("User deleted successfully");			
		}
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body("Deleting the user failed");

	}	
	
	@PostMapping("/delete/selected")
	public ResponseEntity<?> deleteSelected(@RequestBody final UserDto dto){
		if(dto.getUsers() != null) {
			for(VerifyDto vd : dto.getUsers()) {
				userService.deleteUser(vd.getUserId());	
				}
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("Selected Users Deleted successfully");
		} else {
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body("Selected Users Deleting Failed");
			
		}
		

	}	
	
	@GetMapping("/activate/{userId}")
	public ResponseEntity<?> activate(@PathVariable final String userId){
		if(userService.enableUser(userId)) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("User activated successfully");			
		}
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body("Activating the user failed");

	}	
	@GetMapping("/deactivate/{userId}")
	public ResponseEntity<?> deactivate(@PathVariable final String userId){
		if(userService.disableUser(userId)) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("User deactivated successfully");			
		}
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body("Deactivating the user failed");

	}	*/
}
