package it.uniroma3.siw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity //ricordatelo sempre
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(nullable = false)
	private String street;
	@Column(nullable = false)
	private String city;
	@Column(nullable = false)
	private String state;
	@Column(nullable = false)
	private String zipCode;
	@Column(nullable = false)
	private String country;
	
	
	public Address() {
		// TODO Auto-generated constructor stub
	}
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}

}
