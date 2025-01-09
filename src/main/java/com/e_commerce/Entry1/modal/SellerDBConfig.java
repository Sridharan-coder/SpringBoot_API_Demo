package com.e_commerce.Entry1.modal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

import jakarta.annotation.PostConstruct;

import org.bson.Document;
import com.mongodb.client.model.IndexOptions;

import java.util.Arrays;

@Configuration
public class SellerDBConfig {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private MappingMongoConverter mappingMongoConverter;

	@PostConstruct
	public void setUpMongoEscapeCharacterConversion() {
		mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
	}

	@PostConstruct
	public void init() {
		// Create unique index for s_phoneNumber
		mongoTemplate.getDb().getCollection("sellers").createIndex(new Document("s_phoneNumber", 1),
				new IndexOptions().unique(true));

		// Create unique index for s_id
		mongoTemplate.getDb().getCollection("sellers").createIndex(new Document("s_id", 1),
				new IndexOptions().unique(true));

		// Create unique index for s_emailAddress
		mongoTemplate.getDb().getCollection("sellers").createIndex(new Document("s_emailAddress", 1),
				new IndexOptions().unique(true));

		// Apply schema validation
		mongoTemplate.getDb().runCommand(new Document("collMod", "sellers").append("validator",
				new Document("$jsonSchema", new Document().append("bsonType", "object")
						.append("required",
								Arrays.asList("s_id", "s_name", "s_phoneNumber", "s_emailAddress", "s_password"))
						.append("properties",
								new Document()
										.append("s_id",
												new Document().append("bsonType", "int").append("description",
														"must be an integer and is required"))
										.append("s_name",
												new Document().append("bsonType", "string").append("description",
														"must be a string and is required"))
										.append("s_phoneNumber", new Document().append("bsonType", "long")
												.append("description", "must be a long and is required")
												.append("pattern", "^[6-9]\\d{9}$"))
										.append("s_emailAddress",
												new Document().append("bsonType", "string")
														.append("description",
																"must be a string and is required"))
										.append("s_password", new Document().append("bsonType", "string")
												.append("description", "must be a string and is required")
												.append("pattern",
														"^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"))
										.append("__v", new Document().append("bsonType", "int")
												.append("description", "must be a int and is required"))))));
	}
}
