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
	private Sekretariat sekr;
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
		/*for(int i = 0; i < 5; i++){
			File f = new File("C:/Users/Meta/Documents/Roboterfabrik/test"+i+".txt");
			System.out.println("test"+i);
			f.createNewFile();
			
			RandomAccessFile file = new RandomAccessFile("C:/Users/Meta/Documents/Roboterfabrik/test"+i+".txt", "rw");
			file.write(al.get(i));
			file.close();
		}
		*/
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
