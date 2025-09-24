package com.mvcexample.demomvc.controller;

import org.springframework.web.bind.annotation.RestController;

import com.mvcexample.demomvc.model.Person;
import com.mvcexample.demomvc.model.PersonDegrees;
import com.mvcexample.demomvc.model.PersonDegreesInsert;
import com.mvcexample.demomvc.services.PersonService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("/greet")
    public ResponseEntity<String> myResponse() {
        return ResponseEntity.status(200).body("Your request is served");   //Success, status code - 200
    }

    @GetMapping("/greet/{name}") // to send data in path variable
    public ResponseEntity<String> myResponseArg(@PathVariable String name) {
        StringBuilder strbuild = new StringBuilder();
        strbuild.append(name.substring(0,1).toUpperCase());
        strbuild.append(name.substring(1, name.length()));
        return ResponseEntity.status(200).body("Hi " + strbuild + "! Welcome back...");
    }

    @PostMapping("/signup") // to get data from the user and respond
    public ResponseEntity<String> signUp(@RequestBody String entity) {
        return ResponseEntity.status(200).body(entity);
    }

    // fetch all persons
    @GetMapping("/getallpersons")  
    public ResponseEntity<List<Person>> getAllPersons() {
        return ResponseEntity.status(200).body(personService.getAllPersons());
    }
    
    // fetch person by id - passing in path variable
    @GetMapping("/getpersonbyid/{id}")
    public ResponseEntity<Optional<Person>> getPersonById(@PathVariable int id) {
        return ResponseEntity.status(200).body(personService.getPersonById(id));
    }

    // fetch person by id - passing in body
    @PostMapping("/getpersonbyid")
    public ResponseEntity<Optional<Person>> getPersonById(@RequestBody Person person) {
        return ResponseEntity.status(200).body(personService.getPersonById(person.getPersonid()));
    }
    
    // insert a record (new person)
    @PostMapping("/saveperson")
    public ResponseEntity<String> savePerson(@RequestBody Person person) {
        StringBuilder firstName = new StringBuilder();
        StringBuilder lastName = new StringBuilder();
        firstName.append(person.getFirstname().substring(0,1).toUpperCase());
        firstName.append(person.getFirstname().substring(1, person.getFirstname().length()));
        lastName.append(person.getLastname().substring(0,1).toUpperCase());
        lastName.append(person.getLastname().substring(1, person.getLastname().length()));
        return ResponseEntity.status(201).body("(Mr/Ms) " + firstName + " " 
        + lastName + " - Saved Succesfully...\n\n" + personService.savePerson(person));   //Created, status code - 201
    }

    // fetch persons by firstname
    @PostMapping("/getpersonsbyfirstname")
    public ResponseEntity<List<Person>> getPersonsByFirstname(@RequestBody Person person) {
        return ResponseEntity.status(200).body(personService.getPersonsByFirstname(person.getFirstname()));
    }

    // fetch persons between ages 15 and 25 (custom query)
    @GetMapping("/getpersonsbetweenages")
    public ResponseEntity<List<Person>> getPersonsBetweenAges() {
        return ResponseEntity.status(200).body(personService.getPersonsBetweenAges());
    }

    // fetch persons in Page number given by the user
    @GetMapping("/getallpersonsbylimit")
    public ResponseEntity<Page<Person>> getAllPersonsByLimit(@RequestParam(defaultValue = "0") int pageNumber,
                                                           @RequestParam(defaultValue = "5") int pageSize ) {        
        return ResponseEntity.status(200).body(personService.getAllPersonsByLimit(pageNumber, pageSize));
    }

    // fetch persons' firstname whose age is greater than the given age
    @GetMapping("/getpersonsfirstnamebygreaterthanage")
    public ResponseEntity<List<String>> getPersonsFirstNameByGreaterThanAge(@RequestParam int age) {
        return ResponseEntity.status(200).body(personService.getPersonsFirstNameByGreaterThanAge(age));
    }

    // fetch persons who stays in chennai
    @GetMapping("/getpersonsbyaddresschennai")
    public ResponseEntity<List<Person>> getPersonsByAddressChennai() {
        return ResponseEntity.status(200).body(personService.getPersonsByAddressChennai());
    }

    // fetch join data from Person and Degrees
    @GetMapping("/getallpersonswithdegrees")
    public ResponseEntity<List<PersonDegrees>> getAllPersonsWithDegrees() {
        return ResponseEntity.status(200).body(personService.getAllPersonsWithDegrees());
    } 

    // insert Person and Degrees tables in one-go, when a new Person is inserted
    @PostMapping("/savePersonanddegrees")
    public ResponseEntity<String> savePersonAndDegrees(@RequestBody PersonDegreesInsert pd) {
        StringBuilder firstName = new StringBuilder();
        StringBuilder lastName = new StringBuilder();
        firstName.append(pd.getFirstname().substring(0,1).toUpperCase());
        firstName.append(pd.getFirstname().substring(1, pd.getFirstname().length()));
        lastName.append(pd.getLastname().substring(0,1).toUpperCase());
        lastName.append(pd.getLastname().substring(1, pd.getLastname().length()));
        return ResponseEntity.status(201).body("(Mr/Ms) " + firstName + " " 
        + lastName + " - Saved Succesfully...\n\n" + personService.savePersonAndDegrees(pd));
    }

    // fetch person by id with degree
    @GetMapping("/getpersonbyidwithdegrees")
    public ResponseEntity<Optional<Person>>  getPersonbyidWithDegrees(@RequestParam int id){
        return ResponseEntity.status(200).body(personService.getPersonbyidWithDegrees(id));
    }
    
    // update person through personid (any column)
    @PostMapping("/updateperson")
    public ResponseEntity<String> updatePerson(@RequestBody Person p) {
        Person person = personService.updatePerson(p);
        
        if(person != null) {       
            StringBuilder firstName = new StringBuilder();
            StringBuilder lastName = new StringBuilder();
            firstName.append(person.getFirstname().substring(0,1).toUpperCase());
            firstName.append(person.getFirstname().substring(1, person.getFirstname().length()));
            lastName.append(person.getLastname().substring(0,1).toUpperCase());
            lastName.append(person.getLastname().substring(1, person.getLastname().length()));
            return ResponseEntity.status(201).body("(Mr/Ms) " + firstName + " " 
            + lastName + " - Updated Succesfully...\n\n" + person);
        } else return ResponseEntity.status(404).body("Person not Found");
    }    

    // delete person by id
    @DeleteMapping("/deletepersonbyid") 
    public ResponseEntity<String> deletePersonById(@RequestParam int id) {
        boolean isDeleted = personService.deletePersonById(id);
        if(isDeleted) {
            return ResponseEntity.status(200).body("Person with id " + id + " deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Person with id " + id + " not found");
        }
    }
}
