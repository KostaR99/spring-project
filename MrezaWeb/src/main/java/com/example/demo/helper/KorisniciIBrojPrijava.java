package com.example.demo.helper;

import model.Korisnik;

public class KorisniciIBrojPrijava {
	private String username;
	private String email;
	private int brojPrijavaKorisnika;
	private int brojPrijavaBagova;
	private int brojKonfiguracija;
	public KorisniciIBrojPrijava(Korisnik k) {
		this.username=k.getUsername();
		this.email=k.getEmail();
		this.brojPrijavaBagova=k.getBagprijaves().size();
		this.brojPrijavaKorisnika=k.getKorisnikprijaves().size();
		this.brojKonfiguracija=k.getPrivatnakonfiguracijas().size();
	}

	public int getBrojKonfiguracija() {
		return brojKonfiguracija;
	}

	public void setBrojKonfiguracija(int brojKonfiguracija) {
		this.brojKonfiguracija = brojKonfiguracija;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getBrojPrijavaKorisnika() {
		return brojPrijavaKorisnika;
	}
	public void setBrojPrijavaKorisnika(int brojPrijavaKorisnika) {
		this.brojPrijavaKorisnika = brojPrijavaKorisnika;
	}
	public int getBrojPrijavaBagova() {
		return brojPrijavaBagova;
	}
	public void setBrojPrijavaBagova(int brojPrijavaBagova) {
		this.brojPrijavaBagova = brojPrijavaBagova;
	}
	
	
}
