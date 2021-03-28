package it.uniroma3.siw.main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import it.uniroma3.siw.model.Product;

public class ProductMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("products-unit");	//riprendo il file persistence.xml
		EntityManager em = emf.createEntityManager();
		Product product = new Product();
		product.setName("KRIDDIG");
		product.setPrice(3.5F);
		product.setDescription("A wonderful bla bla");
		product.setCode("pt000154");
		
		Product product1 = new Product();
		product1.setName("KRIDDIG1");
		product1.setPrice(3.51F);
		product1.setDescription("A wonderful bla bla1");
		product1.setCode("pt0001541");
		

		EntityTransaction tx = em.getTransaction(); //fa partire la transizione per la trascrizione sul DB
		tx.begin();
		em.persist(product);//rende persistente l'oggetto product
		em.persist(product1);//secondo oggetto da aggiungere al db
		tx.commit();

		em.close();
		emf.close();
	}
}