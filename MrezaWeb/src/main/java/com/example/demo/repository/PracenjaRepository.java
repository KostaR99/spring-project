package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Korisnik;
import model.Pracenja;


public interface PracenjaRepository extends JpaRepository<Pracenja, Integer>{
	List<Pracenja> findAllBykorisnik(Korisnik k);
}
