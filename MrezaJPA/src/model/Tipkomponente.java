package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipkomponente database table.
 * 
 */
@Entity
@NamedQuery(name="Tipkomponente.findAll", query="SELECT t FROM Tipkomponente t")
public class Tipkomponente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idtipKomponente;

	private String nazivTipa;

	//bi-directional many-to-one association to Komponenta
	@OneToMany(mappedBy="tipkomponente")
	private List<Komponenta> komponentas;

	public Tipkomponente() {
	}

	public int getIdtipKomponente() {
		return this.idtipKomponente;
	}

	public void setIdtipKomponente(int idtipKomponente) {
		this.idtipKomponente = idtipKomponente;
	}

	public String getNazivTipa() {
		return this.nazivTipa;
	}

	public void setNazivTipa(String nazivTipa) {
		this.nazivTipa = nazivTipa;
	}

	public List<Komponenta> getKomponentas() {
		return this.komponentas;
	}

	public void setKomponentas(List<Komponenta> komponentas) {
		this.komponentas = komponentas;
	}

	public Komponenta addKomponenta(Komponenta komponenta) {
		getKomponentas().add(komponenta);
		komponenta.setTipkomponente(this);

		return komponenta;
	}

	public Komponenta removeKomponenta(Komponenta komponenta) {
		getKomponentas().remove(komponenta);
		komponenta.setTipkomponente(null);

		return komponenta;
	}

}