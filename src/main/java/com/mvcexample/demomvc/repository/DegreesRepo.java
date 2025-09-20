package com.mvcexample.demomvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvcexample.demomvc.model.Degrees;

public interface DegreesRepo extends JpaRepository<Degrees, Integer> {

}
