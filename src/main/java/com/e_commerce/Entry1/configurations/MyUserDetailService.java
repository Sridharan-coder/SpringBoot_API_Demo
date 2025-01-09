package com.e_commerce.Entry1.configurations;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.e_commerce.Entry1.dto.Seller;
import com.e_commerce.Entry1.repository.SellerRepository;

@Service
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	private SellerRepository sellerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		
		Seller seller=sellerRepository.findByS_EmailAddress(username);

		if (seller.getS_emailAddress().equals(username)) { 
			return User.withUsername(username) 
					.password(seller.getS_password()) // Use {noop} prefix for plain text password 
					.authorities(Collections.emptyList()) .build(); 
		}
	else{
		throw new UsernameNotFoundException("User not found");
	}

}

}
