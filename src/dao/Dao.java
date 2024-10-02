package dao;
import jakarta.persistence.*;

import entity.*;
import java.util.*;

public class Dao {
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Converters");

	public void insertAccount(Account account) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(account);
		et.commit();
		em.close();
	}

	public List<Account> getAccounts(int number) {
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT a FROM Account a WHERE a.number = :number");
		query.setParameter("number", number);
		List<Account> accounts = query.getResultList();
		em.close();
		return accounts;
	}

	public void transfer(int from, int to, double amount) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Account accountFrom = em.find(Account.class, from);
		Account accountTo = em.find(Account.class, to);
		accountFrom.setBalance(accountFrom.getBalance() - amount);
		accountTo.setBalance(accountTo.getBalance() + amount);
		et.commit();
		em.close();
	}
	

	
}
