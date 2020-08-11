package com.rest.withConfiguredPostgresContainerIntegrationTests;

import com.rest.Application;
import com.rest.Controller.PersonController;
import com.rest.Entity.Person;
import com.rest.Repository.PersonRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@ActiveProfiles("it")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestWithPostgresQLContainerIT {
    @Rule
    public PostgreSQLContainer myPostgreSQLContainer = new PostgreSQLContainer()
            .withUsername("nura1")
            .withPassword("nura")
            .withDatabaseName("db_nura1");

//    do not work BeforEach block - U should wright expected data in testmethod

    @Autowired
    private PersonController personController;

    @Autowired
    private PersonRepository personRepository;


    public void stupConnection() {
        try (Connection connection = DriverManager.getConnection(myPostgreSQLContainer.getJdbcUrl(), myPostgreSQLContainer.getUsername(), myPostgreSQLContainer.getPassword())) {
            System.out.println("=============================");
            System.out.println(connection.isValid(3000));
            System.out.println("=============================");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    @DisplayName("getAllPeopleIT")
    public void getAllPeopleIT() {
        stupConnection();
        List<Person> all = personController.getAllPeople();
        Assertions.assertThat(all.size()).isEqualTo(3);
        Assertions.assertThat(all.get(0).getFirstName()).isEqualTo("JoeTest");
        Assertions.assertThat(all.get(0).getEmail()).isEqualTo("Herring@gmail.com");
    }

    @Test
    @Transactional
    @DisplayName("getPeopleById Test")
    public void getPeopleByIdIT() {
        stupConnection();
        Person people1 = new Person(1L, "JoeTest", "Herring", "380675553311", "Herring@gmail.com", LocalDate.of(1991, 1, 20), true);
        Assert.assertEquals(people1, personController.getPeopleById(1L));
    }

    @Test
    @Transactional
    @DisplayName("getPeopleByName Test")
    public void getPeopleByNameIT() {
        stupConnection();
        Person people1 = new Person(1L, "JoeTest", "Herring", "380675553311", "Herring@gmail.com", LocalDate.of(1991, 1, 20), true);
        Assert.assertEquals(people1, personController.getPeopleByFirstName("JoeTest").get(0));
    }
}
