package it.uniroma3.siw.model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductTest {
	
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static EntityTransaction tx;
	private static Product p;
	private static Product p1;

	@BeforeAll
	static void setUp() throws Exception{
		emf = Persistence.createEntityManagerFactory("uni");
		em = emf.createEntityManager();
		tx = em.getTransaction();
		p = new Product();
		p.setName("Palla");
		p.setCode("111");
		p1 = new Product();
		p1.setName("Pallone");
		p1.setCode("222");
	}
	
	@BeforeEach
	void tableSetUp() throws Exception{
		tx.begin();
		em.remove(p);
		em.remove(p1);
		tx.commit();
		tx.begin();
		em.merge(p); //inserito merge per passare dallo stato deatached --> persist
		em.merge(p1); //chiedere al prof. perchè persist non funziona
		tx.commit();
	}
	
	@AfterAll
	static void emClose() throws Exception{
		em.close();
		emf.close();
	}

	/*Cancella il contenuto della tabella rimuovendo i due prodotti e riportandolo in output sull' assertEquals*/
	@Test
	void testDynamicQuery() {
		Query deleteQuery = em.createQuery("DELETE FROM Product p"); //cancella il contenuto con JPQL
		tx.begin(); //avvio la transazione poichè modifico il DB 
		int deletedRows = deleteQuery.executeUpdate(); //eseguo l'eliminazione
		tx.commit();
		assertEquals("Non sono stati cancellati i due prodotti dalla tabella", deletedRows, 2);
	}
	
	@Test
	void testNamedQuery() {
		Query deleteQuery = em.createNamedQuery("deleteAllProducts");
		tx.begin();
		int deletedRows = deleteQuery.executeUpdate();
		tx.commit();
		assertEquals("Non sono stati eliminati i due prodotti dalla tabella", deletedRows, 2);
	}
	
	@Test 
	void testNamedQuery2() {
		Query deleteQuery = em.createNamedQuery("deleteAllProducts");
		tx.begin();
		int deletedRows = deleteQuery.executeUpdate();
		tx.commit();
		assertEquals("Non sono stati elminiati i due prodotti dalla tabella", deletedRows, 2);
	}

}
