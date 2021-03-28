package it.uniroma3.siw.model;

import java.util.List;

import javax.annotation.Generated;
import javax.persistence.*;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Provider> providers;
	
	@Column(nullable = false)
	private String name;
	
	@Column(length = 2000)
	private String description;
	
	private float price;
	
	public Product() {
		// TODO Auto-generated constructor stub
	}

}
