package it.uniroma3.siw.model;

import java.util.List;

import javax.persistence.*;

@Entity
public class OrdineLine {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Product products;
	
	private float unitPrice;
	
	private int quantity;
	
	public OrdineLine() {
		// TODO Auto-generated constructor stub
	}

}
