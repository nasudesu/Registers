package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import entity.*;
import java.util.*;

public class Dao {
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Harj1PU");

	
	public void addRegister(Register reg) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
        em.persist(reg);
        
        em.getTransaction().commit();
        em.close();
	}
	
	public void addEvent(int eventNumber, int regNumber, double amount) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Register reg = em.find(Register.class, regNumber);
        SalesEvent evt = new SalesEvent(eventNumber, reg, amount);
		em.persist(evt);
        em.getTransaction().commit();
        em.close();	
	}

	public void retrieveEvents() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		String query = "SELECT s FROM SalesEvent s";
		Query q = em.createQuery(query);
		List<SalesEvent> result = q.getResultList();
		em.getTransaction().commit();
		em.close();
		for (SalesEvent s : result) {
			System.out.println(s);
		}
	}

	public void serviceFee(double fee) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		String query = "UPDATE SalesEvent s SET s.amount = s.amount - (s.amount * :fee)";
		Query q = em.createQuery(query);
		q.setParameter("fee", fee);
		q.executeUpdate();
		em.getTransaction().commit();
		em.close();
	}

	public void retrieveEventsCriteria() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<SalesEvent> cq = cb.createQuery(SalesEvent.class);
		Root<SalesEvent> root = cq.from(SalesEvent.class);
		cq.select(root);
		TypedQuery<SalesEvent> tq = em.createQuery(cq);
		List<SalesEvent> result = tq.getResultList();
		em.getTransaction().commit();
		em.close();
		for (SalesEvent s : result) {
			System.out.println(s);
		}
	}

	public void serviceFeeCriteria(double fee) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaUpdate<SalesEvent> cu = cb.createCriteriaUpdate(SalesEvent.class);
		Root<SalesEvent> root = cu.from(SalesEvent.class);
		cu.set(root.get("amount"), Optional.ofNullable(cb.diff(root.get("amount"), cb.prod(root.get("amount"), fee))));
		Query q = em.createQuery(cu);
		q.executeUpdate();
		em.getTransaction().commit();
		em.close();
	}

	public List<SalesEvent> retrieveSmallSales(double limit) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		String query = "SELECT s FROM SalesEvent s WHERE amount <:limit";
		Query q = em.createQuery(query);
		q.setParameter("limit", limit);
		List<SalesEvent> result = q.getResultList();
		em.getTransaction().commit();
		em.close();
		return result;
	}

	public List<SalesEvent> retrieveSmallSalesCriteria(double limit) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<SalesEvent> cq = cb.createQuery(SalesEvent.class);
		Root<SalesEvent> root = cq.from(SalesEvent.class);
		cq.select(root).where(cb.lt(root.get("amount"), limit));
		TypedQuery<SalesEvent> tq = em.createQuery(cq);
		List<SalesEvent> result = tq.getResultList();
		em.getTransaction().commit();
		em.close();
		return result;
	}

	public void deleteAllEvents() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		String query = "DELETE FROM SalesEvent";
		Query q = em.createQuery(query);
		q.executeUpdate();
		em.getTransaction().commit();
		em.close();
	}

	public void deleteAllEventsCriteria() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaDelete<SalesEvent> cd = cb.createCriteriaDelete(SalesEvent.class);
		Query q = em.createQuery(cd);
		q.executeUpdate();
		em.getTransaction().commit();
		em.close();
	}
	
}
