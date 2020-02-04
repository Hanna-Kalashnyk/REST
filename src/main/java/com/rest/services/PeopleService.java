package com.rest.services;

import com.rest.Entity.People;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface PeopleService {
    People findPeopleById(long id);

    List<People> findAllPeople();

    People findPeopleByName(String name);

    void cleanPeopleById(long id);

    void newGuyPeople(String guyname, String guyemail);

    void cleanAllPeople();

    void newName(long id, String newname);

    void renew(long id, String name, String email);

    People findPeopleByEmail(String email);
}

