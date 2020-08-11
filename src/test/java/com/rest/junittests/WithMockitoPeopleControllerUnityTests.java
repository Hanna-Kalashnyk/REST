package com.rest.junittests;

import com.rest.Controller.PersonController;
import com.rest.Entity.Person;
import com.rest.services.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
//It works well weth @RunWith(..) aslo without
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WithMockitoPeopleControllerUnityTests {

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
    public void setup() {
        person1 = new Person(1L, "Herring", "Herring", "380675553311", "Herring@gmail.com", LocalDate.of(1991,01,20), true);
        person2 = new Person(2L, "Malcolm", "Malcolm", "380675553311","Malcolm@gmail.com", LocalDate.of(1993,06,29), false);
        person3 = new Person(3L, "Jack", "Jackson", "380683337799", "Malcolm@gmail.com", LocalDate.of(1901,06,11), false);
        peopleList.add(person1);
        peopleList.add(person2);
        peopleList.add(person3);
        newPerson = new Person(4L, "Hagrid", "Hagrid@gmail.com");
    }

    @Test
    @DisplayName("getAllPeople Test")
    void withMockGetAllPeopleTest() {
        when(personService.findAllPeople()).thenReturn(peopleList);
        List<Person> actual = personController.getAllPeople();
        assertArrayEquals(new List[]{peopleList}, new List[]{actual}, "Controller's test through the service mock");
    }


    @Test
    @DisplayName("getPeopleById Test")
    final void getPeopleByIdTest() {
        when(personService.findPersonById(anyLong())).thenReturn(person1);
        assertEquals(person1, personController.getPeopleById(1L));

    }

    @Test
    @DisplayName("getPeopleByFirstName Test")
    void getPeopleByFirstNameTest() {
        List<Person> sameFirstNameList = new ArrayList<>();
   //     newPerson = new Person(4L, "Hagrid", "Hagrid@gmail.com");

        sameFirstNameList.add(newPerson);
        when(personService.findPersonByFirstName(anyString())).thenReturn(sameFirstNameList);
        assertArrayEquals(new List[]{sameFirstNameList}, new List[]{personController.getPeopleByFirstName("Hagrid")});
    }

    @Test
    @DisplayName("getPeopleByIsHuman Test")
    void getPeopleByIsNotHumanTest() {
        List<Person> sameIsNotHumanList = new ArrayList<>();
        sameIsNotHumanList.add(person2);
        sameIsNotHumanList.add(person3);


        when(personService.findPersonIsNotHuman()).thenReturn(sameIsNotHumanList);
      //  Person actual = sameIsHumanList.get(0);
        assertArrayEquals(new List[]{sameIsNotHumanList}, new List[]{personController.getPeopleIsNotHuman()});
    }

    @Test
    @DisplayName("getPeopleByIsHuman Test")
    void getPeopleByIsHumanTest() {
        List<Person> sameIsHumanList = new ArrayList<>();
        sameIsHumanList.add(person1);


        when(personService.findPersonIsHuman()).thenReturn(sameIsHumanList);
        //  Person actual = sameIsHumanList.get(0);
        assertArrayEquals(new List[]{sameIsHumanList}, new List[]{personController.getPeopleIsHuman()});
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
        ArrayList actual = new ArrayList(personController.getAllPeople());
        assertEquals(peopleList, actual , "List check uot after people addition");

        when(personService.findPersonByEmail(anyString())).thenReturn(newPerson);
        assertEquals(newPerson, personController.getPeopleByEmail("Hagrid@gmail.com"));
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
