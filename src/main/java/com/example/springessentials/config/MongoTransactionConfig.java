package com.example.springessentials.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import java.util.Arrays;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
//@EnableMongoRepositories("com.example.springessentials.model")
public class MongoTransactionConfig extends AbstractMongoClientConfiguration {


    @Value("${spring.data.mongodb.uri}")
    private String uri;

    @Bean
    MongoTransactionManager transactionManager(MongoDbFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

    public MongoClient mongoClient() {
        return MongoClients.create(uri);
    }

    @Override
    protected String getDatabaseName() {
        return "essentials";
    }

    /*
    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private String port;

    @Value("${spring.data.mongodb.username}")
    private String username;

    @Value("${spring.data.mongodb.password}")
    private String password;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Override
    public MongoClient mongoClient() {
        MongoCredential credential = MongoCredential.createCredential(username, database, password.toCharArray());
        ServerAddress address = new ServerAddress(host, Integer.valueOf(port));
        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
                .applyToSslSettings(builder -> builder.enabled(true))
                .applyToClusterSettings(builder ->
                        builder.hosts(Arrays.asList(
                                address
                        )))
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        return mongoClient;
    }
     */
}