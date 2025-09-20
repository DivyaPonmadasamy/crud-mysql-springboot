package com.mvcexample.demomvc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvcexample.demomvc.model.Degrees;
import com.mvcexample.demomvc.repository.DegreesRepo;

@Service
public class DegreesService {
    @Autowired
    DegreesRepo degreesRepo;

    // fetch all records
    public List<Degrees> getAllDegrees() {
        return degreesRepo.findAll();
    }

    //insert a record
    public Degrees saveDegrees(Degrees p) {
        return degreesRepo.save(p);
    }
}
