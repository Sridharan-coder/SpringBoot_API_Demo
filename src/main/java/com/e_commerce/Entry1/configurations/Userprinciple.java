package com.e_commerce.Entry1.configurations;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.e_commerce.Entry1.dto.Seller;

public class Userprinciple implements UserDetails{
	
	
	
	private Seller seller;
	
	

	public Userprinciple(Seller seller) {
		super();
		this.seller = seller;
		System.err.println(seller.toString());
		System.out.println("http ---> ");
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority("USER"));
	}

	@Override
	public String getPassword() {
		return seller.getS_password();
	}

	@Override
	public String getUsername() {
		return seller.getS_emailAddress();
	}

}
