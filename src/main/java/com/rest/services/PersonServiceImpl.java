package com.rest.services;

import com.rest.Entity.Person;
import com.rest.Repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person findPersonById(Long id) {
        Person person;
        person = personRepository.findById(id).get();
        return person;
    }

    @Override
    public List<Person> findAllPeople() {
        return personRepository.findAll();
    }

    @Override
    public List<Person> findPersonByFirstName(String firstName) {
        List<Person> peopleList = personRepository.findAll();
        List<Person> peopleWithTheSameFirstName = new ArrayList();
        for (Person person : peopleList) {
            if (person.getFirstName().equals(firstName))
                peopleWithTheSameFirstName.add(person);
        }
        return peopleWithTheSameFirstName;
        //or easier way:
        // return personRepository.findBySecondNameStartsWithIgnoreCase(nill);
    }

    @Override
    public List<Person> findPersonBySecondName(String secondName) {
        List<Person> peopleList = personRepository.findAll();
        List<Person> peopleWithTheSameSecondName = new ArrayList();
        for (Person person : peopleList) {
            if (person.getSecondName().equals(secondName))
                peopleWithTheSameSecondName.add(person);
        }
        return peopleWithTheSameSecondName;
        //or easier way:
        // return personRepository.findBySecondNameStartsWithIgnoreCase(null);

        //   or another way:
//       return  List<Person> peopleWithTheSameSecondName =  personRepository.findAll().stream()
//                .filter(x -> x.getSecondName().equals("secondName"))
//                .collect(Collectors.toList());
    }

    @Override
    public List<Person> findPersonIsHuman() {
        List<Person> peopleList = personRepository.findAll();
        List<Person> peopleIsHuman = new ArrayList();
        for (Person person : peopleList) {
            if (person.isHuman())
                peopleIsHuman.add(person);
        }
        return peopleIsHuman;
    }

    @Override
    public List<Person> findPersonIsNotHuman() {
        List<Person> peopleList = personRepository.findAll();
        List<Person> peopleIsNotHuman = new ArrayList();
        for (Person person : peopleList) {
            if (!person.isHuman())
                peopleIsNotHuman.add(person);
        }
        return peopleIsNotHuman;
    }

    @Override
    public Person findPersonByEmail(String email) {

        if (null != personRepository.findPersonByEmail(email)) {
            return personRepository.findPersonByEmail(email);
        } else {
            System.out.println("NoOne person has such email. Check out it.");
            return null;
        }
    }

    @Override
    public void cleanPersonById(Long id) {
        personRepository.delete(personRepository.findById(id).get());
    }

    @Override
    public void cleanPerson(Person person) {
        personRepository.delete(person);
    }

    @Override
    public void newFirstName(Long id, String newname) {
        Person personH = personRepository.findById(id).get();
        personH.setFirstName(newname);
        personRepository.save(personH);

    }

    @Override
    public void renew(Long id, String name, String email) {
        Person person = personRepository.findById(id).get();
        person.setFirstName(name);
        person.setEmail(email);
        personRepository.save(person);
    }


    @Override
    public void newGuyPerson(String guyFirstName, String guySecondName) {
        Person guy = new Person(0L, guyFirstName, guySecondName, null, null, null, true);
        personRepository.save(guy);
    }
    
    @Override
    public void newPerson(Person person){personRepository.save(person);
    }

    @Override
    public void cleanAllPeople() {
        List<Person> allPeople = personRepository.findAll();

        for (Person people : allPeople) {
            Long id;
            id = people.getId();
            personRepository.deleteById(id);
        }
    }
}
