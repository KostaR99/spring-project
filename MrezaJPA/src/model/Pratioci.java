package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the pratioci database table.
 * 
 */
@Entity
@NamedQuery(name="Pratioci.findAll", query="SELECT p FROM Pratioci p")
public class Pratioci implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idpratioci;

	private int idDrugogKorisnika;

	//bi-directional many-to-one association to Korisnik
	@ManyToOne
	private Korisnik korisnik;

	public Pratioci() {
	}

	public int getIdpratioci() {
		return this.idpratioci;
	}

	public void setIdpratioci(int idpratioci) {
		this.idpratioci = idpratioci;
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