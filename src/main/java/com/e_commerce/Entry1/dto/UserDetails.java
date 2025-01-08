package com.e_commerce.Entry1.dto;

import java.util.ArrayList;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Document(collection = "userdetails")
public class UserDetails {

	@NotBlank(message = "ID is mandatory")
	@Indexed(unique = true)
	private Integer u_id;

	@NotBlank(message = "Name is mandatory")
	private String u_name;

	@Indexed(unique = true)
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone number must be 10 digits and start with 6, 7, 8, or 9")
	@NotBlank(message = "Name is mandatory")
	private Long u_phoneNumber;

	@NotBlank(message = "Name is mandatory")
	@Email(message = "Email should be valid")
	private String u_emailAddress;

	@NotBlank(message = "Name is mandatory")
	@Size(min = 8, message = "Password should have at least 8 characters")
	private String u_password;

	private ArrayList<Integer> u_carts;

	private ArrayList<Integer> u_whitelist;

	@NotBlank(message = "Version is mandatory")
	private Integer __v;

	public UserDetails() {
		super();
	}

	public UserDetails(Integer u_id, String u_name, Long u_phoneNumber, String u_emailAddress, String u_password,
			ArrayList<Integer> u_carts, ArrayList<Integer> u_whitelist, Integer __v) {
		super();
		this.u_id = u_id;
		this.u_name = u_name;
		this.u_phoneNumber = u_phoneNumber;
		this.u_emailAddress = u_emailAddress;
		this.u_password = u_password;
		this.u_carts = u_carts;
		this.u_whitelist = u_whitelist;
		this.__v = __v;
	}

	public Integer getU_id() {
		return u_id;
	}

	public void setU_id(Integer u_id) {
		this.u_id = u_id;
	}

	public String getU_name() {
		return u_name;
	}

	public void setU_name(String u_name) {
		this.u_name = u_name;
	}

	public Long getU_phoneNumber() {
		return u_phoneNumber;
	}

	public void setU_phoneNumber(Long u_phoneNumber) {
		this.u_phoneNumber = u_phoneNumber;
	}

	public String getU_emailAddress() {
		return u_emailAddress;
	}

	public void setU_emailAddress(String u_emailAddress) {
		this.u_emailAddress = u_emailAddress;
	}

	public String getU_password() {
		return u_password;
	}

	public void setU_password(String u_password) {
		this.u_password = u_password;
	}

	public ArrayList<Integer> getU_carts() {
		return u_carts;
	}

	public void setU_carts(ArrayList<Integer> u_carts) {
		this.u_carts = u_carts;
	}

	public ArrayList<Integer> getU_whitelist() {
		return u_whitelist;
	}

	public void setU_whitelist(ArrayList<Integer> u_whitelist) {
		this.u_whitelist = u_whitelist;
	}

	public Integer get__v() {
		return __v;
	}

	public void set__v(Integer __v) {
		this.__v = __v;
	}

	@Override
	public String toString() {
		return "UserDetails [u_id=" + u_id + ", u_name=" + u_name + ", u_phoneNumber=" + u_phoneNumber
				+ ", u_emailAddress=" + u_emailAddress + ", u_password=" + u_password + ", u_carts=" + u_carts
				+ ", u_whitelist=" + u_whitelist + ", __v=" + __v + "]";
	}

}
