package mbud.hibernate.dao;

import java.util.List;

import org.hibernate.Hibernate;

import mbud.hibernate.mapping.Produkt;

@SuppressWarnings({ "deprecation", "unchecked" })
public class ProduktDao extends AbstractDao<Produkt> {

	@Override
	public List<Produkt> findAll() {
		List<Produkt> list = getSession().createQuery("from Produkt").list();
		return list;
	}

	@Override
	public Produkt findById(Integer id) {
		Produkt produkt = getSession().get(Produkt.class, id);
		return produkt;
	}
	
	public List<Produkt> findCoffins() {
		List<Produkt> list = getSession().createQuery("select p from Produkt p, Trumna t "
				+ "where p.id = t.id order by p.nazwa").list();
		for (Produkt p : list) {
			Hibernate.initialize(p.getTrumna());
		}
		return list;
	}
	
	public List<Produkt> findWreaths() {
		List<Produkt> list = getSession().createQuery("select p from Produkt p, Wieniec w "
				+ "where p.id = w.id order by p.nazwa").list();
		for (Produkt p : list) {
			Hibernate.initialize(p.getWieniec());
		}
		return list;
	}
	
	public List<Produkt> findCandles() {
		List<Produkt> list = getSession().createQuery("select p from Produkt p, Znicz z "
				+ "where p.id = z.id order by p.nazwa").list();
		for (Produkt p : list) {
			Hibernate.initialize(p.getZnicz());
		}
		return list;
	}

}
