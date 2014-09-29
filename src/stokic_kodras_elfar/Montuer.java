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
	//static logger damit nicht jeder Lieferant ein eigenes Logfile erstellt
	private final static Logger log = Logger.getLogger("Montuer");
	private static boolean hasFileHandler = false;
	
	public Montuer(int id, Lagermitarbeiter lagerMit, String logV){
		
		this.id = id;
		this.lagerMit = lagerMit;
		
		if(!hasFileHandler) {
			
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
				
				hasFileHandler = true;
				
			}catch(IOException ioe){
				
				JOptionPane.showMessageDialog(null, "IOException: " + ioe.getMessage());
			}
		}
	}
	
	@Override
	public void run(){
		
		sort();
	}
	
	@Override
	public void stop(){
		
		this.isRunning = false;
		log.log(Level.INFO, "Montuer beendet!");
	}
	
	private void sort(){
		int[] arm1Puffer = lagerMit.getArm1();
		int[] arm2Puffer = lagerMit.getArm2();
		int[] augeLPuffer = lagerMit.getAugeL();
		int[] augeRPuffer = lagerMit.getAugeR();
		int[] rumpfPuffer = lagerMit.getRumpf();
		int[] kantriebPuffer = lagerMit.getKettenantrieb();
		
		//Arm1
		int len = arm1Puffer.length;
		int j = 0;
		int tmp = 0;
		for(int i=0;i<len;i++){
		  j = i;
		  for(int k = i;k<len;k++){
		    if(arm1Puffer[j]>arm1Puffer[k]){
		      j = k;
		    }
		  }
		  tmp = arm1Puffer[i];
		  arm1Puffer[i] = arm1Puffer[j];
		  arm1Puffer[j] = tmp;
		}
		arm1 = arm1Puffer;
		
		//Arm2
		len = arm2Puffer.length;
		j = 0;
		tmp = 0;
		for(int i=0;i<len;i++){
		  j = i;
		  for(int k = i;k<len;k++){
		    if(arm2Puffer[j]>arm2Puffer[k]){
		      j = k;
		    }
		  }
		  tmp = arm2Puffer[i];
		  arm2Puffer[i] = arm2Puffer[j];
		  arm2Puffer[j] = tmp;
		}
		arm2 = arm1Puffer;
		
		//AugeL
		len = augeLPuffer.length;
		j = 0;
		tmp = 0;
		for(int i=0;i<len;i++){
		  j = i;
		  for(int k = i;k<len;k++){
		    if(augeLPuffer[j]>augeLPuffer[k]){
		      j = k;
		    }
		  }
		  tmp = augeLPuffer[i];
		  augeLPuffer[i] = augeLPuffer[j];
		  augeLPuffer[j] = tmp;
		}
		augeL = augeLPuffer;
		
		//AugeR
		len = augeRPuffer.length;
		j = 0;
		tmp = 0;
		for(int i=0;i<len;i++){
		  j = i;
		  for(int k = i;k<len;k++){
		    if(augeRPuffer[j]>augeRPuffer[k]){
		      j = k;
		    }
		  }
		  tmp = augeRPuffer[i];
		  augeRPuffer[i] = augeRPuffer[j];
		  augeRPuffer[j] = tmp;
		}
		augeR = augeRPuffer;
		
		//Rumpf
		len = rumpfPuffer.length;
		j = 0;
		tmp = 0;
		for(int i=0;i<len;i++){
		  j = i;
		  for(int k = i;k<len;k++){
		    if(rumpfPuffer[j]>rumpfPuffer[k]){
		      j = k;
		    }
		  }
		  tmp = rumpfPuffer[i];
		  rumpfPuffer[i] = rumpfPuffer[j];
		  rumpfPuffer[j] = tmp;
		}
		rumpf = rumpfPuffer;
		
		//Kettenantrieb
		len = kantriebPuffer.length;
		j = 0;
		tmp = 0;
		for(int i=0;i<len;i++){
		  j = i;
		  for(int k = i;k<len;k++){
		    if(kantriebPuffer[j]>kantriebPuffer[k]){
		      j = k;
		    }
		  }
		  tmp = kantriebPuffer[i];
		  kantriebPuffer[i] = kantriebPuffer[j];
		  kantriebPuffer[j] = tmp;
		}
		kettenantrieb = kantriebPuffer;
	}
}
