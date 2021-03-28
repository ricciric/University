package it.uniroma3.siw.main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ProductMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("uni");
		EntityManager em = emf.createEntityManager();
		em.close();
		emf.close();
	}
}