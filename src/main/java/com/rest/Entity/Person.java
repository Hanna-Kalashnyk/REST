package com.rest.Entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;


@Entity(name = "Person")
@Getter
@Setter
@EqualsAndHashCode
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String secondName;
    private String numberPhone;
    private String email;
    private LocalDate birthDay;

    private boolean isHuman;

    public Person() {
    }

    public Person(String firstName, String secondName) {
        new Person(0L, firstName, secondName, null, null, null, true);
    }

    public Person(Long id, String firstName, String email) {
        new Person(id, firstName, null, null, email, null, true);
    }

    public Person(Long id, String firstName, String secondName, String numberPhone, String email, LocalDate birthDay, boolean isHuman) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.numberPhone = numberPhone;
        this.email = email;
        this.birthDay = birthDay;
        this.isHuman = isHuman;
    }

}
