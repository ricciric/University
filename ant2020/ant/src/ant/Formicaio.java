package ant;

import java.util.HashSet;
import java.util.Set;

import ant.formica.Esploratrice;
import ant.formica.Formica;
import ant.formica.Inseguitrice;

public class Formicaio {

	private Coordinate posizione;

	private Set<Cibo> magazzino;               // cibo raccolto

	public Formicaio(Coordinate posizione) {
		this.posizione  = posizione;
		this.magazzino = new HashSet<>();
	}
	
	public Coordinate getPosizione() {
		return this.posizione;
	}
	
	public void immagazzinaCaricoDi(Formica formica) {
		final Cibo carico = formica.scaricaCibo();
		if (carico!=null)
			this.magazzino.add(carico);
	}

	public Set<Cibo> getCiboRaccolto() {
		return this.magazzino;
	}
	
}
