package mbud.hibernate.mapping;

/// default package
// Generated 2016-11-16 16:55:22 by Hibernate Tools 5.2.0.Beta1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import mbud.util.StringUtil;

/**
 * Pogrzeb generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "pogrzeb", catalog = "pogrzebowka")
public class Pogrzeb implements java.io.Serializable {

	private Integer id;
	private Cmentarz cmentarz;
	private DomPogrzebowy domPogrzebowy;
	private Klient klient;
	private Kosciol kosciol;
	private Trumna trumna;
	private String imieDenata;
	private String nazwiskoDenata;
	private String adresDenata;
	private Date dataZamowienia;
	private Date dataUrodzenia;
	private Date dataSmierci;
	private Date dataPogrzebu;
	private Date dataWystawieniaCiala;
	private Double koszt;
	private Boolean oplacony;

	public Pogrzeb() {
	}

	public Pogrzeb(Cmentarz cmentarz, DomPogrzebowy domPogrzebowy, Klient klient, Kosciol kosciol,
			String imieDenata, String nazwiskoDenata, String adresDenata, Date dataZamowienia, Date dataUrodzenia,
			Date dataSmierci) {
		this.cmentarz = cmentarz;
		this.domPogrzebowy = domPogrzebowy;
		this.klient = klient;
		this.kosciol = kosciol;
		this.imieDenata = imieDenata;
		this.nazwiskoDenata = nazwiskoDenata;
		this.adresDenata = adresDenata;
		this.dataZamowienia = dataZamowienia;
		this.dataUrodzenia = dataUrodzenia;
		this.dataSmierci = dataSmierci;
	}

	public Pogrzeb(Cmentarz cmentarz, DomPogrzebowy domPogrzebowy, Klient klient, Kosciol kosciol, Trumna trumna,
			String imieDenata, String nazwiskoDenata, String adresDenata, Date dataZamowienia, Date dataUrodzenia,
			Date dataSmierci, Date dataPogrzebu, Date dataMszy, Double koszt, Boolean oplacony) {
		this.cmentarz = cmentarz;
		this.domPogrzebowy = domPogrzebowy;
		this.klient = klient;
		this.kosciol = kosciol;
		this.trumna = trumna;
		this.imieDenata = imieDenata;
		this.nazwiskoDenata = nazwiskoDenata;
		this.adresDenata = adresDenata;
		this.dataZamowienia = dataZamowienia;
		this.dataUrodzenia = dataUrodzenia;
		this.dataSmierci = dataSmierci;
		this.dataPogrzebu = dataPogrzebu;
		this.dataWystawieniaCiala = dataMszy;
		this.koszt = koszt;
		this.oplacony = oplacony;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cmentarza", nullable = false)
	public Cmentarz getCmentarz() {
		return this.cmentarz;
	}

	public void setCmentarz(Cmentarz cmentarz) {
		this.cmentarz = cmentarz;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_domu", nullable = false)
	public DomPogrzebowy getDomPogrzebowy() {
		return this.domPogrzebowy;
	}

	public void setDomPogrzebowy(DomPogrzebowy domPogrzebowy) {
		this.domPogrzebowy = domPogrzebowy;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_klienta", nullable = false)
	public Klient getKlient() {
		return this.klient;
	}

	public void setKlient(Klient klient) {
		this.klient = klient;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_kosciola", nullable = false)
	public Kosciol getKosciol() {
		return this.kosciol;
	}

	public void setKosciol(Kosciol kosciol) {
		this.kosciol = kosciol;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_trumny", nullable = true)
	public Trumna getTrumna() {
		return this.trumna;
	}

	public void setTrumna(Trumna trumna) {
		this.trumna = trumna;
	}

	@Column(name = "imie_denata", nullable = false, length = 64)
	public String getImieDenata() {
		return this.imieDenata;
	}

	public void setImieDenata(String imieDenata) {
		this.imieDenata = imieDenata;
	}

	@Column(name = "nazwisko_denata", nullable = false, length = 64)
	public String getNazwiskoDenata() {
		return this.nazwiskoDenata;
	}

	public void setNazwiskoDenata(String nazwiskoDenata) {
		this.nazwiskoDenata = nazwiskoDenata;
	}

	@Column(name = "adres_denata", nullable = false, length = 128)
	public String getAdresDenata() {
		return this.adresDenata;
	}

	public void setAdresDenata(String adresDenata) {
		this.adresDenata = adresDenata;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_zamowienia", nullable = false, length = 19)
	public Date getDataZamowienia() {
		return this.dataZamowienia;
	}

	public void setDataZamowienia(Date dataZamowienia) {
		this.dataZamowienia = dataZamowienia;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_urodzenia", nullable = false, length = 19)
	public Date getDataUrodzenia() {
		return this.dataUrodzenia;
	}

	public void setDataUrodzenia(Date dataUrodzenia) {
		this.dataUrodzenia = dataUrodzenia;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_smierci", nullable = false, length = 19)
	public Date getDataSmierci() {
		return this.dataSmierci;
	}

	public void setDataSmierci(Date dataSmierci) {
		this.dataSmierci = dataSmierci;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_pogrzebu", length = 19)
	public Date getDataPogrzebu() {
		return this.dataPogrzebu;
	}

	public void setDataPogrzebu(Date dataPogrzebu) {
		this.dataPogrzebu = dataPogrzebu;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_wystawienia_ciala", length = 19)
	public Date getDataWystawieniaCiala() {
		return this.dataWystawieniaCiala;
	}

	public void setDataWystawieniaCiala(Date dataWystawieniaCiala) {
		this.dataWystawieniaCiala = dataWystawieniaCiala;
	}

	@Column(name = "koszt", precision = 10)
	public Double getKoszt() {
		return this.koszt;
	}

	public void setKoszt(Double koszt) {
		this.koszt = koszt;
	}

	@Column(name = "oplacony")
	public Boolean getOplacony() {
		return this.oplacony;
	}

	public void setOplacony(Boolean oplacony) {
		this.oplacony = oplacony;
	}
	
	@Transient
	public String getCenaString() {
		if (getKoszt() == null || getKoszt() <= 0.0) {
			return "Niewyceniony";
		}
		return StringUtil.formatPrice(getKoszt()) + " zł";
	}
	
	@Transient
	public String getStatus() {
		Date cur = new Date();
		if (dataPogrzebu != null) {
			if (dataPogrzebu.before(cur)) {
				if (oplacony != null && oplacony) {
					return "ZREALIZOWANY";
				}
				else {
					return "ZREALIZOWANY, NIEOPŁACONY";
				}
			}
			else {
				if (oplacony != null && oplacony) {
					return "OCZEKUJĄCY NA REALIZACJĘ";
				}
				else {
					return "OCZEKUJĄCY NA REALIZACJĘ, NIEOPŁACONY";
				}
			}
		}
		if (oplacony != null && oplacony) {
			return "OCZEKUJĄCY NA WYZNACZENIE TERMINU";
		}
		if (koszt != null && koszt > 0.0) {
			return "WYCENIONY";
		}
		return "NOWY";
	}
	
	@Transient
	public boolean canBePaid() {
		return (koszt != null && koszt > 0.0 && (oplacony == null || !oplacony));
	}
	
	@Transient
	public boolean canBeEdited() {
		if (dataPogrzebu != null && dataPogrzebu.before(new Date())) {
			return false;
		}
		return true;
	}
	
	@Transient
	public boolean canDateBeDetermined() {
		if (koszt == null || koszt <= 0.0) {
			return false;
		}
		if (dataPogrzebu == null && dataWystawieniaCiala == null) {
			return true;
		}
		return false;
	}

}
