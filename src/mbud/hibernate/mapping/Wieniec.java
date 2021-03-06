package mbud.hibernate.mapping;

/// default package
// Generated 2016-11-16 16:55:22 by Hibernate Tools 5.2.0.Beta1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * Wieniec generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "wieniec", catalog = "pogrzebowka")
public class Wieniec implements java.io.Serializable {

	private int id;
	private Produkt produkt;
	private Boolean kwiatyZywe;

	public Wieniec() {
	}

	public Wieniec(Produkt produkt) {
		this.produkt = produkt;
	}

	public Wieniec(Produkt produkt, Boolean kwiatyZywe) {
		this.produkt = produkt;
		this.kwiatyZywe = kwiatyZywe;
	}

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "produkt"))
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public Produkt getProdukt() {
		return this.produkt;
	}

	public void setProdukt(Produkt produkt) {
		this.produkt = produkt;
	}

	@Column(name = "kwiaty_zywe")
	public Boolean getKwiatyZywe() {
		return this.kwiatyZywe;
	}

	public void setKwiatyZywe(Boolean kwiatyZywe) {
		this.kwiatyZywe = kwiatyZywe;
	}
	
	public String czyKwiatyZywe() {
		return (getKwiatyZywe())? "TAK" : "NIE";
	}

}
