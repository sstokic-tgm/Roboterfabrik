package stokic_kodras_elfar;

import java.io.*;
import java.util.logging.*;

import javax.swing.JOptionPane;

/**
 * Klasse welche neue Teile hinzufügt, löscht bzw. eine Klasse die die Teile verwaltet
 * Darunter versteht man ebenfalls, das Speichern/Löschen der Teile in eine .csv Datei
 * 
 * @author Stokic Stefan
 * @version 1.0
 */
public class Lagermitarbeiter implements Runnable, Stoppable {

	private int id;
	private String logV;
	private String lagerV;
	private boolean isRunning;
	
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
	 * @param part das Teil
	 */
	public void addPart(String part) {
		
		
	}
	
	public int[] getAugeL(){
		
		return null;
	}
	
	public int[] getAugeR(){
		
		return null;
	}
	
	public int[] getKettenantrieb(){
		
		return null;
	}
	
	public int[] getRumpf(){
		
		return null;
	}
	
	public void setRoboter(){
		
		
	}
	
	public int getId(){
		
		return this.id;
	}
	
	public int[] getArm1(){
		
		return null;
	}
	
	public int[] getArm2(){
		
		return null;
	}
	
}
