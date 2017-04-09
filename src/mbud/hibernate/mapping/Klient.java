package mbud.hibernate.mapping;

/// default package
// Generated 2016-11-16 16:55:22 by Hibernate Tools 5.2.0.Beta1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Klient generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "klient", catalog = "pogrzebowka")
public class Klient implements java.io.Serializable {

	private Integer id;
	private String imie;
	private String nazwisko;
	private String email;
	private String haslo;
	private String telefon;
	private Set<Zamowienie> zamowienia = new HashSet<Zamowienie>(0);
	private Set<Pogrzeb> pogrzeby = new HashSet<Pogrzeb>(0);

	public Klient() {
	}

	public Klient(String imie, String nazwisko, String telefon) {
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.telefon = telefon;
	}

	public Klient(String imie, String nazwisko, String email, String haslo, String telefon) {
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.email = email;
		this.haslo = haslo;
		this.telefon = telefon;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "imie", nullable = false, length = 64)
	public String getImie() {
		return this.imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	@Column(name = "nazwisko", nullable = false, length = 64)
	public String getNazwisko() {
		return this.nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	@Column(name = "email", length = 64)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "haslo", length = 64)
	public String getHaslo() {
		return this.haslo;
	}

	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}

	@Column(name = "telefon", nullable = false, length = 15)
	public String getTelefon() {
		return this.telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "klient")
	public Set<Zamowienie> getZamowienia() {
		return this.zamowienia;
	}

	public void setZamowienia(Set<Zamowienie> zamowienies) {
		this.zamowienia = zamowienies;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "klient")
	public Set<Pogrzeb> getPogrzeby() {
		return this.pogrzeby;
	}

	public void setPogrzeby(Set<Pogrzeb> pogrzebs) {
		this.pogrzeby = pogrzebs;
	}

}
