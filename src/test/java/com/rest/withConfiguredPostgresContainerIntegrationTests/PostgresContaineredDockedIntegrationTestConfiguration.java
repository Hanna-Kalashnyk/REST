package com.rest.withConfiguredPostgresContainerIntegrationTests;

import com.rest.Repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

@PropertySource(value = {"classpath:application-it.properties"})
@ActiveProfiles("it")
@TestConfiguration

public class PostgresContaineredDockedIntegrationTestConfiguration {

    @Autowired
    PersonRepository peopleRepository;

    private static final String DB_NAME = "db_nura1";
    private static final String USERNAME = "nura1";
    private static final String PASSWORD = "nura";
    private static final String PORT = "5432";
    private static final String INIT_SCRIPT_PATH = "schema-it.sql";


    @Bean(initMethod = "start")
    JdbcDatabaseContainer databaseContainer() {
        return new PostgreSQLContainer()
                .withInitScript(INIT_SCRIPT_PATH)
                .withUsername(USERNAME)
                .withPassword(PASSWORD)
                .withDatabaseName(DB_NAME);
    }

    @Bean
    @Primary
    DataSource dataSource(JdbcDatabaseContainer container) {

        System.out.println("Connecting to test container " + container.getUsername() + ":" + container.getPassword() + "@" + container.getJdbcUrl());

        int mappedPort = container.getMappedPort(Integer.parseInt(PORT));
        String mappedHost = container.getContainerIpAddress();

        final DataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:postgresql://" + mappedHost + ":" + mappedPort + "/" + container.getDatabaseName())
                .username(container.getUsername())
                .password(container.getPassword())
                .driverClassName(container.getDriverClassName())
                .build();

        return dataSource;
    }
}
