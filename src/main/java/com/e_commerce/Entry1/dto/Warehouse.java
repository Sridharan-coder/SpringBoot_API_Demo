package com.e_commerce.Entry1.dto;

import java.util.ArrayList;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;

@Document(collection = "warehouses")
public class Warehouse {

	@Indexed(unique = true)
	@NotBlank(message = "ID is mandatory")
	private Integer p_id;

	@NotBlank(message = "Name is mandatory")
	private String p_name;

	@NotBlank(message = "Price is mandatory")
	private Double p_price;

	@NotBlank(message = "Image is mandatory")
	private String p_image;

	@NotBlank(message = "Type is mandatory")
	private String p_type;

	@NotBlank(message = "Stock is mandatory")
	private Integer p_stock;

	@NotBlank(message = "Seller ID's are mandatory")
	private ArrayList<Integer> s_ids;

	@NotBlank(message = "Version is mandatory")
	private Integer __v;

	public Warehouse() {
		super();
	}

	public Warehouse(Integer p_id, String p_name, Double p_price, String p_image, String p_type, Integer p_stock,
			ArrayList<Integer> s_ids, Integer __v) {
		super();
		this.p_id = p_id;
		this.p_name = p_name;
		this.p_price = p_price;
		this.p_image = p_image;
		this.p_type = p_type;
		this.p_stock = p_stock;
		this.s_ids = s_ids;
		this.__v = __v;
	}

	public Integer getP_id() {
		return p_id;
	}

	public void setP_id(Integer p_id) {
		this.p_id = p_id;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public Double getP_price() {
		return p_price;
	}

	public void setP_price(Double p_price) {
		this.p_price = p_price;
	}

	public String getP_image() {
		return p_image;
	}

	public void setP_image(String p_image) {
		this.p_image = p_image;
	}

	public String getP_type() {
		return p_type;
	}

	public void setP_type(String p_type) {
		this.p_type = p_type;
	}

	public Integer getP_stock() {
		return p_stock;
	}

	public void setP_stock(Integer p_stock) {
		this.p_stock = p_stock;
	}

	public ArrayList<Integer> getS_ids() {
		return s_ids;
	}

	public void setS_ids(ArrayList<Integer> s_ids) {
		this.s_ids = s_ids;
	}

	public Integer get__v() {
		return __v;
	}

	public void set__v(Integer __v) {
		this.__v = __v;
	}

	@Override
	public String toString() {
		return "Warehouse [p_id=" + p_id + ", p_name=" + p_name + ", p_price=" + p_price + ", p_image=" + p_image
				+ ", p_type=" + p_type + ", p_stock=" + p_stock + ", s_ids=" + s_ids + ", __v=" + __v + "]";
	}

}
