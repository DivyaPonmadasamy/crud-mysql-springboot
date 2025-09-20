package com.mvcexample.demomvc.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

@Entity
public class Degrees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer degreeid;
    @Column(insertable=false, updatable=false)
    private Integer personid; 
    private String degreename;
    private Integer yearofpassing;
    private Float cgpa;

    @ManyToOne
    @JoinColumn(name = "personid", nullable = false) // cannot contain null foreign key
    @JsonBackReference
    private Person person;

    @Override
    public String toString() {
        return "{\r\n" + //
                        "    \"degreeid\": " + degreeid + ",\r\n" +
                        "    \"personid\": " + personid + ",\r\n" +
                        "    \"degreename\": \"" + degreename + "\",\r\n" +
                        "    \"yearofpassing\": " + yearofpassing + ",\r\n" +
                        "    \"cgpa\": " + cgpa + "\r\n" +
                        "}";
    }
}
