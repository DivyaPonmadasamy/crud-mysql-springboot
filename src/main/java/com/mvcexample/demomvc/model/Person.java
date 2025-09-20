package com.mvcexample.demomvc.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SqlResultSetMapping;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

@NamedNativeQuery(
    name = "Person.findAllWithDegrees", // alias name to be used in UserRepo
    query = "SELECT p.personid, firstname, lastname, age, address, dateofjoin, " +
            "GROUP_CONCAT(d.degreename separator ', ') as degrees " +
            "FROM person p JOIN degrees d ON p.personid = d.personid " +
            "GROUP BY p.personid, firstname, lastname, age, address, dateofjoin",
    resultSetMapping = "PersonDegreeMapping"    
)

// manual mapping to the fields of DTO (PersonDegrees.java)
@SqlResultSetMapping(
    name = "PersonDegreeMapping",
    classes = @ConstructorResult(
        targetClass = PersonDegrees.class,
        columns = {
            @ColumnResult(name = "personid", type = Integer.class),
            @ColumnResult(name = "firstname", type = String.class),
            @ColumnResult(name = "lastname", type = String.class),
            @ColumnResult(name = "age", type = Integer.class),
            @ColumnResult(name = "address", type = String.class),
            @ColumnResult(name = "dateofjoin", type = LocalDate.class),
            @ColumnResult(name = "degrees", type = String.class)
        }
    )
)

@Component
@Entity
public class Person {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto_increment for MySQL
    private Integer personid;
    private String firstname;
    private String lastname;
    private Integer age;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dateofjoin")
    private LocalDate dateofjoin;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true) //when a person entity is removed, corrs. degrees entity is removed
    // @JsonIgnore
    @JsonManagedReference
    private List<Degrees> degrees = new ArrayList<>();

    // establishing bi-directional link
    public void addDegree(Degrees degree) {
        degrees.add(degree);    // sets Degrees list of objects in Person entity
        degree.setPerson(this); // sets Person object in Degrees entity for each degreename (back-reference for Hibernate)
    }

    @Override
    public String toString() {
        return "{\r\n" + //
                        "    \"personid\": " + personid + ",\r\n" +
                        "    \"firstname\": \"" + firstname + "\",\r\n" +
                        "    \"lastname\": \"" + lastname + "\",\r\n" +
                        "    \"age\": " + age + ",\r\n" +
                        "    \"address\": \"" + address + "\",\r\n" +
                        "    \"dateofjoin\": \"" + dateofjoin + "\"\r\n" +
                        "}";
    }
}