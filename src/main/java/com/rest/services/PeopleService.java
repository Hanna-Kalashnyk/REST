package com.rest.services;

import com.rest.Entity.People;

import java.util.List;

public interface PeopleService {
    People findPeopleById(long id);

    List<People> findAllPeople();

    People findPeopleByName(String name);

    void cleanPeopleById(long id);

    void newGuyPeople(long id, String guyname, String guyemail);

    void cleanAllPeople();

    void newName(long id, String newname);

    void renew(long id, String name, String email);

    People findPeopleByEmail(String email);
}

