package ant.costanti;

public class CostantiSimulazione {

	/**
	 * La {@link Ambiente} è un quadrato di queste dimensioni (incluso i bordi)
	 */
	static final public int DIMENSIONE = 30;

	/**
	 * Numero di formiche per ogni tipologia
	 */
	static final public int NUMERO_FORMICHE_PER_TIPOLOGIA = 3;

	/**
	 * Durata (in passi) totale della simulazione
	 */
	static final public int DURATA_SIMULAZIONE = 1000;

	/**
	 * Pausa (in millisecondi) tra due passi consecutivi della simulazione
	 */
	static final public int RITMO = 50; // millis
	
	/**
	 * Probabilita' di aggiungere cibo ad ogni passo
	 */
	static final public double PROBABILITA_CIBO = 0.15d;

	/**
	 * Probabilita' di accendere una nuova fonte di cibo ad ogni passo
	 */
	static final public double PROBABILITA_FONTE_NUOVA = 0.03d;

	/**
	 * Probabilita' di spegnere una fonte di cibo ad ogni passo
	 */
	static final public double PROBABILITA_FONTE_ESAURITA = 0.03d;
	
	/**
	 * Probabilita' di un cambio di direzione di una {@link Formica}
	 */
	static final public double PROBABILITA_CAMBIO_DIREZIONE = 0.20d;

	/**
	 * Percentuale di ostacoli nell'{@link Ambiente}
	 */
	static final public double PERCENTUALE_OSTACOLI = 0.06d;

	/**
	 * Lunghezza degli ostacoli nell'{@link Ambiente}
	 */
	static final public double LUNGHEZZA_OSTACOLI = 5;

	/**
	 * Raggio di una fonte di cibo
	 */
	static final public int RAGGIO_FONTE = DIMENSIONE / 10;
	
	/**
	 * Incremento ferormone da parte di formiche che trasportano cibo
	 */
	static final public int DELTA_FERORMONE = 50;

}
