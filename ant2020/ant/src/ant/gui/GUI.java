package ant.gui;

import static ant.costanti.CostantiGUI.COLORE_BORDO;
import static ant.costanti.CostantiGUI.DIM_CELLE;
import static ant.costanti.CostantiGUI.DIM_FERORMONE;
import static ant.costanti.CostantiGUI.IMMAGINE_SEME;
import static ant.costanti.CostantiGUI.IMMAGINE_MATTONE;
import static ant.costanti.CostantiGUI.IMMAGINE_FORMICAIO;
import static ant.costanti.CostantiSimulazione.DIMENSIONE;
import static java.awt.Color.BLACK;
import static java.awt.Color.YELLOW;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ant.Ambiente;
import ant.Cibo;
import ant.Coordinate;
import ant.Formicaio;
import ant.formica.Aggressiva;
import ant.formica.Esploratrice;
import ant.formica.Formica;
import ant.formica.Inseguitrice;
import ant.simulatore.Simulatore;
import ant.simulatore.Statistiche;

public class GUI extends JPanel {

	static final private long serialVersionUID = 0L;

	final private Simulatore simulatore;

	final private JFrame jframe;

	GUI(final Simulatore simulatore) {
		this.simulatore = simulatore;
		this.jframe = new JFrame("ANT");		
		jframe.add(this);
		jframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
		jframe.setSize(DIMENSIONE*DIM_CELLE, DIMENSIONE*DIM_CELLE);
		jframe.setVisible(true);		
	}

	public void initControlliDaTastiera(final Simulatore simulatore) {

		/* Gestione eventi associati alla tastiera: 
		 * premi ESC per terminare la simulazione e stampare le statistiche */
		this.jframe.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==VK_ESCAPE) {
					final Ambiente ambiente = simulatore.getAmbiente();
					new Statistiche().stampaStatisticheFinali(ambiente);
					simulatore.terminaSimulazione();
				}
			}
		});
	}


	public void riportaNelTitolo(int passo, long attuali) {
		this.jframe.setTitle("Passo: "+passo+"\tLivello cibo="+attuali);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(BLACK);
		g.fillRect(0, 0, DIMENSIONE * DIM_CELLE, DIMENSIONE * DIM_CELLE);
		final Ambiente ambiente = this.simulatore.getAmbiente();
		this.disegnaOstacoli(g,ambiente.getOstacoli());
		g.setColor(YELLOW);

		/* DA CAMBIARE (VEDI DOMANDA 2) */
		for (Formica formica : this.simulatore.getFormiche()) {
			disegnaFormica(g, formica);
		}

		for (Cibo cibo : ambiente.getCibo()) {
			disegnaCibo(g, cibo.getPosizione());
		}

		for (Coordinate p : ambiente.getFerormone().keySet()) {
			final int livello = ambiente.getFerormone().get(p);
			disegnaFerormone(g, p.getX(), p.getY(), livello);
		}
		
		disegnaFormicaio(g);
	}

	private void disegnaCibo(Graphics g, Coordinate posizione) {
		disegnaImmagine(g, IMMAGINE_SEME, posizione);
	}

	private void disegnaFormicaio(Graphics g) {
		final Formicaio formicaio = this.simulatore.getAmbiente().getFormicaio();
		disegnaImmagine(g, IMMAGINE_FORMICAIO, formicaio.getPosizione(), 3.5f);
		g.setColor(BLACK);
	}


	private void disegnaFormica(Graphics g, Formica formica) {
        /* DA CAMBIARE ( VEDI DOMANDA 2 )*/        
        final Coordinate pos = formica.getPosizione();		
		final Image immagine = formica.getImmagine();
		final String ids = formica.toString();
		disegnaTesto(g, pos, ids);
		if (formica.ciboCaricato())
			disegnaCibo(g, pos);
		disegnaImmagine(g, immagine, pos);
	}

	private void disegnaTesto(Graphics g, Coordinate p, String testo) {
		final int x = p.getX();
		final int y = p.getY();
		int d = DIM_CELLE;
		int gx = x*d, gy = y*d;
        g.drawString(testo, gx-d/2, gy);
	}

	private void disegnaImmagine(Graphics g, Image image, Coordinate p) {
		final int x = p.getX();
		final int y = p.getY();
		int d = DIM_CELLE;
		int gx = x*d, gy = y*d;
		g.drawImage(image, gx, gy, d, d, null);		
	}

	private void disegnaImmagine(Graphics g, Image image, Coordinate p, float scala) {
		final int x = p.getX();
		final int y = p.getY();
		int d = DIM_CELLE;
		int gx = Math.round(x*d-d*(scala-1)/2), gy = Math.round(y*d-d*(scala-1)/2);
		int size = Math.round(d*scala);
		g.drawImage(image, gx, gy, size, size, null);		
	}

	private void disegnaOstacoli(Graphics g, Set<Coordinate> ostacoli) {
		for(Coordinate c : ostacoli) {
			disegnaOstacolo(g, c.getX(), c.getY(), COLORE_BORDO); 
		}
	}

	private void disegnaOstacolo(Graphics g, int x, int y, Color colore) {
		g.setColor(colore);
		disegnaImmagine(g, IMMAGINE_MATTONE, new Coordinate(x, y));
	}

	private void disegnaFerormone(Graphics g, int x, int y, int livello) {
		final Color colore = getColore(livello);
		g.setColor(colore);
		g.fillRect(x*DIM_CELLE+DIM_CELLE/2-DIM_FERORMONE, 
				   y*DIM_CELLE+DIM_CELLE/2-DIM_FERORMONE, 
				   DIM_FERORMONE, 
				   DIM_FERORMONE);
	}

	private Color getColore(float livello) {
		return Color.getHSBColor(0.5f , 0.5f, Math.min( (livello + 50 )/ 100 , 1) );
	}

}
