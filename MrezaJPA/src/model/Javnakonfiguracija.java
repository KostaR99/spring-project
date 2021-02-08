package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the javnakonfiguracija database table.
 * 
 */
@Entity
@NamedQuery(name="Javnakonfiguracija.findAll", query="SELECT j FROM Javnakonfiguracija j")
public class Javnakonfiguracija implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idjavnaKonfiguracija;

	private String namena;

	private String nazivKonfiguracije;

	private String slikaKonfiguracije;

	//bi-directional many-to-many association to Komponenta
	@ManyToMany(mappedBy="javnakonfiguracijas")
	private List<Komponenta> komponentas;

	public Javnakonfiguracija() {
	}

	public int getIdjavnaKonfiguracija() {
		return this.idjavnaKonfiguracija;
	}

	public void setIdjavnaKonfiguracija(int idjavnaKonfiguracija) {
		this.idjavnaKonfiguracija = idjavnaKonfiguracija;
	}

	public String getNamena() {
		return this.namena;
	}

	public void setNamena(String namena) {
		this.namena = namena;
	}

	public String getNazivKonfiguracije() {
		return this.nazivKonfiguracije;
	}

	public void setNazivKonfiguracije(String nazivKonfiguracije) {
		this.nazivKonfiguracije = nazivKonfiguracije;
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

}