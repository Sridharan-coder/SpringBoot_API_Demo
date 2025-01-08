package com.e_commerce.Entry1.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.e_commerce.Entry1.dto.UserDetails;

@Repository
public interface UserRepository extends MongoRepository<UserDetails, Integer> {

	Optional<UserDetails> findById(Integer u_id);

	@Query("{ 'u_id' : ?0 }")
	Optional<UserDetails> findOne(Integer u_id);

	@Query("{ 'u_emailAddress' : ?0 }")
	Optional<UserDetails> findByU_EmailAddress(String u_emailAddress);

	@Query("{ 'u_id' : ?0 }")
	void deleteBySId(Integer u_id);
	
}
