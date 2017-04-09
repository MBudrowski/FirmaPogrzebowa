package mbud.hibernate.dao;

import java.util.List;

import org.hibernate.query.Query;

import mbud.hibernate.mapping.Zamowienie;

@SuppressWarnings({ "unchecked", "deprecation" })
public class ZamowienieDao extends AbstractDao<Zamowienie> {

	@Override
	public List<Zamowienie> findAll() {
		List<Zamowienie> list = getSession().createQuery("from Zamowienie").list();
		return list;
	}

	@Override
	public Zamowienie findById(Integer id) {
		Zamowienie zamowienie = getSession().get(Zamowienie.class, id);
		return zamowienie;
	}

	public List<Zamowienie> findUsersOrders(Integer id) {
		List<Zamowienie> list = getSession().createQuery("from Zamowienie z where z.klient.id = " + id).list();
		return list;
	}

	public List<Zamowienie> findWorkerOrdersRealized(Integer id) {
		Query<Zamowienie> query = getSession().createQuery("from Zamowienie z "
				+ "where z.domPogrzebowy.id = (select p.domPogrzebowy.id from Pracownik p where p.id = " + id
				+ ") and z.odebrane = 1 order by z.dataZlozenia desc");
		query.setMaxResults(10);
		List<Zamowienie> list = query.list();
		return list;
	}

	public List<Zamowienie> findWorkerOrdersNotRealized(Integer id) {
		List<Zamowienie> list = getSession().createQuery("from Zamowienie z "
				+ "where z.domPogrzebowy.id = (select p.domPogrzebowy.id from Pracownik p where p.id = " + id
				+ ") and z.odebrane = 0 order by z.dataZlozenia").list();
		return list;
	}

}
