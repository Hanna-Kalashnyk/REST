package com.rest.Entity;
import javax.persistence.*;
import java.util.Objects;

@Entity(name = "People")
public class People {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String email;


    public People() {
    }

    public People(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        People people = (People) o;
        return id == people.id &&
                name.equals(people.name) &&
                Objects.equals(email, people.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }
}
