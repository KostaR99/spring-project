package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Status;

public interface StatusRepository extends JpaRepository<Status, Integer>{

}
