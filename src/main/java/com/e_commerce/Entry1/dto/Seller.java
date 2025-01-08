package com.e_commerce.Entry1.dto;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Document(collection = "sellers")
public class Seller {

	@Indexed(unique = true)
	@NotBlank(message = "ID is mandatory")
	private Integer s_id;

	@NotBlank(message = "Name is mandatory")
	private String s_name;

	@Indexed(unique = true)
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone number must be 10 digits and start with 6, 7, 8, or 9")
	@NotBlank(message = "Phone number is mandatory")
	private Long s_phoneNumber;

	@Email(message = "Email should be valid")
	@NotBlank(message = "Email is mandatory")
	private String s_emailAddress;

	@Size(min = 8, message = "Password should have at least 8 characters")
	@NotBlank(message = "Password is mandatory")
	private String s_password;

	@NotBlank(message = "Version is mandatory")
	private Integer __v;

	public Seller() {
		super();
	}

	public Seller(Integer s_id, String s_name, Long s_phoneNumber, String s_emailAddress, String s_password,
			Integer __v) {
		super();
		this.s_id = s_id;
		this.s_name = s_name;
		this.s_phoneNumber = s_phoneNumber;
		this.s_emailAddress = s_emailAddress;
		this.s_password = s_password;
		this.__v = __v;
	}

	public Integer getS_id() {
		return s_id;
	}

	public void setS_id(Integer s_id) {
		this.s_id = s_id;
	}

	public String getS_name() {
		return s_name;
	}

	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	public Long getS_phoneNumber() {
		return s_phoneNumber;
	}

	public void setS_phoneNumber(Long s_phoneNumber) {
		this.s_phoneNumber = s_phoneNumber;
	}

	public String getS_emailAddress() {
		return s_emailAddress;
	}

	public void setS_emailAddress(String s_emailAddress) {
		this.s_emailAddress = s_emailAddress;
	}

	public Integer get__v() {
		return __v;
	}

	public void set__v(Integer __v) {
		this.__v = __v;
	}

	public String getS_password() {
		return s_password;
	}

	public void setS_password(String s_password) {
		this.s_password = s_password;
	}

	@Override
	public String toString() {
		return "Seller [s_id=" + s_id + ", s_name=" + s_name + ", s_phoneNumber=" + s_phoneNumber + ", s_emailAddress="
				+ s_emailAddress + ", s_password=" + s_password + ", __v=" + __v + "]";
	}

}
