package ant.costanti;


import static ant.costanti.LettoreImmagini.leggiImmagineRicolorata;
import static ant.costanti.LettoreImmagini.leggiImmagine;
import static java.awt.Color.*;

import java.awt.Color;
import java.awt.Image;

public interface CostantiGUI {


	// dimensione in px di una cella
	static final public int DIM_CELLE = 30;

	static final public int DIM_FERORMONE = 3;
	
	static final public Color COLORE_BORDO  = LIGHT_GRAY;

	static final public Color COLORE_CENTRO = RED;

	static public Image IMMAGINE_SEME = leggiImmagine("seme.png");

	static public Image IMMAGINE_MATTONE = leggiImmagine("mattone.png");

	static public Image IMMAGINE_FORMICAIO = leggiImmagine("formicaio.png");

	/* VEDI DOMANDA 7 */
	static public Image IMMAGINE_FORMICA_BIANCA = leggiImmagine("formica.png");

	static public Image IMMAGINE_FORMICA_GIALLA = leggiImmagineRicolorata("formica.png",WHITE,YELLOW);

	static public Image IMMAGINE_FORMICA_VERDE = leggiImmagineRicolorata("formica.png",WHITE,GREEN);

	/* N.B. rosso e' un colore libero per i successivi utilizzi: VEDI DOMANDA 2 */
	static public Image IMMAGINE_FORMICA_ROSSA = leggiImmagineRicolorata("formica.png",WHITE,RED);
	
}

