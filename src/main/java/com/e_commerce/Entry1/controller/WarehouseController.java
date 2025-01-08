package com.e_commerce.Entry1.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.e_commerce.Entry1.dao.WarehouseDAO;
import com.e_commerce.Entry1.dto.Warehouse;

import jakarta.servlet.http.HttpServletRequest;




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
@RequestMapping("/product")
public class WarehouseController {

	@Autowired
	private WarehouseDAO warehouseDAO;
	
	@Value("${file.upload-dir}") 
	private String uploadDir;

	@GetMapping("/getProductById/{p_id}")
	public ResponseEntity<?> getProductById(@PathVariable Integer p_id) {
		return warehouseDAO.getProductById(p_id);
	}

	@GetMapping("/getProductByType/{p_type}")
	public ResponseEntity<?> getProductByType(@PathVariable String p_type) {
		System.out.println(p_type);
		return warehouseDAO.getProductByType(p_type);
	}

//	@RequestMapping(path = "/addProduct",method = RequestMethod.POST)
	@PostMapping(value = "/addProduct", consumes = "multipart/form-data")
	public ResponseEntity<?> addProducts(@RequestBody Warehouse product, @RequestParam MultipartFile file,HttpServletRequest request ) {
		try {
			String fileName = new Date().getTime() + "." + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1); 
			 String protocol = request.getScheme(); 
			 String host = request.getServerName(); 
			 int port = request.getServerPort(); 
			 String hostPath = protocol + "://" + host + ":" + port + "/uploads/"+fileName;
			product.setP_image(hostPath);
			File dest = new File(uploadDir + File.separator + fileName);
			file.transferTo(dest); // Set the image URL in the product object product.setImageUrl(dest.getAbsolutePath());
			Map<String, Object> map = new LinkedHashMap<>(); 
			map.put("files", dest);
			return  warehouseDAO.addProducts(product);
		}
		catch(IOException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/updateProduct/{p_id}")
	public ResponseEntity<?> updateProduct(@PathVariable Integer p_id, @RequestBody Warehouse product) {
		return warehouseDAO.updateProduct(p_id, product);
	}
 
	@DeleteMapping("/deleteProduct/{p_id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Integer p_id) {
		return warehouseDAO.deleteProduct(p_id);
	}

}
