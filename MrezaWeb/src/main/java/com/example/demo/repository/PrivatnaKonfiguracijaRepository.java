package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Korisnik;
import model.Privatnakonfiguracija;

public interface PrivatnaKonfiguracijaRepository extends JpaRepository<Privatnakonfiguracija, Integer>{
	List<Privatnakonfiguracija> findAllBykorisnik(Korisnik k);
	List<Privatnakonfiguracija> findAllBynazivPrivKonf(String naziv);
}
