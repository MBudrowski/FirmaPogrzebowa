package mbud.hibernate.dao;

import java.util.List;

import mbud.hibernate.mapping.Kosciol;

@SuppressWarnings({ "deprecation", "unchecked" })
public class KosciolDao extends AbstractDao<Kosciol> {

	@Override
	public List<Kosciol> findAll() {
		List<Kosciol> list = getSession().createQuery("from Kosciol").list();
		return list;
	}

	@Override
	public Kosciol findById(Integer id) {
		Kosciol k = getSession().get(Kosciol.class, id);
		return k;
	}

}
