package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the korisnikprijave database table.
 * 
 */
@Entity
@NamedQuery(name="Korisnikprijave.findAll", query="SELECT k FROM Korisnikprijave k")
public class Korisnikprijave implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idkorisnikPrijave;

	private String opisPrijave;

	//bi-directional many-to-one association to Korisnik
	@ManyToOne
	private Korisnik korisnik;

	public Korisnikprijave() {
	}

	public int getIdkorisnikPrijave() {
		return this.idkorisnikPrijave;
	}

	public void setIdkorisnikPrijave(int idkorisnikPrijave) {
		this.idkorisnikPrijave = idkorisnikPrijave;
	}

	public String getOpisPrijave() {
		return this.opisPrijave;
	}

	public void setOpisPrijave(String opisPrijave) {
		this.opisPrijave = opisPrijave;
	}

	public Korisnik getKorisnik() {
		return this.korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

}