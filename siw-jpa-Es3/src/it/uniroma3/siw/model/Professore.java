package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Professore {
	/*
	 * professore(id, nome, cognome)
	 * create table Professore (
       id int8 not null,
        cognome varchar(255) not null,
        nome varchar(255) not null,
        primary key (id)
    )
	 * 
	 * Tabella JOIN che unisce i due (professore-Corso);
	 * professore_corso(professore_id, corso_id) professore_id -> professore.id,  corso_id -> corso.id
	 * create table Corso_Professore (
       membroDiCommissione_id int8 not null,
        commissione_id int8 not null
    )
	 * */
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String cognome;
	
	@ManyToMany(mappedBy = "commissione", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}) /*Evento a cascata: quando carico sul db Professore,
																								carico anche la classe Corso.class*/
	private List<Corso> membriCommissione;
	
	@OneToMany(mappedBy = "titolare", fetch = FetchType.LAZY)  // il fetch type sta ad indicare il tipo di caricamento previsto
	private List<Corso> corsi;
	
	public Professore() {
		this.corsi = new ArrayList<Corso>();
		this.membriCommissione = new ArrayList<Corso>();
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public List<Corso> getMembroDiCommissione() {
		return membriCommissione;
	}

	public void setMembroDiCommissione(List<Corso> membroDiCommissione) {
		this.membriCommissione = membroDiCommissione;
	}

	public List<Corso> getCorsi() {
		return corsi;
	}

	public void setCorsi(List<Corso> corsi) {
		this.corsi = corsi;
	}


}
