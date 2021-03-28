package ant;


import static ant.costanti.CostantiSimulazione.DELTA_FERORMONE;
import static ant.costanti.CostantiSimulazione.DIMENSIONE;
import static ant.costanti.CostantiSimulazione.LUNGHEZZA_OSTACOLI;
import static ant.costanti.CostantiSimulazione.PERCENTUALE_OSTACOLI;

import static ant.simulatore.GeneratoreCasuale.posizioneCasuale;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * 
 */
public class Ambiente {

	final private int dimensione;

	final private Formicaio formicaio;

	final private Map<Coordinate,Cibo> pos2cibo;

	final private Map<Coordinate,Integer> pos2ferormone;
	
	final private Set<Coordinate> ostacoli;

	/**
	 * Crea un ambiente con le dimensioni di default
	 */
	public Ambiente() {
		this(DIMENSIONE);
	}

	/**
	 * Crea un'ambiente (quadrato) delle dimensioni date.
	 * @param dim - dimensione
	 */
	public Ambiente(int dim) {
		this.dimensione = dim;
		this.formicaio = creaFormicaio();
		this.pos2cibo = new HashMap<>();
		this.pos2ferormone = new HashMap<>();
		this.ostacoli = new HashSet<>();
		this.inizializzaBordi(dim);
		this.inizializzaOstacoli(dim);
	}

	private void inizializzaBordi(int dim) {
		for(int i=0; i<dim; i++) {
			aggiungiOstacolo(0,i);
			aggiungiOstacolo(i,0);
			aggiungiOstacolo(dim-1,i);
			aggiungiOstacolo(i,dim-1);
		}
	}

	private void inizializzaOstacoli(int dim) {
		final double n = (dim*(dim-4)+4)*PERCENTUALE_OSTACOLI/LUNGHEZZA_OSTACOLI;
		for(int i=0; i<Math.round(n); i++) {			
			Coordinate posizione = posizioneCasuale();
			for(int c=0; c<LUNGHEZZA_OSTACOLI; c++) {
				aggiungiOstacolo(posizione);
			    posizione = posizione.trasla(Direzione.casuale());
			}
		}
	}

	public void aggiungiOstacolo(int x, int y) {
		aggiungiOstacolo(new Coordinate(x, y));
	}

	public void aggiungiOstacolo(Coordinate c) {
		if (!c.equals(this.getFormicaio().getPosizione()))
			this.ostacoli.add(c);
	}

	public Set<Coordinate> getOstacoli() {
		return this.ostacoli;
	}
	
	private Formicaio creaFormicaio() {		
		Coordinate posizione = new Coordinate(this.dimensione/2, this.dimensione/2);
		return new Formicaio(posizione);
	}

	public Formicaio getFormicaio() {
		return this.formicaio;
	}

	public boolean suOstacolo(Coordinate pos) {
		return this.getOstacoli().contains(pos);
	}

	public void addCibo(Cibo cibo) {
		this.pos2cibo.put(cibo.getPosizione(), cibo);
	}

	public Collection<Cibo> getCibo() {
		return this.pos2cibo.values();
	}

	public Cibo getCibo(Coordinate posizione) {
		return this.pos2cibo.get(posizione);
	}

	public Cibo remove(Cibo cibo) {
		return this.pos2cibo.remove(cibo.getPosizione());
	}

	/**
	 * <B>( VEDI SUGGERIMENTO DOMANDA 2 )</B> <BR/>
	 * Restituisce la {@link Direzione} che conduce a cibo nelle
	 * immediate vicinanze rispetto ad una posizione di riferimento,
	 * <EM>null</EM> se non si trova nulla
	 * @param riferimento - la posizione da cui cercare
	 * @return direzione del cibo nelle immediate vicinanze
	 */
	public Direzione getDirezioneCiboVicino(Coordinate riferimento) {
		for(Direzione dir : Direzione.tutteAcaso()) {
			final Coordinate adiacente = riferimento.trasla(dir);
			final Cibo cibo = this.getCibo(adiacente);
			if (cibo!=null) {
				return dir;
			}
		}
		return null;
	}

	public Map<Coordinate, Integer> getFerormone() {
		return this.pos2ferormone;
	}

	public Integer getFerormone(Coordinate p) {
		return this.pos2ferormone.get(p);
	}

	/**
	 * Restituisce la {@link Direzione} che conduce ad una cella con
	 * ferormono nelle immediate vicinanze rispetto ad una posizione 
	 * di riferimento, <EM>null</EM> se non si trova nulla
	 * @param riferimento - la posizione da cui cercare
	 * @return direzione del ferormone nelle immediate vicinanze
	 */
	public Direzione getDirezioneFerormoneVicino(Coordinate riferimento) {
		for(Direzione dir : Direzione.tutteAcaso()) {
			final Coordinate adiacente = riferimento.trasla(dir);
			final Integer ferormone = this.getFerormone(adiacente);
			if (ferormone!=null) {
				return dir;
			}
		}
		return null;
	}

	public void incrementaFerormone(Coordinate p) {
		if (suOstacolo(p)) 
			throw new IllegalArgumentException("Il ferormone non puo' stare sul muro!");
		Integer livello = this.pos2ferormone.get(p);
		if (livello==null) livello = new Integer(0);
		livello = livello + DELTA_FERORMONE;
		this.pos2ferormone.put(p, livello);
	}

	public void dissipaFerormone() {
		final Set<Coordinate> daRimuovere = new HashSet<>();
		for(Coordinate p : this.pos2ferormone.keySet()) {
			int vecchioLivello = this.pos2ferormone.get(p);
			final int nuovoLivello = vecchioLivello-1;
			if (nuovoLivello<=0)
				daRimuovere.add(p);
			else
				this.pos2ferormone.put(p, nuovoLivello);
		}
		this.pos2ferormone.keySet().removeAll(daRimuovere);
	}

	/**
	 * Restituisce l'insieme degli oggetti {@link Direzione} che possono
	 * essere seguite a partire dalla posizione passata come riferimento,
	 * oppure l'insieme vuoto se nessuna direzione e' possibile.<BR/>
	 * <B> VEDI DOMANDA 6 </B>
	 * @param riferimento - la posizione di partenza
	 * @return l'insieme delle direzioni lecite (senza colpire ostacoli)
	 */
	public Set<Direzione> getPossibiliDirezioni(Coordinate riferimento) {
		/* seleziona solo direzioni verso posizioni adiacenti 
		 * al riferimento che non siano sul bordo */		
		final Set<Direzione> possibili = new HashSet<>();
		possibili.addAll(Direzione.TUTTE);
		final Iterator<Direzione> it = possibili.iterator();
		while (it.hasNext()) {
			final Coordinate destinazione = riferimento.trasla(it.next());
			if (this.suOstacolo(destinazione))
				it.remove();
		}
		return possibili;
		
	}
	
}
