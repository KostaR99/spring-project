package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Javnakonfiguracija;

public interface JavnaKonfiguracijaRepository extends JpaRepository<Javnakonfiguracija, Integer>{
	List<Javnakonfiguracija> findAllBynazivKonfiguracije(String naziv);
}
