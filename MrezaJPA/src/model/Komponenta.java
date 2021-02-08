package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the komponenta database table.
 * 
 */
@Entity
@NamedQuery(name="Komponenta.findAll", query="SELECT k FROM Komponenta k")
public class Komponenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idkomponenta;

	private String nazivKomponente;

	private String opis;

	private String slikaKomponente;

	//bi-directional many-to-many association to Javnakonfiguracija
	@ManyToMany
	@JoinTable(
		name="komponentajav"
		, joinColumns={
			@JoinColumn(name="komponenta_idkomponenta")
			}
		, inverseJoinColumns={
			@JoinColumn(name="javnaKonfiguracija_idjavnaKonfiguracija")
			}
		)
	private List<Javnakonfiguracija> javnakonfiguracijas;

	//bi-directional many-to-one association to Tipkomponente
	@ManyToOne
	private Tipkomponente tipkomponente;

	//bi-directional many-to-many association to Privatnakonfiguracija
	@ManyToMany(mappedBy="komponentas")
	private List<Privatnakonfiguracija> privatnakonfiguracijas;

	public Komponenta() {
	}

	public int getIdkomponenta() {
		return this.idkomponenta;
	}

	public void setIdkomponenta(int idkomponenta) {
		this.idkomponenta = idkomponenta;
	}

	public String getNazivKomponente() {
		return this.nazivKomponente;
	}

	public void setNazivKomponente(String nazivKomponente) {
		this.nazivKomponente = nazivKomponente;
	}

	public String getOpis() {
		return this.opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getSlikaKomponente() {
		return this.slikaKomponente;
	}

	public void setSlikaKomponente(String slikaKomponente) {
		this.slikaKomponente = slikaKomponente;
	}

	public List<Javnakonfiguracija> getJavnakonfiguracijas() {
		return this.javnakonfiguracijas;
	}

	public void setJavnakonfiguracijas(List<Javnakonfiguracija> javnakonfiguracijas) {
		this.javnakonfiguracijas = javnakonfiguracijas;
	}

	public Tipkomponente getTipkomponente() {
		return this.tipkomponente;
	}

	public void setTipkomponente(Tipkomponente tipkomponente) {
		this.tipkomponente = tipkomponente;
	}

	public List<Privatnakonfiguracija> getPrivatnakonfiguracijas() {
		return this.privatnakonfiguracijas;
	}

	public void setPrivatnakonfiguracijas(List<Privatnakonfiguracija> privatnakonfiguracijas) {
		this.privatnakonfiguracijas = privatnakonfiguracijas;
	}

}