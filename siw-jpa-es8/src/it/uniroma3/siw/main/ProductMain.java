package it.uniroma3.siw.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import it.uniroma3.siw.model.Product;


public class ProductMain {

	public static void main(String args[]) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("products-unit");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Product p1 = new Product();
		Product p2 = new Product();
		Product p3 = new Product();
		Product p4 = new Product();
		Product p5 = new Product();
		p1.setPrice(10f);
		p2.setPrice(20f);
		p3.setPrice(30f);
		p4.setPrice(40f);
		p5.setPrice(50f);
		tx.begin();
		em.persist(p1);
		em.persist(p2);
		em.persist(p3);
		em.persist(p4);
		em.persist(p5);
		tx.commit();
		Scanner scan = new Scanner(System.in);//scanf con input float
		System.out.println("Inserisci un prezzo, io riporterò tutti i prodotti con prezzo inferiore:\n");
		if(scan.nextFloat() > 0f) 
			System.out.println(findProductsPriceLessThan(scan.nextFloat(), em).toString() + "\n\n");
		scan.close();
		em.close();
		emf.close();

	}

	//funzione per la ricerca dei prodotti con prezzo inferiore al prezzo riportato (NamedQuery)
	public static List<Product> findProductsPriceLessThan(float value, EntityManager em){
		TypedQuery<Product> selectQuery = em.createNamedQuery("findProductsPriceLessThan", Product.class);
		selectQuery.setParameter("value", value);
		List<Product> listProducts = selectQuery.getResultList();
		return listProducts;
	}

}
