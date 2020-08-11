package com.rest.withConfiguredPostgresContainerIntegrationTests;
//https://rieckpil.de/howto-write-spring-boot-integration-tests-with-a-real-database/

import com.rest.Controller.PersonController;
import com.rest.Entity.Person;
import com.rest.Repository.PersonRepository;
import org.assertj.core.api.Assertions;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

@ActiveProfiles("it")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {selfInitializedIntegrationIT.Initializer.class})
public class selfInitializedIntegrationIT {

    @ClassRule
    public static JdbcDatabaseContainer postgres = new PostgreSQLContainer("postgres")
            .withDatabaseName("dn_nura1")
            .withUsername("nura1")
            .withPassword("nura").withInitScript("schema-it.sql");

    @Value("http://localhost:${local.server.port}")
    String baseUrl;

    @Autowired
    PersonController personController;
    @Autowired
    PersonRepository personRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void checking_DB_after_spring_entering_test() {
        List<Person> all = personController.getAllPeople();
        Assertions.assertThat(all.size()).isEqualTo(3);
        Assertions.assertThat(all.get(0).getFirstName()).isEqualTo("JoeTest");
        Assertions.assertThat(all.get(0).getEmail()).isEqualTo("Herring@gmail.com");
    }

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgres.getJdbcUrl(),
                    "spring.datasource.username=" + postgres.getUsername(),
                    "spring.datasource.password=" + postgres.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

}