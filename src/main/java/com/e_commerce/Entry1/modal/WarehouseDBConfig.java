package com.e_commerce.Entry1.modal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import jakarta.annotation.PostConstruct;

import org.bson.Document;
import com.mongodb.client.model.IndexOptions;

import java.util.Arrays;

@Configuration
public class WarehouseDBConfig {

	@Autowired
	private MongoTemplate mongoTemplate;

	@PostConstruct
	public void init() {

		// Create unique index for p_id
		mongoTemplate.getDb().getCollection("warehouses").createIndex(new Document("p_id", 1),
				new IndexOptions().unique(true));

		// Apply schema validation
		mongoTemplate.getDb()
				.runCommand(
						new Document("collMod", "warehouses").append("validator",
								new Document("$jsonSchema", new Document()
										.append("bsonType", "object").append(
												"required",
												Arrays.asList("p_id", "p_name", "p_price", "p_image", "p_type",
														"p_stock", "s_ids", "__v"))
										.append("properties", new Document()
												.append("p_id",
														new Document().append("bsonType", "int").append("description",
																"must be an integer and is required"))
												.append("p_name",
														new Document().append("bsonType", "string").append(
																"description", "must be a string and is required"))
												.append("p_price",
														new Document().append("bsonType", "double").append(
																"description", "must be a double and is required"))
												.append("p_image",
														new Document().append("bsonType", "string").append(
																"description", "must be a string and is required"))
												.append("p_type",
														new Document().append("bsonType", "string").append(
																"description", "must be a string and is required"))
												.append("p_stock",
														new Document().append("bsonType", "int").append("description",
																"must be an integer and is required"))
												.append("s_ids",
														new Document().append("bsonType", "array").append("description",
																"must be an array and is required"))
												.append("__v", new Document().append("bsonType", "int").append(
														"description", "must be an integer and is required"))))));
	}
}
