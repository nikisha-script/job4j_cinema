package ru.job4j.cinema.config;

import liquibase.integration.spring.SpringLiquibase;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:db.properties")
public class DbConfig {

    @Bean
    public BasicDataSource loadPool(
            @Value("${jdbc.url}") String url,
            @Value("${jdbc.driver}") String driver,
            @Value("${jdbc.username}") String username,
            @Value("${jdbc.password}") String password) {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(driver);
        basicDataSource.setUrl(url);
        basicDataSource.setPassword(password);
        basicDataSource.setUsername(username);

        basicDataSource.setMinIdle(10);
        basicDataSource.setMaxIdle(5);
        basicDataSource.setMaxOpenPreparedStatements(100);

        return basicDataSource;
    }

    @Bean
    public SpringLiquibase springLiquibase(BasicDataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:db/changelog/liquibase-changeLog.xml");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }

}
