package com.mvcexample.demomvc.model;

// interface to fetch Person with degrees through auto-mapping, but the order of display was out of control
// a DTO (PersonDegrees.java) is used instead, where mapping is done manually
public interface PersonDegreeInterface {
    Integer getPersonid();
    String getFirstname();
    String getLastname();
    Integer getAge();
    String getAddress();
    String getDateofjoin();
    String getDegrees();
}
