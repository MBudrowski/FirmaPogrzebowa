package mbud.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import mbud.hibernate.util.SessionFactoryBuilder;

public abstract class AbstractDao<T> {

	private Session session;
	private Transaction transaction;
	
	public void openSession() {
		session = SessionFactoryBuilder.getSessionFactory().openSession();
	}
	
	public void closeSession() {
		session.close();
		session = null;
	}
	
	public void openSessionWithTransaction() {
		openSession();
		transaction = session.beginTransaction();
	}
	
	public void closeSessionWithTransaction() {
		transaction.commit();
		transaction = null;
		closeSession();
	}
	
	public void save(T obj) {
		openSessionWithTransaction();
		session.save(obj);
		closeSessionWithTransaction();
	}
	
	public void update(T obj) {
		openSessionWithTransaction();
		session.update(obj);
		closeSessionWithTransaction();
	}
	
	public void delete(T obj) {
		openSessionWithTransaction();
		session.delete(obj);
		closeSessionWithTransaction();
	}
	
	public abstract List<T> findAll();
	
	public abstract T findById(Integer id);
	
	protected Session getSession() {
		return session;
	}
	
	protected Transaction getTransaction() {
		return transaction;
	}
}
