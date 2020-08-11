package com.rest.services;

import com.rest.Entity.Person;

import java.util.List;

public interface PersonService {
    Person findPersonById(Long id);

    List<Person> findAllPeople();

    List<Person> findPersonByFirstName(String firstName);

    List<Person> findPersonBySecondName(String secondName);

    List<Person> findPersonIsHuman();
    List<Person> findPersonIsNotHuman();

    void cleanPersonById(Long id);

    void cleanPerson(Person person);

    void newGuyPerson(String guyFirstName, String guySecondName);
    void newPerson(Person person);

    void cleanAllPeople();

    void newFirstName(Long id, String newFirstName);

    void renew(Long id, String name, String email);

    Person findPersonByEmail(String email);
}

