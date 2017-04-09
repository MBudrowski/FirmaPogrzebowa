package mbud.hibernate.dao;

import java.util.List;

import mbud.hibernate.mapping.Pracownik;

public class PracownikDao extends AbstractDao<Pracownik> {

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<Pracownik> findAll() {
		List<Pracownik> list = getSession().createQuery("from Pracownik").list();
		return list;
	}

	@Override
	public Pracownik findById(Integer id) {
		Pracownik pracownik = getSession().get(Pracownik.class, id);
		return pracownik;
	}

	@SuppressWarnings("deprecation")
	public Pracownik findByLoginPassword(String login, String password) {
		Pracownik pracownik = (Pracownik) getSession().createQuery("from Pracownik where login = \'" + login + "\' and haslo = \'" + password + "\'").uniqueResult();
		return pracownik;
	}

}
