package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the privatnakonfiguracija database table.
 * 
 */
@Entity
@NamedQuery(name="Privatnakonfiguracija.findAll", query="SELECT p FROM Privatnakonfiguracija p")
public class Privatnakonfiguracija implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idprivatnaKonfiguracija;

	private String namena;

	private String nazivPrivKonf;

	private String slikaKonfiguracije;

	//bi-directional many-to-many association to Komponenta
	@ManyToMany
	@JoinTable(
		name="komponentapriv"
		, joinColumns={
			@JoinColumn(name="privatnaKonfiguracija_idprivatnaKonfiguracija")
			}
		, inverseJoinColumns={
			@JoinColumn(name="komponenta_idkomponenta")
			}
		)
	private List<Komponenta> komponentas;

	//bi-directional many-to-one association to Korisnik
	@ManyToOne
	private Korisnik korisnik;

	public Privatnakonfiguracija() {
	}

	public int getIdprivatnaKonfiguracija() {
		return this.idprivatnaKonfiguracija;
	}

	public void setIdprivatnaKonfiguracija(int idprivatnaKonfiguracija) {
		this.idprivatnaKonfiguracija = idprivatnaKonfiguracija;
	}

	public String getNamena() {
		return this.namena;
	}

	public void setNamena(String namena) {
		this.namena = namena;
	}

	public String getNazivPrivKonf() {
		return this.nazivPrivKonf;
	}

	public void setNazivPrivKonf(String nazivPrivKonf) {
		this.nazivPrivKonf = nazivPrivKonf;
	}

	public String getSlikaKonfiguracije() {
		return this.slikaKonfiguracije;
	}

	public void setSlikaKonfiguracije(String slikaKonfiguracije) {
		this.slikaKonfiguracije = slikaKonfiguracije;
	}

	public List<Komponenta> getKomponentas() {
		return this.komponentas;
	}

	public void setKomponentas(List<Komponenta> komponentas) {
		this.komponentas = komponentas;
	}

	public Korisnik getKorisnik() {
		return this.korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

}