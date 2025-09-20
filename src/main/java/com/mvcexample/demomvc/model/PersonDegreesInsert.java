package com.mvcexample.demomvc.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

// DTO to insert Person and Degrees at the same time
public class PersonDegreesInsert {
    private int personid;
    private String firstname;
    private String lastname;
    private int age;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateofjoin;
    private List<DegreeList> degrees;

    @Setter
    @Getter
    // nested class to insert corresponding degree data of a person into Degrees table
    public static class DegreeList {
        private String degreename;
        private Integer yearofpassing;
        private Float cgpa;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n")
          .append("  \"personid\": ").append(personid).append(",\r\n")
          .append("  \"firstname\": \"").append(firstname).append("\",\r\n")
          .append("  \"lastname\": \"").append(lastname).append("\",\r\n")
          .append("  \"age\": ").append(age).append(",\r\n")
          .append("  \"address\": \"").append(address).append("\",\r\n")
          .append("  \"dateofjoin\": \"").append(dateofjoin).append("\",\r\n")
          .append("  \"degrees\": [\n");

        for (DegreeList degree : degrees) {
            sb.append("    {\n")
              .append("      \"degreename\": \"").append(degree.getDegreename()).append("\",\r\n")
              .append("      \"yearofpassing\": ").append(degree.getYearofpassing()).append(",\r\n")
              .append("      \"cgpa\": ").append(degree.getCgpa()).append(",\r\n")
              .append("    },\n");
        }

        if (!degrees.isEmpty()) {
            sb.setLength(sb.length() - 2); // remove last comma
        }
        sb.append("\n  ]\n}");

        return sb.toString();

        // return "{\r\n" + //
        //                 "    \"personid\": " + personid + ",\r\n" +
        //                 "    \"firstname\": \"" + firstname + "\",\r\n" +
        //                 "    \"lastname\": \"" + lastname + "\",\r\n" +
        //                 "    \"age\": " + age + ",\r\n" +
        //                 "    \"address\": \"" + address + "\",\r\n" +
        //                 "    \"dateofjoin\": \"" + dateofjoin + "\",\r\n" +
        //                 "    \"degrees\": \"" + degreename + "\"\r\n" +
        //                 "    \"yearofpassing\": " + yearofpassing + ",\r\n" +
        //                 "    \"cgpa\": " + cgpa + "\r\n" +
        //                 "}";
    }
}