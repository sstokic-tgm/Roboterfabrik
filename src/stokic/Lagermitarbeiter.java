package stokic;

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
	private Sekretariat sekr;
	private boolean isRunning;
	
	private final static Logger log = Logger.getLogger("Lagermitarbeiter");
	
	public Lagermitarbeiter(String lagerV, String logV){
		
		this.logV = logV;
		this.lagerV = lagerV;
		
		this.logV += "lagermitarbeiter.log";
		
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
			
		}catch(IOException ioe){
			
			JOptionPane.showMessageDialog(null, "IOException: " + ioe.getMessage());
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
	
	
	public int getAugeL(){
		
		return 1;
	}
	
	public int getAugeR(){
		
		return 1;
	}
	
	public int getKettenantrieb(){
		
		return 1;
	}
	
	public int getRumpf(){
		
		return 1;
	}
	
	public void setRoboter(){
		
		
	}
	
	public int getId(){
		
		return this.id;
	}
	
	public int getArm1(){
		
		return 1;
	}
	
	public int getArm2(){
		
		return 1;
	}
	
}
