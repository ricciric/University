package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Corso { 
/*
 * Corso(id, denominazione, titolare) FK: titolare -> professore.id
 * 
 *     create table Corso (
       id int8 not null,
        denominazione varchar(255) not null,
        titolare_id int8,
        primary key (id)
    )
 * 
 * */

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false)
	private String denominazione;
	
	@ManyToMany
	private List<Professore> commissione;
	
	@ManyToOne
	private Professore titolare;
	
	public Corso() {
		this.commissione = new ArrayList<Professore>();
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public List<Professore> getCommissione() {
		return commissione;
	}

	public void setCommissione(List<Professore> commissione) {
		this.commissione = commissione;
	}

	public Professore getProfessore() {
		return titolare;
	}

	public void setProfessore(Professore professore) {
		this.titolare = professore;
	}
	

}
