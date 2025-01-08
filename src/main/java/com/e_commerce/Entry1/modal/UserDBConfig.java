package com.e_commerce.Entry1.modal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import jakarta.annotation.PostConstruct;

import org.bson.Document;
import com.mongodb.client.model.IndexOptions;

import java.util.Arrays;

@Configuration
public class UserDBConfig {

	@Autowired
	private MongoTemplate mongoTemplate;

	@PostConstruct
	public void init() {
		// Create unique index for s_phoneNumber
		mongoTemplate.getDb().getCollection("userdetails").createIndex(new Document("u_phoneNumber", 1),
				new IndexOptions().unique(true));

		// Create unique index for s_id
		mongoTemplate.getDb().getCollection("userdetails").createIndex(new Document("u_id", 1),
				new IndexOptions().unique(true));

		// Create unique index for s_emailAddress
		mongoTemplate.getDb().getCollection("userdetails").createIndex(new Document("u_emailAddress", 1),
				new IndexOptions().unique(true));

		// Apply schema validation
		mongoTemplate.getDb()
				.runCommand(new Document("collMod", "userdetails").append("validator",
						new Document("$jsonSchema", new Document()
								.append("bsonType", "object")
								.append("required",
										Arrays.asList(
												"u_id", "u_name", "u_phoneNumber", "u_emailAddress", "u_password",
												"u_carts", "u_whitelist"))
								.append("properties",
										new Document()
												.append("u_id",
														new Document().append("bsonType", "int").append("description",
																"must be an integer and is required"))
												.append("u_name", new Document()
														.append("bsonType", "string")
														.append("description", "must be a string and is required"))
												.append("u_phoneNumber",
														new Document().append("bsonType", "long")
																.append("description", "must be a long and is required")
																.append("pattern", "^[6-9]\\d{9}$"))
												.append("u_emailAddress", new Document().append("bsonType", "string")
														.append("description",
																"must be a string and is required")
														.append("pattern",
																"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"))
												.append("u_password", new Document().append("bsonType", "string")
														.append("description", "must be a string and is required")
														.append("pattern",
																"^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"))
												.append("u_carts",
														new Document().append("bsonType", "array").append("description",
																"must be an array"))
												.append("u_whitelist", new Document().append("bsonType", "array")
														.append("description", "must be an array"))))));

	}
}
