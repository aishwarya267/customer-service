package com.aishwarya.customerservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    public MongoTemplate mongoTemplate(MongoDatabaseFactory databaseFactory, MappingMongoConverter converter) {
        // remove __class field from mongo
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return super.mongoTemplate(databaseFactory, converter);
    }

    @Override
    protected String getDatabaseName() {
        return "customerdemo";
    }

}

