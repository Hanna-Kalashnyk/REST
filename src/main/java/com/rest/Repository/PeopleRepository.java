package com.rest.Repository;

import com.rest.Entity.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public  interface PeopleRepository extends JpaRepository<People, Long> {

    People findPeopleByName(String name);
    People findPeopleByEmail(String email);
}