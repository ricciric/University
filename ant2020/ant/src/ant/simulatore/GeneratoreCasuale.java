package ant.simulatore;

import static ant.costanti.CostantiSimulazione.DIMENSIONE;
import static ant.costanti.CostantiSimulazione.PROBABILITA_CIBO;
import static ant.costanti.CostantiSimulazione.PROBABILITA_FONTE_ESAURITA;
import static ant.costanti.CostantiSimulazione.PROBABILITA_FONTE_NUOVA;
import static ant.costanti.CostantiSimulazione.RAGGIO_FONTE;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import ant.Cibo;
import ant.Coordinate;
import ant.Direzione;
import ant.costanti.CostantiSimulazione;

public class GeneratoreCasuale {

	static final private Random random = new Random();

	private Set<Coordinate> fonti;
	
	public GeneratoreCasuale() {
		this.fonti = new HashSet<>();		
	}
	
	/**
	 * @return le coordinate di una posizione scelta a caso tra quelle
	 *         all'interno dell'ambiente di esecuzione
	 */
	static public Coordinate posizioneCasuale() {
		final int x = 1 + random.nextInt(DIMENSIONE-2);
		final int y = 1 + random.nextInt(DIMENSIONE-2);
		return new Coordinate(x,y);
	}

	/**
	 * Crea unita' di {@link Cibo} casualmente ma collocandole di modo
	 * che provengano vicino a delle "fonti" di cibo che nascono e si
	 * esauriscono con una probabilita' fissata in
	 * {@linkplain CostantiSimulazione}
	 * @return la nuova unita' di cibo appena creata
	 */
	public Cibo ciboCasuale() {
		if (siVerificaEventoDiProbabilita(PROBABILITA_FONTE_NUOVA)) {
			final Coordinate p = posizioneCasuale();
			this.fonti.add(p);
		}
		if (!this.fonti.isEmpty() && siVerificaEventoDiProbabilita(PROBABILITA_FONTE_ESAURITA)) {
			final Iterator<Coordinate> it = this.fonti.iterator();
			it.next();
			it.remove();
		}
		if (!this.fonti.isEmpty() && siVerificaEventoDiProbabilita(PROBABILITA_CIBO)) {

			Coordinate coordinateFonte = scegliFonteAcaso();
			
			for(int c=0; c<RAGGIO_FONTE; c++)
				coordinateFonte = coordinateFonte.trasla(Direzione.casuale());
			
			int x = Math.max(1, coordinateFonte.getX()); x = Math.min(x, DIMENSIONE-2);
			int y = Math.max(1, coordinateFonte.getY()); y = Math.min(y, DIMENSIONE-2);
			
			return new Cibo(new Coordinate(x,y));
		}
		return null;
	}

	private Coordinate scegliFonteAcaso() {
		int indiceFonteCasuale = random.nextInt(this.fonti.size());
		
		Iterator<Coordinate> it = this.fonti.iterator();
		for(int i=0;i<indiceFonteCasuale;i++) it.next();
		
		return it.next();
	}


	/**
	 * Metodo di utilita' per decidere se si verifica o meno un evento di probabilita' data
	 * secondo una distribuzione uniforme
	 * @param probabilita - la probabilita' dell'evento che si deve verificare o meno
	 * @return true se e solo se l'evento di data probabilita' si e' verificato
	 */
	static public boolean siVerificaEventoDiProbabilita(Double probabilita) {
		return ( random.nextDouble() < probabilita );
	}
	
	
}
