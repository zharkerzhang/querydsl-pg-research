package com.zharker.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.zharker.database.data.customized.CustomRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = CustomRepositoryFactoryBean.class)
public class QueryDslPGApp {

    @Value("${spring.flyway.drop-before-migrate:true}")
    private boolean dropBeforeMigrate;

    public static void main(String[] args) {
        SpringApplication.run(QueryDslPGApp.class, args);
    }

    @Bean
    public FlywayMigrationStrategy cleanMigrateStrategy() {
        FlywayMigrationStrategy strategy = flyway -> {
            if (dropBeforeMigrate) {
                flyway.clean();
            }
            flyway.migrate();
        };

        return strategy;
    }

    @Bean("objectMapper")
    public ObjectMapper myMapper() {
        return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }
}
