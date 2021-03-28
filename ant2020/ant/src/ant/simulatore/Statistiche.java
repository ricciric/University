package ant.simulatore;


import java.util.*;

import ant.Ambiente;
import ant.Cibo;
import ant.Formicaio;
import ant.formica.Formica;

public class Statistiche {

	synchronized public void stampaStatisticheFinali(Ambiente ambiente) {
		final Formicaio formicaio = ambiente.getFormicaio();

		final Set<Cibo> raccolto = formicaio.getCiboRaccolto();
		System.out.println("Totale cibo presente nel formicaio: " + raccolto.size());
		System.out.println();

		// (VEDI DOMANDA 3)
		System.out.println("Quantita' raccolta da ciascuna formica:");
		final Map<Formica, Integer> formica2quantita = raccoltoPerFormica(raccolto);
		stampaRaccoltoPerFormica(formica2quantita);
		System.out.println();

		// (VEDI DOMANDA 4)
		System.out.println("Quantita' di cibo raccolto per ciascuna tipologia di formica:");
		final Map<Class<? extends Formica>, Set<Cibo>> tipologia2cibo = raccoltoPerTipoDiFormica(raccolto);
		stampaRaccoltoPerTipoDiFormica(tipologia2cibo);
		System.out.println();

		// (VEDI DOMANDA 5)
		System.out.println("Classifica finale delle strategie di raccolta:");
		final List<Class<? extends Formica>> classificaTipo = ordinaStrategieDiRaccolta(tipologia2cibo);
		stampaClassificaStrategie(classificaTipo,tipologia2cibo);
		System.out.println();
	}


	/**
	 * <B>DA COMPLETARE (VEDI DOMANDA 3)</B>
	 * @param raccolto - insieme di unita' di cibo raccolte
	 * @return una mappa che conti per ogni formica quante unita' di cibo ha raccolto
	 */
	public Map<Formica, Integer> raccoltoPerFormica(Set<Cibo> raccolto) {
		Map<Formica, Integer> formica2raccolto = new HashMap<>();
		for(Cibo c: raccolto) {
			final Formica raccoglitrice = c.getRaccoglitrice();
			if (formica2raccolto.containsKey(raccoglitrice)) {
				Integer ciboTot =formica2raccolto.get(raccoglitrice);
				ciboTot++;
				formica2raccolto.put(raccoglitrice, ciboTot);
			} else {
				formica2raccolto.put(raccoglitrice, 1);
			}
		}
		// N.B. il tipo restituito e' migliorabile dopo aver svolto la domanda 2
		return formica2raccolto;

	}


	/**
	 *  <EM>N.B. UTILE PER STAMPARE RISULTATI DOMANDA 3</EM>
	 * @param formica2quantita
	 */
	private void stampaRaccoltoPerFormica(final Map<Formica, Integer> formica2quantita) {
		// N.B. il tipo del parametro e' migliorabile dopo aver svolto la domanda 2
		for(Object formica : formica2quantita.keySet()) {
			Integer quantita = formica2quantita.get(formica);
			if (quantita==null)
				quantita = 0;
			System.out.println("La formica "+formica+" ha raccolto "+quantita);
		}
	}

	/**
	 * <B>DA COMPLETARE (VEDI DOMANDA 4)</B>
	 * @param raccolto - l'insieme di unita' di cibo raccolte
	 * @return una mappa che riporta per ciascuna tipologia di formiche quante unita' di cibo ha raccolto
	 */
	public Map<Class<? extends Formica>, Set<Cibo>> raccoltoPerTipoDiFormica(Set<Cibo> raccolto) {
		Map<Class< ? extends Formica>, Set<Cibo>> tipoFormica2raccolto = new HashMap<>();
		for(Cibo c:raccolto) {
			if (tipoFormica2raccolto.containsKey(c.getRaccoglitrice().getClass())) {
				tipoFormica2raccolto.get(c.getRaccoglitrice().getClass()).add(c);
				
			} else {
				Set<Cibo> racc = new HashSet<>();
				racc.add(c);
				tipoFormica2raccolto.put(c.getRaccoglitrice().getClass(), racc);
			}
		}
		return tipoFormica2raccolto;
	}

	/**
	 *  <EM>N.B. UTILE PER STAMPARE RISULTATI DOMANDA 4</EM>
	 * @param tipologia2cibo
	 */
	private void stampaRaccoltoPerTipoDiFormica(final Map<Class<? extends Formica>, Set<Cibo>> tipologia2cibo) {
		if (tipologia2cibo==null) return;

		for(Class<?> tipo : tipologia2cibo.keySet()) {
			Set<Cibo> raccolto = tipologia2cibo.get(tipo);
			System.out.println("Le formiche di tipo "+tipo.getSimpleName()+" hanno raccolto "+raccolto.size());
		}
	}

	/**
	 * <B>DA COMPLETARE (VEDI DOMANDA 5)</B>
	 * @param tipologia2cibo una mappa che per ogni tipologia di formica riporta quante unita' ha raccolto
	 * @return una lista ordinata degli oggetti {@link Class} associati ai diversi tipi di {@link Formica}
	 */
	public List<Class<? extends Formica>> ordinaStrategieDiRaccolta(final Map<Class<? extends Formica>, Set<Cibo>> tipologia2cibo) {
		List<Class<? extends Formica>> classifica = new ArrayList<Class<? extends Formica>>(tipologia2cibo.keySet());
		Collections.sort(classifica, new Comparator<Class<? extends Formica>>() {

			@Override
			public int compare(Class<? extends Formica> o1, Class<? extends Formica> o2) {
				return o1.getClass().toString().compareTo(o2.getClass().toString());
			}
			
		});
		return classifica;
	}

	/**
	 * <EM>N.B. UTILE PER STAMPARE RISULTATI DOMANDA 5</EM>
	 * @param classificaTipo
	 * @param tipologia2cibo
	 */
	private void stampaClassificaStrategie(List<Class<? extends Formica>> classificaTipo, Map<Class<? extends Formica>, Set<Cibo>> tipologia2cibo) {
		if (classificaTipo==null) return;
		
		for(int i=1; i<classificaTipo.size()+1; i++) {
			final Class<?> tipo = classificaTipo.get(i-1);
			System.out.println(i+") "+tipo.getSimpleName()+" con "+tipologia2cibo.get(tipo).size()+" unita' di cibo");
		}
	}
}
