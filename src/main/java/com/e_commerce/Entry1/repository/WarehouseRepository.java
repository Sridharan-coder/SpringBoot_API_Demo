package com.e_commerce.Entry1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.e_commerce.Entry1.dto.Warehouse;

@Repository
public interface WarehouseRepository extends MongoRepository<Warehouse, Integer> {

	@Query("{ 'p_id' : ?0 }")
	Optional<Warehouse> findOne(Integer p_id);

	@Query("{ 'p_type' : { $regex: ?0, $options: 'i' } }")
	List<Warehouse> findByP_Type(String p_type);

	@Query("{ 'p_id' : ?0 }")
	void deleteBySId(Integer p_id);
	
}
