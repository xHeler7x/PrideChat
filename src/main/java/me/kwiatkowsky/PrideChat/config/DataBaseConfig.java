package me.kwiatkowsky.PrideChat.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("me.kwiatkowsky.PrideChat.repository")
public class DataBaseConfig extends AbstractMongoConfiguration{

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private Integer port;

    @Value("${spring.data.mongodb.database}")
    private String database;


    @Override
    public MongoClient mongoClient() {
        return new MongoClient(host + ":" + port);
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }
}
