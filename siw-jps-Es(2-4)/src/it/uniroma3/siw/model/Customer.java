package it.uniroma3.siw.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"firstname","lastName","email"})) //permette di inserire un vincolo di unicità multiplo
																							  //tra questi valori tra virgolette
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}) /*evento a cascata, consiste nel caricamento di classi
	 																						combinate tra loro senza il bisogno di inserire all'interno
	 																	                     della transazione dell' EM anche la classe combinata
	 																	                     {MODELLAZIONE DI DOMINIO CLASSI COMBINATE} */
	private Address address;
	


	@OneToMany(mappedBy = "customer", fetch = FetchType.EAGER) //va solo qui il mappedBy perchè questa è la parte inversa
	private List<Ordine> ordini;

	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable = false)
	private String lastName;

	private String email;

	private String phoneNumber;

	@Column(nullable = false)
	private LocalDate dateOfBirth;

	@Column(nullable = false)
	private LocalDate registrationDate;

	public Customer() {
		this.ordini = new ArrayList<Ordine>();

	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
