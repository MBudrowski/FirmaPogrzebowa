package mbud.hibernate.dao;

import java.util.List;

import javax.persistence.Query;

import mbud.hibernate.mapping.Pogrzeb;

@SuppressWarnings({ "unchecked", "deprecation" })
public class PogrzebDao extends AbstractDao<Pogrzeb> {

	@Override
	public List<Pogrzeb> findAll() {
		List<Pogrzeb> list = getSession().createQuery("from Pogrzeb").list();
		return list;
	}

	@Override
	public Pogrzeb findById(Integer id) {
		Pogrzeb pogrzeb = getSession().get(Pogrzeb.class, id);
		return pogrzeb;
	}
	
	public List<Pogrzeb> findUsersFunerals(Integer id) {
		List<Pogrzeb> list = getSession().createQuery("from Pogrzeb p where p.klient.id = " + id).list();
		return list;
	}
	
	public List<Pogrzeb> findLatestFunerals() {
		Query q = getSession().createQuery("from Pogrzeb p where p.dataWystawieniaCiala is not null "
				+ "and p.dataPogrzebu is not null "
				+ "order by p.dataPogrzebu desc");
		q.setMaxResults(5);
		List<Pogrzeb> list = (List<Pogrzeb>) q.getResultList();
		return list;
	}
	
	public List<Pogrzeb> findWorkerFunerals(Integer id) {
		Query q = getSession().createQuery("from Pogrzeb p "
				+ "where p.domPogrzebowy.id = (select pr.domPogrzebowy.id from Pracownik pr where pr.id = " + id + ") "
						+ "order by p.dataZamowienia");
		List<Pogrzeb> list = (List<Pogrzeb>) q.getResultList();
		return list;
	}

}
