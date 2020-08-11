package com.rest.withConfiguredPostgresContainerIntegrationTests;

import com.rest.Application;
import com.rest.Controller.PersonController;
import com.rest.Entity.Person;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@ActiveProfiles("it")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class integrationTestsIT {

    @Autowired
    private PersonController personController;

    List<Person> peopleList = new ArrayList<>();
    Person people1;
    Person people2;
    Person people3;
    Person newPeople;

    @BeforeEach
    public void setup() {
        people1 = new Person(1L, "JoeTest", "Herring", "380675553311", "Herring@gmail.com", LocalDate.of(1991, 1, 20), true);
        people2 = new Person(2L, "HarryTest", "Malcolm", "380895544331", "Malcolm@gmail.com", LocalDate.of(1993, 6, 29), false);
        people3 = new Person(3L, "JackTest", "Jackson", "380683337799", "Jackson@gmail.com", LocalDate.of(1901, 6, 11), false);

        peopleList.add(people1);
        peopleList.add(people2);
        peopleList.add(people3);
        newPeople = new Person(4L, "Hagrid", "Hagrid@gmail.com");
    }

    @Test
    public void contextLoadsTest() {
        assertNotNull(personController);
    }

    @Test
    @Transactional
    @DisplayName("getAllPeople Test")
    void withMockGetAllPeopleIT() {
        List<Person> actual = personController.getAllPeople();
        Assert.assertEquals(peopleList, actual);
    }

    @Test
    @Transactional
    @DisplayName("getPeopleById Test")
    void getPeopleByIdIT() {
        Assert.assertEquals(people1, personController.getPeopleById(1L));
    }

    @Test
    @Transactional
    @DisplayName("getPeopleByName Test")
    void getPeopleByNameIT() {
        Assert.assertEquals(people1, personController.getPeopleByFirstName("JoeTest").get(0));
    }

    @Test
    @Transactional
    @DisplayName("getPeopleByEmail Test")
    void getPeopleByEmailIT() {
        Assert.assertEquals(people1, personController.getPeopleByEmail("Herring@gmail.com"));
    }

    @Test
    @Transactional
    @DisplayName("add new People Test")
    void newPeopleIT() {
        personController.newPeople("Hagrid", "Giant");
        Assert.assertNotNull(personController.getPeopleBySecondName("Giant"));
        Assert.assertEquals("Hagrid", (personController.getPeopleBySecondName("Giant")).get(0).getFirstName());
    }

    @Test
    @Transactional
    @DisplayName("patch newPeople's name to Giant Test")
    void changePeopleIT() {
        people1.setFirstName("Giant");
        personController.changePeopleName(1L, "Giant");

        String actualName = personController.getPeopleById(1L).getFirstName();
        Assert.assertEquals("Giant", actualName);
    }

    @Test
    @Transactional
    @DisplayName("PUT new first name&Email for people id=3 Test")
    void renewPeopleIT() {
        people3.setFirstName("Giant");
        people3.setEmail("Giant@gmail.com");
        personController.renewPeople(3L, "Giant", "Giant@gmail.com");

        String actualEmail = personController.getPeopleById(3L).getEmail();
        Assert.assertEquals("Giant@gmail.com", actualEmail);


        String actualName = personController.getPeopleById(3L).getFirstName();
        Assert.assertEquals("Giant", actualName);
    }

    @Test
    @Transactional
    @DisplayName("Delete People by id Test")
    void cleanPeopleByIdIT() {
        peopleList.remove(people3);
        personController.cleanPeopleById(3L);
        List<Person> actual = personController.getAllPeople();
        Assert.assertEquals(peopleList, actual);
    }

    @Test
    @Transactional
    @DisplayName("Delete All People Test")
    void cleanAllPeopleITt() {
        peopleList.remove(people1);
        peopleList.remove(people2);
        peopleList.remove(people3);
        personController.cleanAllPeople();
        Assert.assertEquals(personController.getAllPeople(), peopleList);
    }
}
