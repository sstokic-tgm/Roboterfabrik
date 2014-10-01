package stokic_kodras_elfar;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.*;


/**
 * Klasse welche neue Teile hinzufügt, löscht bzw. eine Klasse die die Teile verwaltet
 * Darunter versteht man ebenfalls, das Speichern/Loeschen der Teile in eine .csv Datei
 * 
 * @author Stokic Stefan
 * @version 1.6
 */
public class Lagermitarbeiter implements Runnable, Stoppable {

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

				log.log(Level.SEVERE, "IOException: " + ioe.getMessage());
			}
		}

		auge = new ConcurrentLinkedQueue<String>();
		arm = new ConcurrentLinkedQueue<String>();
		kettenantrieb = new ConcurrentLinkedQueue<String>();
		rumpf = new ConcurrentLinkedQueue<String>();
		tmp = new ConcurrentLinkedQueue<String>();

		this.isRunning = true;
	}

	@Override
	public void run(){

		while(this.isRunning) {

			apply();

			try {

				Thread.sleep(500);

			}catch(InterruptedException ie) {

				log.log(Level.SEVERE, "InterruptedException: " + ie.getMessage());
			}
		}
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

	/**
	 * Teil in das Lager zuruecklegen
	 * 
	 * @param parts
	 * @param part
	 */
	public void returnPart(RoboParts parts, String part) {

		tmp.remove(part);

		switch(parts) {

		case AUGE:

			auge.add(part);
			log.log(Level.INFO, part + " in das Lager zurueckgelegt!");
			break;

		case ARM:

			arm.add(part);
			log.log(Level.INFO, part + " in das Lager zurueckgelegt!");
			break;

		case KETTENANTRIEB:

			kettenantrieb.add(part);
			log.log(Level.INFO, part + " in das Lager zurueckgelegt!");
			break;

		case RUMPF:

			rumpf.add(part);
			log.log(Level.INFO, part + " in das Lager zurueckgelegt");
			break;
		}
	}

	/**
	 * Roboter parts in eine Datei schreiben
	 * 
	 * @param path
	 * @param data
	 * @return
	 */
	public boolean writeToFile(String path, ConcurrentLinkedQueue<String> data) {

		try {

			String out = "";

			File f = new File(path);
			if(!f.exists()){

				f.createNewFile();

			}else{

				f.delete();
			}

			FileWriter fw = new FileWriter(f.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);

			for(String lines : data) {

				lines += System.getProperty("line.seperator");
				out += lines;
			}

			bw.write(out);
			bw.close();

			return true;

		}catch(IOException ioe) {

			log.log(Level.SEVERE, "IOException: " + ioe.getMessage());
			return false;
		}
	}

	/**
	 * Roboter parts aus einer Datei lesen
	 * 
	 * @param path
	 * @return
	 */
	public LinkedList<String> loadFromFile(String path) {

		try {

			File f = new File(path);
			if(!f.exists()){

				f.createNewFile();

			}else{

				f.delete();
			}

			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);

			LinkedList<String> list = new LinkedList<String>();

			String lines = null;

			while((lines = br.readLine()) != null) {

				list.add(lines);
			}

			br.close();

			return list;

		}catch(IOException ioe) {

			log.log(Level.SEVERE, "IOException: " + ioe.getMessage());
			return null;
		}
	}

	/**
	 * Roboter parts in .csv Dateien schreiben
	 */
	private void apply() {

		writeToFile(this.lagerV + "/augen.csv", auge);
		writeToFile(this.lagerV + "/arm.csv", arm);
		writeToFile(this.lagerV + "/kettenantrieb.csv", kettenantrieb);
		writeToFile(this.lagerV + "/rumpf.csv", rumpf);
	}
}
