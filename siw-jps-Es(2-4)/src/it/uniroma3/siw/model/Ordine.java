package it.uniroma3.siw.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Ordine {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<OrdineLine> ordineLine;
	
	@ManyToOne(fetch = FetchType.EAGER) //non ci va il mapped by perchè questa è la parte proprietaria e va solo nella parte inversa
	private Customer customer;
	
	@Column(nullable = false)
	private LocalDateTime creationTime;
	
	public Ordine() {
		this.ordineLine = new ArrayList<>();
	}

	public List<OrdineLine> getOrdineLine() {
		return ordineLine;
	}

	public void setOrdineLine(List<OrdineLine> ordineLine) {
		this.ordineLine = ordineLine;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
