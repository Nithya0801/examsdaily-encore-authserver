package com.dexter.dextest.oauth2.repo;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dexter.dextest.oauth2.model.User;
import com.dexter.dextest.oauth2.model.Verify;



public interface UserRepository extends JpaRepository<User, String> {

	/**
	 * Find a user by username
	 *
	 * @param username
	 *            the user's username
	 * @return user which contains the user with the given username or null.
	 */
	@Query("SELECT u from User u where u.username=?1")	
	User findOneByUsername(String username);
	
	@Query("SELECT u from User u where u.username=?1")	
	User findByUsername(String username);

	@Query("SELECT c.userId from Contact c WHERE c.type='email' AND c.data=?1")
	User findByEmail(String email);

	@Query("SELECT c.userId from Contact c WHERE c.type='mobile' AND c.data=?1")
	User findByMobile(String mobile);
	
	@Query("from User u ORDER BY u.username ASC")
	List<User> findAllUsers();
	
	@Query("SELECT u from User u where u.confirmationToken=?1")	
	 User findByConfirmationToken(String confirmationToken);
	
	@Query("SELECT u from User u where u.userId=?1")	
	User findByUserId(String userId);
	
	@Query("SELECT u from User u where u.generatedOtp=?1")	
	 User findByGeneratedOtp(String generatedOtp);

	@Query("from User u ORDER BY u.username ASC")
	List<User> getAllUsers(Pageable pageable);
	
	@Query("SELECT count(u) from User u")
	Long getUsersCount();
	
	
}
