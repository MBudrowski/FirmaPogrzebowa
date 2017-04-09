package mbud.hibernate.dao;

import java.util.List;

import mbud.hibernate.mapping.DomPogrzebowy;

@SuppressWarnings({ "unchecked", "deprecation" })
public class DomPogrzebowyDao extends AbstractDao<DomPogrzebowy> {

	@Override
	public List<DomPogrzebowy> findAll() {
		List<DomPogrzebowy> list = getSession().createQuery("from DomPogrzebowy").list();
		return list;
	}

	@Override
	public DomPogrzebowy findById(Integer id) {
		DomPogrzebowy dom = getSession().get(DomPogrzebowy.class, id);
		return dom;
	}

}
