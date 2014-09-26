package stokic_kodras_elfar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
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
 * @version 1.0
 */
public class Lieferanten implements Runnable, Stoppable {

	private int prodAnz;
	private Lagermitarbeiter lagerMit;
	private boolean isRunning;
	private String logV;
	
	private final static Logger log = Logger.getLogger("Lieferanten");
	private static boolean hasFileHandler = false;
	
	public Lieferanten(Lagermitarbeiter lagerMit, String logV){
		
		this.lagerMit = lagerMit;
		this.logV = logV;
		
		this.logV += "lieferanten.log";
		
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
				
				JOptionPane.showMessageDialog(null, "IOException: " + ioe.getMessage());
			}
		}
	}
	/*Pfad der Arugemnts muss verwendet werden werden
	 * 
	 */
	@Override
	public void run(){
		Random rm = new Random(18239);		
		int[] rndNum = new int[20];
		
		for(int i = 0; i < rndNum.length; i++){
			
			rndNum[i] = rm.nextInt(250);
			System.out.println(rndNum[i]);
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
	public static int[] createId(){
		
		Random rm = new Random(78160);		
		int[] id = new int[20];
		
		for(int i = 0; i < id.length; i++){
			
			id[i] = rm.nextInt(250);
		}
		
		return id;
	}
}
