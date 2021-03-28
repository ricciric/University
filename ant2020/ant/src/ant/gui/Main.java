package ant.gui;

import ant.simulatore.Simulatore;


public class Main {

	public static void main(String[] args) throws Exception {

		final Simulatore simulatore = new Simulatore();
		final GUI gui = new GUI(simulatore);
		gui.initControlliDaTastiera(simulatore);
		simulatore.setGUI(gui);
		simulatore.simula();
	}

}
