package com.e_commerce.Entry1.dao_imple;

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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.e_commerce.Entry1.controller.SellerController;
import com.e_commerce.Entry1.dao.SellerDAO;
import com.e_commerce.Entry1.dto.Seller;
import com.e_commerce.Entry1.repository.SellerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SellerDAOImple implements SellerDAO {

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JWTServices jwtServices;

	private static final Logger logger = LoggerFactory.getLogger(SellerController.class);

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	@Override
	public ResponseEntity<?> addSeller(Seller seller) {

		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try {

			int min = 1000;
			int max = 9999;
			int randomNum = (int) (Math.random() * (max - min + 1) + min);

			Integer s_id = randomNum;
			String s_name = seller.getS_name();
			Long s_phoneNumberLong = seller.getS_phoneNumber();
			String s_emailAddress = seller.getS_emailAddress();
			String s_password = encoder.encode(seller.getS_password());
			Integer __v = 0;
			Seller sellerTemp = new Seller(s_id, s_name, s_phoneNumberLong, s_emailAddress, s_password, __v);
			System.out.println("s_id type: " + sellerTemp.toString());

			sellerRepository.save(sellerTemp);
			map.put("success", true);
			map.put("message", "Seller created succesfully !");
			return new ResponseEntity<>(map, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e);
			map.put("success", false);
			map.put("message", e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.METHOD_NOT_ALLOWED);
		}

	}

	@Override
	public ResponseEntity<?> getSeller(Integer s_id) {
		Map<String, Object> map = new LinkedHashMap<>();
		try {

			Optional<Seller> seller = sellerRepository.findOne(s_id);

			if (!seller.isPresent()) {
				map.put("success", true);
				map.put("message", "Seller not found");
				return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
			}

			logger.info("Fetched Seller: {}", seller.get());
			map.put("success", true);
			map.put("message", "User Fetched successfully");
			map.put("seller", seller.get());
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);

			map.put("success", false);
			map.put("message", e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.METHOD_NOT_ALLOWED);
		}
	}

	@Override
	public ResponseEntity<?> updateSeller(Integer s_id, Seller updatedSeller) {
		Map<String, Object> map = new LinkedHashMap<>();
		try {

			Query query = new Query();
			query.addCriteria(Criteria.where("s_id").is(s_id));

			Update update = new Update();
			update.set("s_name", updatedSeller.getS_name());
			update.set("s_phoneNumber", updatedSeller.getS_phoneNumber());
			update.set("s_emailAddress", updatedSeller.getS_emailAddress());
			update.set("s_password", encoder.encode(updatedSeller.getS_password()));

			Seller updated = mongoTemplate.findAndModify(query, update, Seller.class);

			if (updated == null) {
				map.put("success", true);
				map.put("message", "Seller Not Found");
				return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
			}
			map.put("success", true);
			map.put("message", "Seller updated successfully");
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			map.put("success", false);
			map.put("message", e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.METHOD_NOT_ALLOWED);
		}
	}

	@Override
	public ResponseEntity<?> deleteSeller(Integer s_id) {
		Map<String, Object> map = new LinkedHashMap<>();
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("s_id").is(s_id));

			long deletedCount = mongoTemplate.remove(query, Seller.class).getDeletedCount();

			if (deletedCount > 0) {
				map.put("success", true);
				map.put("message", "Seller Deleted successfully");
				return new ResponseEntity<>(map, HttpStatus.OK);
			} else {
				map.put("success", true);
				map.put("message", "Seller Not Found");
				return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			System.out.println(e);
			map.put("success", false);
			map.put("message", e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.METHOD_NOT_ALLOWED);
		}
	}

	@Override
	public ResponseEntity<?> sellerLogin(Seller seller) {
		System.out.println("----------------------->"+seller.toString());
		Map<String, Object> map = new LinkedHashMap<>();
//		try {
		Seller sellerTemp = sellerRepository.findByS_EmailAddress(seller.getS_emailAddress());
//
//			if (sellerTemp==null) {
//				map.put("success", true);
//				map.put("message", "Seller not found");
//				return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
//			}
//			String existingPassword=sellerTemp.getS_password();
//			logger.info("Fetched Seller: {}", sellerTemp);
//			if(existingPassword.equals(seller.getS_password())) {
//				map.put("success", true);
//				map.put("message", "Login successfully");
//				map.put("seller", sellerTemp);
//				return new ResponseEntity<>(map, HttpStatus.OK);
//			}
//			else {
//				map.put("success", true);
//				map.put("message", "UserName or Password was incorrect");
//				return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
//			}
//		} catch (Exception e) {
//			System.out.println(e);
//
//			map.put("success", false);
//			map.put("message", e.getMessage());
//			return new ResponseEntity<>(map, HttpStatus.METHOD_NOT_ALLOWED);
//		}

		try {

			Authentication authentication = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(seller.getS_emailAddress(), seller.getS_password()));

			if (authentication.isAuthenticated()) {
				map.put("success", true);
				map.put("message", "Login successfully");
				map.put("seller", sellerTemp);
				map.put("token",jwtServices.generateToken(sellerTemp));
				return new ResponseEntity<>(map, HttpStatus.OK);
			} else {
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
