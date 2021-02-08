package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the korisnik database table.
 * 
 */
@Entity
@NamedQuery(name="Korisnik.findAll", query="SELECT k FROM Korisnik k")
public class Korisnik implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idkorisnik;

	private String email;

	private String password;

	private String slikaKorisnika;

	private String username;

	//bi-directional many-to-one association to Bagprijave
	@OneToMany(mappedBy="korisnik")
	private List<Bagprijave> bagprijaves;

	//bi-directional many-to-one association to Uloga
	@ManyToOne
	private Uloga uloga;

	//bi-directional many-to-one association to Korisnikprijave
	@OneToMany(mappedBy="korisnik")
	private List<Korisnikprijave> korisnikprijaves;

	//bi-directional many-to-one association to Privatnakonfiguracija
	@OneToMany(mappedBy="korisnik")
	private List<Privatnakonfiguracija> privatnakonfiguracijas;

	//bi-directional many-to-one association to Status
	@OneToMany(mappedBy="korisnik")
	private List<Status> statuses;

	//bi-directional many-to-one association to Pracenja
	@OneToMany(mappedBy="korisnik")
	private List<Pracenja> pracenjas;

	//bi-directional many-to-one association to Pratioci
	@OneToMany(mappedBy="korisnik")
	private List<Pratioci> pratiocis;

	public Korisnik() {
	}

	public int getIdkorisnik() {
		return this.idkorisnik;
	}

	public void setIdkorisnik(int idkorisnik) {
		this.idkorisnik = idkorisnik;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSlikaKorisnika() {
		return this.slikaKorisnika;
	}

	public void setSlikaKorisnika(String slikaKorisnika) {
		this.slikaKorisnika = slikaKorisnika;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Bagprijave> getBagprijaves() {
		return this.bagprijaves;
	}

	public void setBagprijaves(List<Bagprijave> bagprijaves) {
		this.bagprijaves = bagprijaves;
	}

	public Bagprijave addBagprijave(Bagprijave bagprijave) {
		getBagprijaves().add(bagprijave);
		bagprijave.setKorisnik(this);

		return bagprijave;
	}

	public Bagprijave removeBagprijave(Bagprijave bagprijave) {
		getBagprijaves().remove(bagprijave);
		bagprijave.setKorisnik(null);

		return bagprijave;
	}

	public Uloga getUloga() {
		return this.uloga;
	}

	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}

	public List<Korisnikprijave> getKorisnikprijaves() {
		return this.korisnikprijaves;
	}

	public void setKorisnikprijaves(List<Korisnikprijave> korisnikprijaves) {
		this.korisnikprijaves = korisnikprijaves;
	}

	public Korisnikprijave addKorisnikprijave(Korisnikprijave korisnikprijave) {
		getKorisnikprijaves().add(korisnikprijave);
		korisnikprijave.setKorisnik(this);

		return korisnikprijave;
	}

	public Korisnikprijave removeKorisnikprijave(Korisnikprijave korisnikprijave) {
		getKorisnikprijaves().remove(korisnikprijave);
		korisnikprijave.setKorisnik(null);

		return korisnikprijave;
	}

	public List<Privatnakonfiguracija> getPrivatnakonfiguracijas() {
		return this.privatnakonfiguracijas;
	}

	public void setPrivatnakonfiguracijas(List<Privatnakonfiguracija> privatnakonfiguracijas) {
		this.privatnakonfiguracijas = privatnakonfiguracijas;
	}

	public Privatnakonfiguracija addPrivatnakonfiguracija(Privatnakonfiguracija privatnakonfiguracija) {
		getPrivatnakonfiguracijas().add(privatnakonfiguracija);
		privatnakonfiguracija.setKorisnik(this);

		return privatnakonfiguracija;
	}

	public Privatnakonfiguracija removePrivatnakonfiguracija(Privatnakonfiguracija privatnakonfiguracija) {
		getPrivatnakonfiguracijas().remove(privatnakonfiguracija);
		privatnakonfiguracija.setKorisnik(null);

		return privatnakonfiguracija;
	}

	public List<Status> getStatuses() {
		return this.statuses;
	}

	public void setStatuses(List<Status> statuses) {
		this.statuses = statuses;
	}

	public Status addStatus(Status status) {
		getStatuses().add(status);
		status.setKorisnik(this);

		return status;
	}

	public Status removeStatus(Status status) {
		getStatuses().remove(status);
		status.setKorisnik(null);

		return status;
	}

	public List<Pracenja> getPracenjas() {
		return this.pracenjas;
	}

	public void setPracenjas(List<Pracenja> pracenjas) {
		this.pracenjas = pracenjas;
	}

	public Pracenja addPracenja(Pracenja pracenja) {
		getPracenjas().add(pracenja);
		pracenja.setKorisnik(this);

		return pracenja;
	}

	public Pracenja removePracenja(Pracenja pracenja) {
		getPracenjas().remove(pracenja);
		pracenja.setKorisnik(null);

		return pracenja;
	}

	public List<Pratioci> getPratiocis() {
		return this.pratiocis;
	}

	public void setPratiocis(List<Pratioci> pratiocis) {
		this.pratiocis = pratiocis;
	}

	public Pratioci addPratioci(Pratioci pratioci) {
		getPratiocis().add(pratioci);
		pratioci.setKorisnik(this);

		return pratioci;
	}

	public Pratioci removePratioci(Pratioci pratioci) {
		getPratiocis().remove(pratioci);
		pratioci.setKorisnik(null);

		return pratioci;
	}

}