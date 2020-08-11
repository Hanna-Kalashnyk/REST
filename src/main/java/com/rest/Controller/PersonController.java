package com.rest.Controller;

import com.rest.Entity.Person;
import com.rest.services.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PersonController {


    private final PersonService personService;

    public PersonController(PersonService personService) {

        this.personService = personService;
    }

    @GetMapping("/all")
    public List<Person> getAllPeople() {

        return personService.findAllPeople();
    }

    @GetMapping("/id/{id}")
    public Person getPeopleById(@PathVariable Long id) {
        return personService.findPersonById(id);

    }

    @GetMapping("/firstname/{firstName}")
    public List<Person> getPeopleByFirstName(@PathVariable String firstName) {
        return personService.findPersonByFirstName(firstName);
    }

    @GetMapping("/secondname/{secondName}")
    public List<Person> getPeopleBySecondName(@PathVariable String secondName) {
        return personService.findPersonBySecondName(secondName);
    }

    @GetMapping("/ishuman")
    public List<Person> getPeopleIsHuman() {
        return personService.findPersonIsHuman();
    }

    @GetMapping("/isnothuman")
    public List<Person> getPeopleIsNotHuman() {
        return personService.findPersonIsNotHuman();
    }

    @GetMapping("/email/{email}")
    public Person getPeopleByEmail(@PathVariable String email) {
        return personService.findPersonByEmail(email);
    }

    @PostMapping("/new/{guyFirstName}/{guySecondName}")
    public void newPeople(@PathVariable String guyFirstName, @PathVariable String guySecondName) {
        personService.newGuyPerson(guyFirstName, guySecondName);
    }

    @PostMapping("/newperson")
    public void newPerson(Person newPerson) {
        personService.newPerson(newPerson);
    }

    @PatchMapping("/newfirstname/{id}/{newFirstName}")
    public void changePeopleName(@PathVariable Long id, @PathVariable String newFirstName) {
        personService.newFirstName(id, newFirstName);
    }

    @PutMapping("/renew/{id}/{firstName}/{secondName}")
    public void renewPeople(@PathVariable Long id, @PathVariable String firstName, @PathVariable String secondName) {
        personService.renew(id, firstName, secondName);
    }

    @DeleteMapping("/cleanid/{id}")
    public void cleanPeopleById(@PathVariable Long id) {
        personService.cleanPersonById(id);
    }

    @DeleteMapping("/clean/")
    public void cleanPeople(Person person) {
        personService.cleanPerson(person);
    }

    @DeleteMapping("/cleanall")
    public void cleanAllPeople() {
        personService.cleanAllPeople();

    }
}
