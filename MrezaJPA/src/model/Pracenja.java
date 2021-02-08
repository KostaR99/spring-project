package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the pracenja database table.
 * 
 */
@Entity
@NamedQuery(name="Pracenja.findAll", query="SELECT p FROM Pracenja p")
public class Pracenja implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idpracenja;

	private int idDrugogKorisnika;

	//bi-directional many-to-one association to Korisnik
	@ManyToOne
	private Korisnik korisnik;

	public Pracenja() {
	}

	public int getIdpracenja() {
		return this.idpracenja;
	}

	public void setIdpracenja(int idpracenja) {
		this.idpracenja = idpracenja;
	}

	public int getIdDrugogKorisnika() {
		return this.idDrugogKorisnika;
	}

	public void setIdDrugogKorisnika(int idDrugogKorisnika) {
		this.idDrugogKorisnika = idDrugogKorisnika;
	}

	public Korisnik getKorisnik() {
		return this.korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

}