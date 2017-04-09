package mbud.hibernate.dao;

import java.util.List;

import mbud.hibernate.mapping.Klient;

@SuppressWarnings({ "unchecked", "deprecation" })
public class KlientDao extends AbstractDao<Klient> {

	@Override
	public List<Klient> findAll() {
		List<Klient> list = getSession().createQuery("select k from Klient k order by k.nazwisko, k.imie").list();
		return list;
	}

	@Override
	public Klient findById(Integer id) {
		Klient klient = getSession().get(Klient.class, id);
		return klient;
	}
	
	public Klient findByEmailPassword(String email, String password) {
		Klient klient = (Klient) getSession().createQuery("from Klient where email = \'" + email + "\' and haslo = \'" + password + "\'").uniqueResult();
		return klient;
	}
	
	public Klient findByEmail(String email) {
		Klient klient = (Klient) getSession().createQuery("from Klient where email = \'" + email + "\'").uniqueResult();
		return klient;
	}

}
