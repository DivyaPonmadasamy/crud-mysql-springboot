package com.mvcexample.demomvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.mvcexample.demomvc.model.Degrees;
import com.mvcexample.demomvc.services.DegreesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class DegreesController {
    @Autowired
    DegreesService degreesService;

    // fetch all records
    @GetMapping("/getalldegrees")
    public ResponseEntity<List<Degrees>> getAllDegrees() {
        return ResponseEntity.status(200).body(degreesService.getAllDegrees());
    }
    
    // insert a record
    @PostMapping("/savedegree")
    public ResponseEntity<String> saveDegrees(Degrees degrees) {
        return ResponseEntity.status(201).body("Saved Successfully...\n\n" + degreesService.saveDegrees(degrees));
    }
}
