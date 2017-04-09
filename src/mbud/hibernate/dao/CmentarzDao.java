package mbud.hibernate.dao;

import java.util.List;

import mbud.hibernate.mapping.Cmentarz;

@SuppressWarnings({ "unchecked", "deprecation" })
public class CmentarzDao extends AbstractDao<Cmentarz> {

	@Override
	public List<Cmentarz> findAll() {
		List<Cmentarz> list = getSession().createQuery("from Cmentarz").list();
		return list;
	}

	@Override
	public Cmentarz findById(Integer id) {
		Cmentarz c = getSession().get(Cmentarz.class, id);
		return c;
	}

}
