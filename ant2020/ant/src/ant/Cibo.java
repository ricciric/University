package ant;

import ant.formica.Esploratrice;
import ant.formica.Formica;
import ant.formica.Inseguitrice;

public class Cibo {

	private Coordinate posizione;
	
	/* DA CAMBIARE: VEDI DOMANDA 2 */
	private Formica raccoglitriceFormica;
	
	public Cibo(Coordinate p) {
		this.posizione = p;
	}

	public Coordinate getPosizione() {
		return this.posizione;
	}
	
	public void setRaccoglitrice(Formica formica) {
		this.raccoglitriceFormica = formica;
	}

	public Formica getRaccoglitrice() {
		return this.raccoglitriceFormica;
	}

}
