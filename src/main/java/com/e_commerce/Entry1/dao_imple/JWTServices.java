package com.e_commerce.Entry1.dao_imple;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.e_commerce.Entry1.dto.Seller;
import com.mongodb.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTServices {
	
	private static final String SECRET_KEY = "HpUZXk2c359EwXSOgM8A6Etwthndqv63NttVRVG6s4k="; // Replace with your actual secret key

	public String generateToken(Seller seller) {

		Map<String, Object> claims = new HashMap<>();
		claims.put("id", seller.getS_id());
		claims.put("name", seller.getS_name());

		return Jwts.builder().claims().add(claims).subject(seller.getS_emailAddress())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000)).and().signWith(getKey()).compact();

//		return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlNyaWRoYXJhbjFAZ21haWwuY29tIiwiaWF0IjoxNTE2MjM5MDIyfQ.Fj8mIEMN_xZzI30XOI29ZFI7oH71to8rcgquo0qRvwU";
	}

	private SecretKey getKey() {
		System.err.println(JwtKeyService());
		byte[] key = Decoders.BASE64.decode(SECRET_KEY);
		System.out.println("Secret key -> " + Arrays.toString(key));
		return Keys.hmacShaKeyFor(key);
	}

	private String JwtKeyService() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk = keyGen.generateKey();
			return Base64.getEncoder().encodeToString(sk.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
	}

	public boolean validateToken(String token, String name) {
		final String userName = extractUsername(token);
		System.out.println(userName.equals(name) && !isTokenExpired(token));
		return userName.equals(name) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {

		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {

		return extractClaim(token, Claims::getExpiration);
	}

}
