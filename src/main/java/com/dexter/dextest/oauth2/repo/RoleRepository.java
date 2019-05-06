package com.dexter.dextest.oauth2.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dexter.dextest.oauth2.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	@Query("SELECT r FROM Role r WHERE r.roleName=?1")
	Role findByRole(String roleName);
	
}
