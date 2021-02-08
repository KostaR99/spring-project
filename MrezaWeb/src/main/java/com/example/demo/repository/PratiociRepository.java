package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Korisnik;
import model.Pratioci;

public interface PratiociRepository extends JpaRepository<Pratioci, Integer>{
	List<Pratioci> findAllBykorisnik(Korisnik k);
}
