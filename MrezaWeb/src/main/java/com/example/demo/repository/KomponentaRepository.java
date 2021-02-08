package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Komponenta;

public interface KomponentaRepository extends JpaRepository<Komponenta, Integer>{
	@Query("SELECT k from Komponenta k where k.tipkomponente.idtipKomponente like:idK")
	List<Komponenta> getPoTipu(@Param("idK")int id);
	List<Komponenta> findAllBynazivKomponente(String naziv);
}
