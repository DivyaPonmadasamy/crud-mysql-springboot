package com.mvcexample.demomvc.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

// DTO to fetch Person with degrees as a join column
public class PersonDegrees {
    private int personid;
    private String firstname;
    private String lastname;
    private int age;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateofjoin;
    private String degrees;

    PersonDegrees(int personid, String firstname, String lastname, int age, String address, LocalDate dateofjoin, String degrees) {
        this.personid = personid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.address = address;
        this.dateofjoin = dateofjoin;
        this.degrees = degrees;
    }
}
