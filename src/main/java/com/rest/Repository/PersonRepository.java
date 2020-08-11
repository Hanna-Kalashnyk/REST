package com.rest.Repository;

import com.rest.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findPersonByEmail(String email);
    Person findByEmailIgnoreCase(String email);


    // List<Person> findByLastNameStartsWithIgnoreCase(String lastName);

    List<Person> findByFirstNameStartsWithIgnoreCase(String filterText);

    List<Person> findBySecondNameStartsWithIgnoreCase(String filterText);

    void delete(Person person);

    List<Person> findAll();


    // Optional<Person> findById(Long id);
}