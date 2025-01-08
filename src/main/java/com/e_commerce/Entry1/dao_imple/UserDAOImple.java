package com.e_commerce.Entry1.dao_imple;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.e_commerce.Entry1.controller.UserController;
import com.e_commerce.Entry1.dao.UserDAO;
import com.e_commerce.Entry1.dto.UserDetails;
import com.e_commerce.Entry1.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserDAOImple implements UserDAO {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private MongoTemplate mongoTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

	@Override
	public ResponseEntity<?> getUser(Integer u_id) {

		Map<String, Object> map = new LinkedHashMap<>();
		try {

			Optional<UserDetails> user = userRepository.findOne(u_id);

			if (!user.isPresent()) {
				map.put("success", true);
				map.put("message", "User not found");
				return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
			}

			logger.info("Fetched User: {}", user.get());
			map.put("success", true);
			map.put("message", "User Fetched successfully");
			map.put("user", user.get());
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);

			map.put("success", false);
			map.put("message", e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.METHOD_NOT_ALLOWED);
		}
	}

	@Override
	public ResponseEntity<?> addUser(UserDetails userDetails) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try {

			int min = 1000;
			int max = 9999;
			int randomNum = (int) (Math.random() * (max - min + 1) + min);

			Integer u_id=randomNum;
			String u_name=userDetails.getU_name();
			Long u_phoneNumber=userDetails.getU_phoneNumber();
			String u_emailAddress=userDetails.getU_emailAddress();
			String u_password=encoder.encode(userDetails.getU_password());
			ArrayList<Integer> u_carts=userDetails.getU_carts()==null?new ArrayList<>():userDetails.getU_carts();
			ArrayList<Integer> u_whitelist=userDetails.getU_whitelist()==null?new ArrayList<>():userDetails.getU_whitelist();
			Integer __v=0;
			
			UserDetails userDetailsTemp=new UserDetails(u_id, u_name, u_phoneNumber, u_emailAddress, u_password, u_carts, u_whitelist,__v);
		
			userRepository.save(userDetailsTemp);
			map.put("success", true);
			map.put("message", "User created succesfully !");
			return new ResponseEntity<>(map, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e);
			map.put("success", false);
			map.put("message", e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}
	}


	@Override
	public ResponseEntity<?> updateUser(Integer u_id,UserDetails UpdatedUserDetails) {
		Map<String, Object> map = new LinkedHashMap<>();
		try {

			Query query = new Query();
			query.addCriteria(Criteria.where("u_id").is(u_id));

			Update update = new Update();
			update.set("u_name", UpdatedUserDetails.getU_name());
			update.set("u_phoneNumber", UpdatedUserDetails.getU_phoneNumber());
			update.set("u_emailAddress", UpdatedUserDetails.getU_emailAddress());
			update.set("u_password", encoder.encode(UpdatedUserDetails.getU_password()));
			update.set("u_carts", UpdatedUserDetails.getU_carts());
			update.set("u_whitelist", UpdatedUserDetails.getU_whitelist());
			System.out.println(update);

			UserDetails updated = mongoTemplate.findAndModify(query, update, UserDetails.class);
			if (updated == null) {
				map.put("success", true);
				map.put("message", "User Not Found");
				return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
			}
			map.put("success", true);
			map.put("message", "User updated successfully");
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			map.put("success", false);
			map.put("message", e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.METHOD_NOT_ALLOWED);
		}
	}

	@Override
	public ResponseEntity<?> deleteUser(Integer u_id) {
		Map<String, Object> map = new LinkedHashMap<>();
		try {
			Query query = new Query();
	        query.addCriteria(Criteria.where("u_id").is(u_id));

	        long deletedCount = mongoTemplate.remove(query, UserDetails.class).getDeletedCount();

			if(deletedCount > 0) {
				map.put("success", true);
				map.put("message", "User Deleted successfully");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
			else {
				map.put("success", true);
				map.put("message", "User Not Found");
				return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
			}
		}
		catch(Exception e) {
			System.out.println(e);
			map.put("success", false);
			map.put("message", e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.METHOD_NOT_ALLOWED);
		}
	}

	@Override
	public ResponseEntity<?> userLogin(UserDetails user) {
		Map<String, Object> map = new LinkedHashMap<>();
		try {
			Optional<UserDetails> userTemp = userRepository.findByU_EmailAddress(user.getU_emailAddress());

			if (!userTemp.isPresent()) {
				map.put("success", true);
				map.put("message", "Seller not found");
				return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
			}
			String existingPassword=userTemp.get().getU_password();
			logger.info("Fetched User: {}", userTemp.get());
			if(existingPassword.equals(user.getU_password())) {
				map.put("success", true);
				map.put("message", "Login successfully");
				map.put("user", userTemp.get());
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
			else {
				map.put("success", true);
				map.put("message", "UserName or Password was incorrect");
				return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			System.out.println(e);

			map.put("success", false);
			map.put("message", e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.METHOD_NOT_ALLOWED);
		}
	}

}
