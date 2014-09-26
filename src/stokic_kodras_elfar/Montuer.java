package stokic_kodras_elfar;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JOptionPane;

/**
 * Klasse die den Roboter zusammenbaut
 * 
 * @author Stokic Stefan
 * @version 1.0
 */
public class Montuer implements Runnable, Stoppable {

	private int id;
	private int[] augeL;
	private int[] augeR;
	private int[] kettenantrieb;
	private int[] rumpf;
	private int[] arm1;
	private int[] arm2;
	private Lagermitarbeiter lagerMit;
	private boolean isRunning;
	private String logV;
	
	private final static Logger log = Logger.getLogger("Montuer");
	
	public Montuer(int id, Lagermitarbeiter lagerMit, String logV){
		
		this.id = id;
		this.lagerMit = lagerMit;
		this.logV = logV;
		
		this.logV += "Montuer.log";
		
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
			
			log.log(Level.INFO, "Montuer logger gestartet!");
			
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
		log.log(Level.INFO, "Montuer beendet!");
	}
}
