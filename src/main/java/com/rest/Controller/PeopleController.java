package com.rest.Controller;

import com.rest.Entity.People;
import com.rest.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PeopleService peopleService;

    public PeopleController(PeopleService peopleService) {

        this.peopleService = peopleService;
    }

    public PeopleController() {

    }

    @GetMapping("/all")
    public List<People> getAllPeople() {

        return peopleService.findAllPeople();
    }

    @GetMapping("/id/{id}")
    public People getPeopleById(@PathVariable long id) {
        return peopleService.findPeopleById(id);

    }

    @GetMapping("/name/{name}")
    public People getPeopleByName(@PathVariable String name) {
        return peopleService.findPeopleByName(name);
    }

    @GetMapping("/email/{email}")
    public People getPeopleByEmail(@PathVariable String email) {
        return peopleService.findPeopleByEmail(email);
    }

    @PostMapping("/new/{guyname}/{guyemail}")
    public void newPeople(@PathVariable String guyname, String guyemail) {
        peopleService.newGuyPeople(guyname, guyemail);
    }

    @PatchMapping("/newname/{id}/{newname}")
    public void patchPeopleName(@PathVariable long id, @PathVariable String newname) {
        peopleService.newName(id, newname);
    }

    @PutMapping("/renew/{id}/{name}/{email}")
    public void renewPeople(@PathVariable long id, @PathVariable String name, @PathVariable String email) {
        peopleService.renew(id, name, email);
    }

    @DeleteMapping("/cleanid/{id}")
    public void cleanPeopleById(@PathVariable long id) {
        peopleService.cleanPeopleById(id);

    }

    @DeleteMapping("/cleanall")
    public void cleanAllPeople() {
        peopleService.cleanAllPeople();

    }
}
