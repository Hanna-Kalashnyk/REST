package com.rest.services;

import com.rest.Entity.People;
import com.rest.Repository.PeopleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeopleServiceImpl implements PeopleService {

    private final PeopleRepository peopleRepository;

    public PeopleServiceImpl(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public People findPeopleById(long id) {
        return peopleRepository.findById(id).get();
    }

    @Override
    public List<People> findAllPeople() {
        return peopleRepository.findAll();
    }

    @Override
    public People findPeopleByName(String name) {
        return peopleRepository.findPeopleByName(name);
    }

    @Override
    public People findPeopleByEmail(String email) {

        if (null != peopleRepository.findPeopleByEmail(email)) {
            return peopleRepository.findPeopleByEmail(email);
        } else {
            System.out.println("NoOne person has such email. Check out it.");
            return null;
        }
    }

    @Override
    public void cleanPeopleById(long id) {
        peopleRepository.delete(peopleRepository.findById(id).get());
    }

    @Override
    public void newName(long id, String newname) {
        People peopleH = peopleRepository.findById(id).get();
        peopleH.setName(newname);
        peopleRepository.save(peopleH);

    }

    @Override
    public void renew(long id, String name, String email) {
        People people = peopleRepository.findById(id).get();
        people.setName(name);
        people.setEmail(email);
        peopleRepository.save(people);
    }


    @Override
    public void newGuyPeople(long id, String guyname, String guyemail) {
        People guy = new People();
        guy.setId(id);
        guy.setName(guyname);
        guy.setEmail(guyemail);
        peopleRepository.save(guy);
    }

    @Override
    public void cleanAllPeople() {
        List<People> allPeople = peopleRepository.findAll();

        for (People people : allPeople) {
            long id;
            id = people.getId();
            peopleRepository.deleteById(id);
        }
    }
}
