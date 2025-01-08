package com.e_commerce.Entry1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce.Entry1.dao.UserDAO;
import com.e_commerce.Entry1.dto.UserDetails;

@CrossOrigin(
	    origins = {
	        "http://localhost:3000"
	        },
	    methods = {
	                RequestMethod.OPTIONS,
	                RequestMethod.GET,
	                RequestMethod.PUT,
	                RequestMethod.DELETE,
	                RequestMethod.POST
	})
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserDAO userDAO;
	
	@PostMapping("/createUser")
	public ResponseEntity<?> addUser(@RequestBody UserDetails userDetails) {
		return userDAO.addUser(userDetails);
	}

	@GetMapping("/getUserDetals/{u_id}")
	public ResponseEntity<?> getUser(@PathVariable Integer u_id) {
		return userDAO.getUser(u_id);
	}

	@PutMapping("/updateUser/{u_id}")
	public ResponseEntity<?> updateUser(@PathVariable Integer u_id,@RequestBody UserDetails userDetails) {
		return userDAO.updateUser(u_id,userDetails);
	}

	@DeleteMapping("/deleteUser/{u_id}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer u_id) {
		return userDAO.deleteUser(u_id);
	}

	@PostMapping("/userLogin")
	public ResponseEntity<?> userLogin(@RequestBody UserDetails seller) {
		return userDAO.userLogin(seller);
	}

}
