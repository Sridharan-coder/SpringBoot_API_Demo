package com.e_commerce.Entry1.dao;

import org.springframework.http.ResponseEntity;

import com.e_commerce.Entry1.dto.Warehouse;

public interface WarehouseDAO {
	
	ResponseEntity<?> getProductById(Integer p_id); 
	ResponseEntity<?> getProductByType(String p_type);
	ResponseEntity<?> addProducts(Warehouse product);
	ResponseEntity<?> updateProduct(Integer p_id,Warehouse product);
	ResponseEntity<?> deleteProduct(Integer p_id);

}
