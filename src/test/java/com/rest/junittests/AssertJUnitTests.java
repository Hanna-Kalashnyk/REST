package com.rest.junittests;

import com.rest.Controller.PersonController;
import com.rest.Entity.Person;
import com.rest.services.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
//@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AssertJUnitTests {

    @InjectMocks
    PersonController personController;
    @Mock
    PersonServiceImpl personService;

    List<Person> peopleList = new ArrayList<>();
    Person person1;
    Person person2;
    Person person3;
    Person newPerson;

    @BeforeEach
    void setup() {
        person1 = new Person(1L, "Herring", "Herring", "380675553311", "Herring@gmail.com", LocalDate.of(1991, 1, 20), true);
        person2 = new Person(2L, "Malcolm", "Malcolm", "380675553311", "Malcolm@gmail.com", LocalDate.of(1993, 6, 29), false);
        person3 = new Person(3L, "Jack", "Jackson", "380683337799", "Malcolm@gmail.com", LocalDate.of(1901, 6, 11), false);
        peopleList.add(person1);
        peopleList.add(person2);
        peopleList.add(person3);
        newPerson = new Person(4L, "Hagrid", "Hagrid@gmail.com");
    }

    @Test
    @DisplayName("getAllPeople Test")
    public void withMockGetAllPeopleTest() {
        when(personService.findAllPeople()).thenReturn(peopleList);
        List<Person> actual = personController.getAllPeople();
        assertThat(actual).isEqualTo(peopleList)
                .containsOnlyOnce(person2)
                .containsSequence(person1, person2, person3);
    }


    @Test
    @DisplayName("getPeopleById Test")
    public void getPeopleByIdTest() {
        Person actual = person1;
        when(personService.findPersonById(anyLong())).thenReturn(person1);
        assertThat(actual).isEqualTo(person1);
//        assertEquals(person1, personController.getPeopleById(1L));

    }

    @Test
    @DisplayName("getPeopleByFirstName Test")
    void getPeopleByFirstNameTest() {
        Person person4 = new Person(4L, "Malcolm", "Second", "380675557890", "Malcolm@gmail.com", LocalDate.of(1993, 6, 29), false);
        peopleList.add(person4);

        List<Person> sameFirstNameList = new ArrayList<>();
        sameFirstNameList.add(person2);
        sameFirstNameList.add(person4);

        when(personService.findPersonByFirstName(anyString())).thenReturn(sameFirstNameList);
        assertThat(personService.findPersonByFirstName("Malcolm")).isEqualTo(sameFirstNameList)
                .isNotEmpty()
                .containsOnlyOnce(person4, person2)
                .containsSequence(person2, person4);
//        assertArrayEquals(new List[]{sameFirstNameList}, new List[]{personController.getPeopleByFirstName("Hagrid")});
    }

    @Test
    @DisplayName("getPeopleByIsHuman Test")
    void getPeopleByIsNotHumanTest() {
        List<Person> notHumanList = new ArrayList<>();
        notHumanList.add(person2);
        notHumanList.add(person3);

        when(personService.findPersonIsNotHuman()).thenReturn(notHumanList);
//        assertArrayEquals(new List[]{notHumanList}, new List[]{personController.getPeopleIsNotHuman()});
        assertThat(personService.findPersonIsNotHuman()).isEqualTo(notHumanList)
                .isNotEmpty()
                .containsOnlyOnce(person2)
                .containsSequence(person2, person3);
    }

    @Test
    @DisplayName("getPeopleByIsHuman Test")
    void getPeopleByIsHumanTest() {
        List<Person> isHumanList = new ArrayList<>();
        isHumanList.add(person1);

        when(personService.findPersonIsHuman()).thenReturn(isHumanList);
        assertThat(personService.findPersonIsHuman()).isEqualTo(isHumanList)
                .isNotEmpty()
                .containsSequence(person1)
                .containsOnlyOnce(person1);
    }


    @Test
    @DisplayName("getPeopleByEmail Test")
    void getPeopleByEmailTest() {
        when(personService.findPersonByEmail(anyString())).thenReturn(person1);
        assertEquals(person1, personController.getPeopleByEmail("Herring@gmail.com"));
    }

    @Test
    @DisplayName("add new People Test")
    void newPeopleTest() {

        peopleList.add(newPerson);
        when(personService.findAllPeople()).thenReturn(peopleList);
        List<Person> actual = personController.getAllPeople();
        assertThat(actual).isEqualTo(personService.findAllPeople())
                .isNotEmpty()
                .containsOnlyOnce(newPerson);

        when(personService.findPersonByEmail(anyString())).thenReturn(newPerson);
        Person actualNewOne = personController.getPeopleByEmail("Hagrid@gmail.com");
        assertThat(actualNewOne).isEqualTo(newPerson);
    }

    @Test
    @DisplayName("patch newPeople's name to Giant Test")
    void changePeopleNameTest() {
        newPerson.setFirstName("Giant");

        when(personService.findPersonByEmail(anyString())).thenReturn(newPerson);
        when(personService.findPersonById(anyLong())).thenReturn(newPerson);

        assertEquals(newPerson, personController.getPeopleByEmail("Herring@gmail.com"));
        assertEquals(newPerson, personController.getPeopleById(4L));

        String actualName = personController.getPeopleById(4L).getFirstName();
        assertEquals("Giant", actualName);

    }

    @Test
    @DisplayName("PUT people id=4 Test")
    void renewPeopleTest() {
        newPerson.setFirstName("Giant");
        newPerson.setEmail("Giant@gmail.com");

        when(personService.findPersonById(anyLong())).thenReturn(newPerson);
        assertEquals(newPerson, personController.getPeopleById(4L));

        String actualEmail = personController.getPeopleById(4L).getEmail();
        assertEquals("Giant@gmail.com", actualEmail);


        String actualName = personController.getPeopleById(4L).getFirstName();
        assertEquals("Giant", actualName);
    }

    @Test
    @DisplayName("Delete People by id Test")
    void cleanPeopleByIdTest() {
        peopleList.remove(person3);

        when(personService.findAllPeople()).thenReturn(peopleList);
        List<Person> actual = personController.getAllPeople();
        assertArrayEquals(new List[]{peopleList}, new List[]{actual}, "List check out after people(id=3) deletion");

        when(personService.findPersonById(anyLong())).thenReturn(null);
        assertNull(personController.getPeopleById(3L));


        when(personService.findPersonByFirstName(anyString())).thenReturn(null);
        assertNull(personController.getPeopleByFirstName("Jack"));
    }

    @Test
    @DisplayName("Delete All People Test")
    void cleanAllPeopleTest() {
        peopleList.remove(person1);
        peopleList.remove(person2);
        peopleList.remove(person3);


        when(personService.findAllPeople()).thenReturn(peopleList);
        List<Person> actual = personController.getAllPeople();
        assertArrayEquals(new List[]{peopleList}, new List[]{actual});

        when(personService.findPersonById(anyLong())).thenReturn(null);
        assertNull(personController.getPeopleById(3L));
        assertNull(personController.getPeopleById(2L));
        assertNull(personController.getPeopleById(1L));


        when(personService.findPersonByFirstName(anyString())).thenReturn(null);
        assertNull(personController.getPeopleByFirstName("Jack"));

        when(personService.findPersonByEmail(anyString())).thenReturn(null);
        assertNull(personController.getPeopleByFirstName("Malcolm@gmail.com"));

    }
}
