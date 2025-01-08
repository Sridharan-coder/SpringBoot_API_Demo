package com.e_commerce.Entry1.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cashfree.ApiException;
import com.cashfree.ApiResponse;
import com.cashfree.Cashfree;
import com.cashfree.model.CreateOrderRequest;
import com.cashfree.model.CustomerDetails;
import com.cashfree.model.OrderEntity;
import com.cashfree.model.PaymentEntity;

import okhttp3.OkHttpClient;

import org.json.JSONObject;

@CrossOrigin(origins = "http://localhost:3000", methods = { RequestMethod.OPTIONS, RequestMethod.GET,
		RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, })
@RestController
@RequestMapping("/")
public class GeneralController {

	@Value("${XClientId}")
	private String XClientId;
	@Value("${XClientSecret}")
	private String XClientSecret;

	@GetMapping("/payment/{amt}")
	public ResponseEntity<?> processPayment(@PathVariable Double amt) {
		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setCustomerId("webcodder01");
		customerDetails.setCustomerPhone("9999999999");
		customerDetails.setCustomerName("Web Codder");
		customerDetails.setCustomerEmail("webcodder@example.com");
		CreateOrderRequest request = new CreateOrderRequest();
		request.setOrderAmount(amt);
		request.setOrderCurrency("INR");
		request.setOrderId(generateOrderId());
		request.setCustomerDetails(customerDetails);
		OkHttpClient client = new OkHttpClient();
		try {
			Cashfree cashfree = new Cashfree();
			Cashfree.XClientId = XClientId.split("=")[1];
			Cashfree.XClientSecret = XClientSecret.split("=")[1];
			Cashfree.XEnvironment = Cashfree.SANDBOX;
			ApiResponse<OrderEntity> response = cashfree.PGCreateOrder("2023-08-01", request, null, null, client);
			System.out.println(response.getData().getOrderId());
			return new ResponseEntity<>(response.getData(), HttpStatus.OK);
		} catch (ApiException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/verify")
	public ResponseEntity<?> verifyPayment(@RequestBody String order) {
		JSONObject json = new JSONObject(order);
		System.out.println(order+"+++++++++++"+json.getString("orderId"));
		String orderId=json.getString("orderId");
		OkHttpClient client = new OkHttpClient();
		try {
			Cashfree cashfree = new Cashfree();
			Cashfree.XClientId = XClientId.split("=")[1];
			Cashfree.XClientSecret =  XClientSecret.split("=")[1];
			Cashfree.XEnvironment = Cashfree.SANDBOX;
			ApiResponse<List<PaymentEntity>> response = cashfree.PGOrderFetchPayments("2023-08-01", orderId, orderId, null, client);
			System.out.println("======================="+response);
			return new ResponseEntity<>(response.getData(), HttpStatus.OK);
		} catch (ApiException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	private String generateOrderId() {
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[16];
		random.nextBytes(bytes);
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(bytes);
			byte[] digest = md.digest();
			StringBuilder sb = new StringBuilder();
			for (byte b : digest) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString().substring(0, 12);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
