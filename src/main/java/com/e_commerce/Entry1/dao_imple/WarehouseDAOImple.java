package com.e_commerce.Entry1.dao_imple;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.e_commerce.Entry1.controller.WarehouseController;
import com.e_commerce.Entry1.dao.WarehouseDAO;
import com.e_commerce.Entry1.dto.Warehouse;
import com.e_commerce.Entry1.repository.WarehouseRepository;


@Service
public class WarehouseDAOImple implements WarehouseDAO {

	@Autowired
	private WarehouseRepository warehouseRepository;
	
	@Autowired
    private MongoTemplate mongoTemplate;

	private static final Logger logger = LoggerFactory.getLogger(WarehouseController.class);


	@Override
	public ResponseEntity<?> getProductById(Integer p_id) {
		Map<String, Object> map = new LinkedHashMap<>();
		try {

			Optional<Warehouse> product = warehouseRepository.findOne(p_id);

			if (!product.isPresent()) {
				map.put("success", true);
				map.put("message", "Product not found");
				return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
			}

			logger.info("Fetched product: {}", product.get());
			map.put("success", true);
			map.put("message", "Product fetched successfully");
			map.put("product", product.get());
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			map.put("success", false);
			map.put("message", e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.METHOD_NOT_ALLOWED);
		}
	}

	@Override
	public ResponseEntity<?> getProductByType(String p_type) {
		Map<String, Object> map = new LinkedHashMap<>();
		try {
			List<Warehouse> product = warehouseRepository.findByP_Type(p_type);
			System.out.println("==="+product);
			
			if (product.isEmpty()) {
				map.put("success", true);
				map.put("message", "Product not found");
				return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
			}

			logger.info("Fetched product: {}", product);
			map.put("success", true);
			map.put("message", "Product fetched successfully");
			map.put("products", product);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			map.put("success", false);
			map.put("message", e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.METHOD_NOT_ALLOWED);
		}

	}

	@Override
	public ResponseEntity<?> addProducts(Warehouse product) {

		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try {

			int min = 1000;
			int max = 9999;
			int randomNum = (int) (Math.random() * (max - min + 1) + min);

			Integer p_id = randomNum;
			String p_name=product.getP_name();
			Double p_price=product.getP_price();
			String p_image=product.getP_image();
			String p_type=product.getP_type();
			Integer p_stock=product.getP_stock();
			ArrayList<Integer> s_ids=product.getS_ids();			
			Integer __v = 0;
			Warehouse productTemp=new Warehouse(p_id, p_name, p_price, p_image, p_type, p_stock, s_ids, __v);

			warehouseRepository.save(productTemp);
			map.put("success", true);
			map.put("message", "Product added successfully!");
			return new ResponseEntity<>(map, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e);
			map.put("success", false);
			map.put("message", e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.METHOD_NOT_ALLOWED);
		}

	}

	@Override
	public ResponseEntity<?> updateProduct(Integer p_id, Warehouse product) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try {

			Query query = new Query();
			query.addCriteria(Criteria.where("p_id").is(p_id));
			
			Update update = new Update();
			update.set("p_name",product.getP_name());
			update.set("p_price",product.getP_price());
			update.set("p_image",product.getP_image());
			update.set("p_type",product.getP_type());
			update.set("p_stock",product.getP_stock());
			update.set("s_ids",product.getS_ids());
			
			Warehouse updated=mongoTemplate.findAndModify(query, update, Warehouse.class);

			if (updated == null) {
				map.put("success", true);
				map.put("message", "Product Not Found");
				return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
			}
			map.put("success", true);
			map.put("message", "Product updated successfully");
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			map.put("success", false);
			map.put("message", e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.METHOD_NOT_ALLOWED);
		}
	} 

	@Override
	public ResponseEntity<?> deleteProduct(Integer p_id) {
		Map<String, Object> map = new LinkedHashMap<>();
		try {
			Query query = new Query();
	        query.addCriteria(Criteria.where("p_id").is(p_id));

	        long deletedCount = mongoTemplate.remove(query, Warehouse.class).getDeletedCount();

			if(deletedCount > 0) {
				map.put("success", true);
				map.put("message", "product removed successfully");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
			else {
				map.put("success", true);
				map.put("message", "Product Not Found");
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

}
