package com.rest.spareTypesOfIntegrationTests;

import com.rest.Application;
import com.rest.Controller.PersonController;
import com.rest.Repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import static org.assertj.core.api.BDDAssertions.then;

@ActiveProfiles("it")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class thenTests {
    @Autowired
    DataSource datasource;

    @Autowired
    private PersonController personController;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void FindPeopleByNameAndCheckItsPropertyTest() {
        then(personRepository.findByFirstNameStartsWithIgnoreCase("HarryTest").get(0)).isEqualTo(personController.getPeopleByFirstName("HarryTest").get(0));
    }

    @Test
    public void CheckEntityCountIT() {
        then(personRepository.count()).isEqualTo(3L);
    }

}
