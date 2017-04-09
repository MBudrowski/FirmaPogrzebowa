package mbud.hibernate.mapping;

/// default package
// Generated 2016-11-16 16:55:22 by Hibernate Tools 5.2.0.Beta1

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Pracownik generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "pracownik", catalog = "pogrzebowka")
public class Pracownik implements java.io.Serializable {

	private Integer id;
	private DomPogrzebowy domPogrzebowy;
	private String imie;
	private String nazwisko;
	private String adres;
	private Date dataUrodzenia;
	private Date dataZatrudnienia;
	private Date dataZwolnienia;
	private String telefon;
	private BigDecimal wynagrodzenie;
	private String login;
	private String haslo;
	private List<Zamowienie> zamowienia = new LinkedList<Zamowienie>();

	public Pracownik() {
	}

	public Pracownik(DomPogrzebowy domPogrzebowy, String imie, String nazwisko, String adres, Date dataUrodzenia,
			Date dataZatrudnienia, String telefon, BigDecimal wynagrodzenie, String login, String haslo) {
		this.domPogrzebowy = domPogrzebowy;
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.adres = adres;
		this.dataUrodzenia = dataUrodzenia;
		this.dataZatrudnienia = dataZatrudnienia;
		this.telefon = telefon;
		this.wynagrodzenie = wynagrodzenie;
		this.login = login;
		this.haslo = haslo;
	}

	public Pracownik(DomPogrzebowy domPogrzebowy, String imie, String nazwisko, String adres, Date dataUrodzenia,
			Date dataZatrudnienia, Date dataZwolnienia, String telefon, BigDecimal wynagrodzenie, String login,
			String haslo, List<Zamowienie> zamowienies) {
		this.domPogrzebowy = domPogrzebowy;
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.adres = adres;
		this.dataUrodzenia = dataUrodzenia;
		this.dataZatrudnienia = dataZatrudnienia;
		this.dataZwolnienia = dataZwolnienia;
		this.telefon = telefon;
		this.wynagrodzenie = wynagrodzenie;
		this.login = login;
		this.haslo = haslo;
		this.zamowienia = zamowienies;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_domu", nullable = false)
	public DomPogrzebowy getDomPogrzebowy() {
		return this.domPogrzebowy;
	}

	public void setDomPogrzebowy(DomPogrzebowy domPogrzebowy) {
		this.domPogrzebowy = domPogrzebowy;
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

	@Column(name = "adres", nullable = false, length = 128)
	public String getAdres() {
		return this.adres;
	}

	public void setAdres(String adres) {
		this.adres = adres;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_urodzenia", nullable = false, length = 19)
	public Date getDataUrodzenia() {
		return this.dataUrodzenia;
	}

	public void setDataUrodzenia(Date dataUrodzenia) {
		this.dataUrodzenia = dataUrodzenia;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_zatrudnienia", nullable = false, length = 19)
	public Date getDataZatrudnienia() {
		return this.dataZatrudnienia;
	}

	public void setDataZatrudnienia(Date dataZatrudnienia) {
		this.dataZatrudnienia = dataZatrudnienia;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_zwolnienia", length = 19)
	public Date getDataZwolnienia() {
		return this.dataZwolnienia;
	}

	public void setDataZwolnienia(Date dataZwolnienia) {
		this.dataZwolnienia = dataZwolnienia;
	}

	@Column(name = "telefon", nullable = false, length = 15)
	public String getTelefon() {
		return this.telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	@Column(name = "wynagrodzenie", nullable = false, precision = 6)
	public BigDecimal getWynagrodzenie() {
		return this.wynagrodzenie;
	}

	public void setWynagrodzenie(BigDecimal wynagrodzenie) {
		this.wynagrodzenie = wynagrodzenie;
	}

	@Column(name = "login", nullable = false, length = 64)
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(name = "haslo", nullable = false, length = 64)
	public String getHaslo() {
		return this.haslo;
	}

	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pracownik")
	public List<Zamowienie> getZamowienia() {
		return this.zamowienia;
	}

	public void setZamowienia(List<Zamowienie> zamowienies) {
		this.zamowienia = zamowienies;
	}

}
