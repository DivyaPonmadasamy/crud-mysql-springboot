package com.mvcexample.demomvc.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import com.mvcexample.demomvc.model.Person;
import com.mvcexample.demomvc.model.PersonDegrees;

@Repository
public interface PersonRepo extends JpaRepository<Person, Integer> {
    List<Person> findByFirstname(String firstname);

    @Query(value = "SELECT * FROM person WHERE age >= 15 && age <= 25", nativeQuery = true)   // native SQL query
    List<Person> findBetweenAges(); // between age = 15 and 25

    @Override
    @NonNull
    Page<Person> findAll(@NonNull Pageable pageable);   // first 5 records

    @Query("SELECT p.firstname FROM Person p WHERE p.age > :age")   // JPQL - maps Entity class not DB
    List<String> findFirstNameByGreaterThanAge(@Param("age") int age);  // return firstname of age > ?

    @Query("SELECT p FROM Person p WHERE p.address LIKE '%chennai%'")
    List<Person> findByAddressChennai();    // return records who lives in chennai

    // @Query(value = "SELECT p.personid, firstname, lastname, age, address, dateofjoin, " +
    //                 "GROUP_CONCAT(d.degreename separator ', ') as degrees " +
    //                 "FROM person p JOIN degrees d ON p.personid = d.personid " +
    //                 "GROUP BY p.personid, firstname, lastname, age, address, dateofjoin", nativeQuery = true)
    @Query(name = "Person.findAllWithDegrees", nativeQuery = true) 
    List<PersonDegrees> findAllWithDegrees();   // joining Person and Degrees, with custom degrees column
}