package com.rest.Repository;

import com.rest.Entity.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<People, Long> {

    People findPeopleByName(String name);

    People findPeopleByEmail(String email);
}