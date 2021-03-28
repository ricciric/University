package it.uniroma3.siw.model;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerTest {

	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static EntityTransaction tx;
	private static Customer customer;
	private static Address address;
	
	@BeforeAll
	public static void initializeEntityManagaer() {
		address = new Address();
		customer = new Customer();
		customer.setAddress(address);
		emf = Persistence.createEntityManagerFactory("products-unit-test");
		em = emf.createEntityManager();
		tx = em.getTransaction();
		tx.begin();
		em.persist(customer);
		em.persist(address);
		tx.commit();
	}
	
	@AfterAll
	public static void closeEntityManager() {
		em.close();
		emf.close();

	}

	@Test
	void testPersistanceAddress() {
		assertEquals(address.getId(),1, "l'address non è stato inserito nel DB poichè il suo ID è nullo");
	}

}
