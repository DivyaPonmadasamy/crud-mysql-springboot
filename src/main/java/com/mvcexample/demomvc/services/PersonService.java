package com.mvcexample.demomvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvcexample.demomvc.model.Degrees;
import com.mvcexample.demomvc.model.Person;
import com.mvcexample.demomvc.model.PersonDegrees;
import com.mvcexample.demomvc.model.PersonDegreesInsert;
import com.mvcexample.demomvc.repository.PersonRepo;

@Service
public class PersonService {
    @Autowired
    private PersonRepo personRepo;

    // fetch all persons
    public List<Person> getAllPersons() {
        return personRepo.findAll();
    }

    // fetch person by id
    public Optional<Person> getPersonById(int id) {
        return personRepo.findById(id);
    }

    // insert a record (new person)
    public Person savePerson(Person p) {
        return personRepo.save(p);
    }

    // fetch person by firstname
    public List<Person> getPersonsByFirstname(String name) {
        return personRepo.findByFirstname(name);
    }

    // fetch persons between ages 15 and 25 (custom query)
    public List<Person> getPersonsBetweenAges() {
        return personRepo.findBetweenAges();
    }

    // fetch persons in Page number given by the user
    public Page<Person> getAllPersonsByLimit(int pageNumber, int pageSize) {
        return personRepo.findAll(PageRequest.of(pageNumber, pageSize));
    }

    // fetch persons' firstname whose age is greater than the given age
    public List<String> getPersonsFirstNameByGreaterThanAge(int age) {
        return personRepo.findFirstNameByGreaterThanAge(age);
    }

    // fetch persons who stays in chennai
    public List<Person> getPersonsByAddressChennai() {
        return personRepo.findByAddressChennai();
    }

    // fetch join data from Person and Degrees
    public List<PersonDegrees> getAllPersonsWithDegrees() {
        return personRepo.findAllWithDegrees();
    }

    // insert Person and Degrees tables in one-go, when a new Person is inserted
    public PersonDegreesInsert savePersonAndDegrees(PersonDegreesInsert pd) {
        Person person = new Person();
        person.setFirstname(pd.getFirstname());
        person.setLastname(pd.getLastname());
        person.setAge(pd.getAge());
        person.setAddress(pd.getAddress());

        for (PersonDegreesInsert.DegreeList degreeList : pd.getDegrees()) {
            Degrees degree = new Degrees();
            degree.setDegreename(degreeList.getDegreename());
            degree.setYearofpassing(degreeList.getYearofpassing());
            degree.setCgpa(degreeList.getCgpa());

            person.addDegree(degree);   // saves both the tables - populates degrees List in Person entity
        }

        // for (String degreeName : pd.getDegrees()) {
        //     Degrees degree = new Degrees();
        //     degree.setDegreename(degreeName);

        //     person.addDegree(degree); // saves both the tables - populates degrees List in Person entity
        // }

        Person savedPerson = personRepo.save(person);

        pd.setPersonid(savedPerson.getPersonid()); // get personid inserted through auto-increment, after insertion, to return to controller

        return pd;
    }

    // fetch person by id with degree
    public Optional<Person> getPersonbyidWithDegrees(int id){
        return personRepo.findById(id);
    }

    // update person through personid (any column)
    @Transactional // not necessary, JIC since 2 transactions are involved (retrieval and insertion)
    public Person updatePerson(Person p) { 
        Optional<Person> personList = personRepo.findById(p.getPersonid());

        if(personList.isEmpty()) return null; // in an unfortunate event, when the record to be updated is not found in DB

        Person person = personList.get();

        if(p.getFirstname() != null) person.setFirstname(p.getFirstname());
        if(p.getLastname() != null) person.setLastname(p.getLastname());
        if(p.getAge() != null) person.setAge(p.getAge());
        if(p.getAddress() != null) person.setAddress(p.getAddress());

        return personRepo.save(person);
    }
}
