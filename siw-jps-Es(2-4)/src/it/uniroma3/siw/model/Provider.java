package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Provider {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToMany(mappedBy = "providers", fetch = FetchType.LAZY)
	private List<Product> products;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Address address;
	
	@Column(nullable = false)
	private String name;
	
	private String phoneNumber;
	
	private String email;
	
	@Column(unique = true) //rende unica la partita IVA del fornitore
	private String vatin; //partita IVA
	
	public Provider() {
		this.products = new ArrayList<Product>();

		}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
