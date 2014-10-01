package stokic_kodras_elfar;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JOptionPane;

/**
 * Eine Klasse die neue Teile anliefert
 * 
 * @author Stokic Stefan
 * @version 1.6
 */
public class Lieferanten implements Runnable, Stoppable {

	private Lagermitarbeiter lagerMit;
	private boolean isRunning;
	private String logV;
	private Random rm;


	private final static Logger log = Logger.getLogger("Lieferanten");
	//static logger damit nicht jeder Lieferant ein eigenes Logfile erstellt
	private static boolean hasFileHandler = false;

	public Lieferanten(Lagermitarbeiter lagerMit, String logV){

		this.lagerMit = lagerMit;
		this.logV = logV;
		rm = new Random();

		this.logV += "lieferanten.log";

		this.isRunning = true;

		if(!hasFileHandler) {

			try{

				File f = new File(this.logV);
				if(!f.exists()){

					f.createNewFile();

				}else{

					f.delete();
				}

				FileHandler fh = new FileHandler(this.logV);
				SimpleFormatter sf = new SimpleFormatter();

				fh.setFormatter(sf);
				log.addHandler(fh);

				log.log(Level.INFO, "Lieferanten logger gestartet!");

				hasFileHandler = true;

			}catch(IOException ioe){

				log.log(Level.SEVERE, "IOException: " + ioe.getMessage());
			}
		}
	}

	@Override
	public void run(){

		while(this.isRunning) {

			switch(rm.nextInt(4) + 1) {

			case 1:

				this.lagerMit.addPart(RoboParts.AUGE, this.createId("Auge"));
				log.log(Level.INFO, "Neues Auge geliefert!");

			case 2:

				this.lagerMit.addPart(RoboParts.ARM, this.createId("Arm"));
				log.log(Level.INFO, "Neuer Arm geliefert!");

			case 3:

				this.lagerMit.addPart(RoboParts.KETTENANTRIEB, this.createId("Kettenantrieb"));
				log.log(Level.INFO, "Neues Kettenantrieb geliefert!");

			case 4:

				this.lagerMit.addPart(RoboParts.RUMPF, this.createId("Rumpf"));
				log.log(Level.INFO, "Neues Rumpf geliefert!");
			}

			try {

				Thread.sleep(20);

			}catch(InterruptedException e) {

				log.log(Level.INFO, "Lieferant unterbrochen!");
			}
		}
	}

	@Override
	public void stop(){

		this.isRunning = false;
		log.log(Level.INFO, "Lieferanten beendet!");
	}

	/**
	 * Erstellt Pseudo random IDs
	 * @return random ID-array
	 */
	public String createId(String part){

		String buffer = part + ",";
		int[] id = new int[20];

		for(int i = 0; i < id.length; i++){

			if(i != id.length - 1) {

				buffer = buffer + rm.nextInt(250) + ",";
			}else {

				buffer = buffer + rm.nextInt(250);
			}
		}

		return buffer;
	}

}
