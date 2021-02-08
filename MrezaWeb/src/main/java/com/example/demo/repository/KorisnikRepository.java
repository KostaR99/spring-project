package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Korisnik;

public interface KorisnikRepository extends JpaRepository<Korisnik, Integer>{

	Optional<Korisnik> findByusername(String username);
	Optional<Korisnik> findByemail(String email);

}
