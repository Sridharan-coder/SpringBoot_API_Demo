package com.e_commerce.Entry1.dao;

import org.springframework.http.ResponseEntity;

import com.e_commerce.Entry1.dto.UserDetails;

public interface UserDAO {
	
	ResponseEntity<?> getUser(Integer u_id);
	ResponseEntity<?> addUser(UserDetails userDetails);
	ResponseEntity<?> updateUser(Integer u_id,UserDetails userDetails);
	ResponseEntity<?> deleteUser(Integer u_id);
	ResponseEntity<?> userLogin(UserDetails user);

}
