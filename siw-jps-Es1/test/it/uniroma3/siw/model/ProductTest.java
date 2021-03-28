package it.uniroma3.siw.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductTest {

	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static EntityTransaction tx;

	@BeforeAll
	public static void initEntityManager() throws Exception {
		emf = Persistence.createEntityManagerFactory("products-unit-test");
		em = emf.createEntityManager();
	}
	@AfterAll
	public static void closeEntityManager() throws SQLException {
		if (em != null) em.close();
		if (emf != null) emf.close();
	}

	@BeforeEach
	public void initTransaction() {
		tx = em.getTransaction();
	}

	@Test
	public void shouldCreateAproduct() throws Exception {
		Product product = new Product();
		product.setName("SLANGAN");
		product.setCode("4321423131-AA");
		tx.begin();
		em.persist(product);
		tx.commit();
		assertNotNull("ID should not be null", product.getId());
		List<Product> products = em.createNamedQuery("findAllProducts", Product.class).getResultList();
		assertEquals(1, products.size());
	}
}
