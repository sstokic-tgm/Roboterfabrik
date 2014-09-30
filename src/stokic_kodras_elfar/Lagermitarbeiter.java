package stokic_kodras_elfar;

import java.io.*;
import java.util.concurrent.*;
import java.util.logging.*;

import javax.swing.JOptionPane;

/**
 * Klasse welche neue Teile hinzufügt, löscht bzw. eine Klasse die die Teile verwaltet
 * Darunter versteht man ebenfalls, das Speichern/Loeschen der Teile in eine .csv Datei
 * 
 * @author Stokic Stefan
 * @version 1.0
 */
public class Lagermitarbeiter implements Runnable, Stoppable {

	private int id;
	private String logV;
	private String lagerV;
	private boolean isRunning;

	private ConcurrentLinkedQueue<String> auge;
	private ConcurrentLinkedQueue<String> arm;
	private ConcurrentLinkedQueue<String> kettenantrieb;
	private ConcurrentLinkedQueue<String> rumpf;

	//Fuer rausgenommene Teile
	private ConcurrentLinkedQueue<String> tmp;

	//static logger damit nicht jeder Lieferant ein eigenes Logfile erstellt
	private final static Logger log = Logger.getLogger("Lagermitarbeiter");
	private static boolean hasFileHandler = false;

	public Lagermitarbeiter(String lagerV, String logV){

		this.logV = logV;
		this.lagerV = lagerV;

		this.logV += "lagermitarbeiter.log";


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

				log.log(Level.INFO, "Lagermitarbeiter logger gestartet!");

				hasFileHandler = true;

			}catch(IOException ioe){

				JOptionPane.showMessageDialog(null, "IOException: " + ioe.getMessage());
			}
		}

		this.auge = new ConcurrentLinkedQueue<String>();
		this.arm = new ConcurrentLinkedQueue<String>();
		this.kettenantrieb = new ConcurrentLinkedQueue<String>();
		this.rumpf = new ConcurrentLinkedQueue<String>();
		this.tmp = new ConcurrentLinkedQueue<String>();
	}

	@Override
	public void run(){

	}

	@Override
	public void stop(){

		this.isRunning = false;
		log.log(Level.INFO, "Lagermitarbeiter beendet!");
	}

	/**
	 * Fuegt ein neues Teil hinzu
	 * 
	 * @param parts
	 * @param part
	 */
	public void addPart(RoboParts parts, String part) {

		switch(parts) {

		case AUGE:

			log.log(Level.INFO, "Neues Auge hinzufuegt: " + part);
			auge.add(part);
			break;

		case ARM:

			log.log(Level.INFO, "Neuer Arm hinzufuegt: " + part);
			arm.add(part);
			break;

		case KETTENANTRIEB:

			log.log(Level.INFO, "Neues Kettenantrieb hinzufuegt: " + part);
			kettenantrieb.add(part);
			break;

		case RUMPF:

			log.log(Level.INFO, "Neues Rumpf hinzufuegt: " + part);
			rumpf.add(part);
			break;
		}
	}

	/**
	 * Holt sich ein Teil
	 * 
	 * @param parts
	 * @return
	 */
	public String getPart(RoboParts parts) {

		String part;

		switch(parts) {

		case AUGE:

			part = auge.poll();

			if(part != null)
				tmp.add(part);

			log.log(Level.INFO, part + " aus dem Lager geholt!");
			auge.add(part);
			break;

		case ARM:

			part = arm.poll();

			if(part != null)
				tmp.add(part);

			log.log(Level.INFO, part + " aus dem Lager geholt!");
			arm.add(part);
			break;

		case KETTENANTRIEB:


			part = kettenantrieb.poll();

			if(part != null)
				tmp.add(part);

			log.log(Level.INFO, part + " aus dem Lager geholt!");
			kettenantrieb.add(part);
			break;

		case RUMPF:

			part = rumpf.poll();

			if(part != null)
				tmp.add(part);

			log.log(Level.INFO, part + " aus dem Lager geholt!");
			rumpf.add(part);
			break;

		default:

			part = null;
			break;
		}

		return part;
	}
	
	/**
	 * Teil aus dem Lager entfernen
	 * 
	 * @param part
	 */
	public void deletePart(String part) {
		
		tmp.remove(part);
		log.log(Level.INFO, part + " aus dem Lager entfernt!");
	}
}
