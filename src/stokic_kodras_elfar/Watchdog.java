package stokic_kodras_elfar;

import java.io.*;
import java.util.logging.*;

import javax.swing.JOptionPane;

/**
 * Watchdog Klasse, welcher die Threads 'überwacht'
 * 
 * @author Stokic Stefan
 * @version 1.1
 */
public class Watchdog implements Runnable {

	private Stoppable s;
	private int laufzeit;
	private String logV;

	private final static Logger log = Logger.getLogger("Watchdog");
	private static boolean hasFileHandler = false;

	public Watchdog(int laufzeit, Stoppable s, String logV){

		this.laufzeit = laufzeit;
		this.s = s;

		this.logV = logV;
		this.logV += "/watchdog.log";

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

				log.log(Level.INFO, "Watchdog logger gestartet!");

				hasFileHandler = true;

			}catch(IOException ioe){

				JOptionPane.showMessageDialog(null, "IOException: " + ioe.getMessage());
			}
		}
	}

	@Override
	public void run(){

		try{

			log.log(Level.INFO, "Thread sleep: " + this.laufzeit);
			Thread.sleep(this.laufzeit);


		}catch(InterruptedException e){

			log.log(Level.WARNING, "InterruptedException: " + e.getMessage());
		}

		this.s.stop();
		log.log(Level.INFO, "Thread stopped: " + this.s.getClass());
	}
}