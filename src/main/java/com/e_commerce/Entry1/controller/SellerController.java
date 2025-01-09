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

import com.e_commerce.Entry1.dao.SellerDAO;
import com.e_commerce.Entry1.dto.Seller;

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
@RequestMapping("/seller")
public class SellerController {
	
	@Autowired
	private SellerDAO sellerDAO;
	
	@PostMapping("/createSeller")
	public ResponseEntity<?> addSeller(@RequestBody Seller seller) {
		System.out.println("Seller Creation"+seller.toString());
		return sellerDAO.addSeller(seller);
	}

	@GetMapping("/sellerDetails/{s_id}")
	public ResponseEntity<?> getSeller(@PathVariable Integer s_id) {
		return sellerDAO.getSeller(s_id);
	}

	@PutMapping("/updateSeller/{s_id}")
	public ResponseEntity<?> updateSeller(@PathVariable Integer s_id,@RequestBody Seller seller) {
		return sellerDAO.updateSeller(s_id,seller);
	}

	@DeleteMapping("/deleteSeller/{s_id}")
	public ResponseEntity<?> deleteSeller(@PathVariable Integer s_id) {
		return sellerDAO.deleteSeller(s_id);
	}	

	@PostMapping("/sellerLogin")
	public ResponseEntity<?> sellerLogin(@RequestBody Seller seller) {
		System.out.println("Seller --> "+seller.toString());
		return sellerDAO.sellerLogin(seller);
	}

}
