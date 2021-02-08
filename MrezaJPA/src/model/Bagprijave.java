package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the bagprijave database table.
 * 
 */
@Entity
@NamedQuery(name="Bagprijave.findAll", query="SELECT b FROM Bagprijave b")
public class Bagprijave implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idbagPrijave;

	private String opisBaga;

	//bi-directional many-to-one association to Korisnik
	@ManyToOne
	private Korisnik korisnik;

	public Bagprijave() {
	}

	public int getIdbagPrijave() {
		return this.idbagPrijave;
	}

	public void setIdbagPrijave(int idbagPrijave) {
		this.idbagPrijave = idbagPrijave;
	}

	public String getOpisBaga() {
		return this.opisBaga;
	}

	public void setOpisBaga(String opisBaga) {
		this.opisBaga = opisBaga;
	}

	public Korisnik getKorisnik() {
		return this.korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

}