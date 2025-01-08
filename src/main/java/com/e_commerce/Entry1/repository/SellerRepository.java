package com.e_commerce.Entry1.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.e_commerce.Entry1.dto.Seller;

@Repository
public interface SellerRepository extends MongoRepository<Seller, Integer> {

	@Query("{ 's_id' : ?0 }")
	Optional<Seller> findOne(Integer s_id);

	@Query("{ 's_emailAddress' : ?0 }")
	Seller findByS_EmailAddress(String s_emailAddress);

	@Query("{ 's_id' : ?0 }")
	void deleteBySId(Integer s_id);

}
