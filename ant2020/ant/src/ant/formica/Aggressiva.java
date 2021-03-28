package ant.formica;

import java.awt.Image;

import java.util.Set;

import ant.Ambiente;
import ant.Coordinate;
import ant.Direzione;

import static ant.costanti.CostantiGUI.IMMAGINE_FORMICA_ROSSA;
import static ant.costanti.CostantiSimulazione.PROBABILITA_CAMBIO_DIREZIONE;
import static ant.simulatore.GeneratoreCasuale.siVerificaEventoDiProbabilita;

public class Aggressiva extends Formica {

	static private int progId=0;
	
	final private int id;


	public Aggressiva(Ambiente ambiente) {
		super(ambiente);
		this.id = progId++;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public Direzione cambioDirezione(Set<Direzione> possibili) {
		final Direzione destinazioneCibo = this.getAmbiente().getDirezioneCiboVicino(getPosizione());
		if (destinazioneCibo != null) 
			return destinazioneCibo;
		else {
			final Set<Direzione> possibiliDirezioni = this.getAmbiente().getPossibiliDirezioni(getPosizione());
			return Direzione.scegliAcasoTra(possibiliDirezioni);
		}
	}
	
	@Override
	public Image getImmagine() {
		return IMMAGINE_FORMICA_ROSSA;
	}

	@Override
	public boolean decideDiCambiareDirezione() {
		return ( siVerificaEventoDiProbabilita(PROBABILITA_CAMBIO_DIREZIONE) ) ;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName()+getId();
	}

}
