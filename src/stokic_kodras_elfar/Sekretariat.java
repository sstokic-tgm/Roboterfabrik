package stokic_kodras_elfar;

import java.util.*;
import java.util.concurrent.*;

/**
 * Klasse die die Lieferanten, Lagermitarbeiter, Montuere und den Watchdog verwaltet/startet
 * 
 * @author Stokic Stefan
 * @version 1.1
 */
public class Sekretariat {

	private int idCount;
	private int lieferanten;
	private int montuere;
	private int laufzeit;
	private String lagerV;
	private String logsV;
	//private boolean isRunning;
	private ThreadPoolExecutor exLieferant, exMontuer, exWatchdog;
	private Lagermitarbeiter lagerMit;
	
	public Sekretariat(int lieferanten, int montuere, int laufzeit, String lagerV, String logsV){
		
		this.lieferanten = lieferanten;
		this.montuere = montuere;
		this.laufzeit = laufzeit;
		this.lagerV = lagerV;
		this.logsV = logsV;
		
		this.exLieferant = new ThreadPoolExecutor(lieferanten, lieferanten, laufzeit, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(lieferanten));
		this.exMontuer = new ThreadPoolExecutor(montuere, montuere, laufzeit, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(montuere));
		this.exWatchdog = new ThreadPoolExecutor(lieferanten + montuere, lieferanten + montuere, laufzeit, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(lieferanten + montuere));
		
		this.lagerMit = new Lagermitarbeiter(this.lagerV, this.logsV);

	}
	
	/**
	 * Die einzelnen Threadpools starten
	 */
	public void start(){
		
		ArrayList<Runnable> watchdogs = new ArrayList<Runnable>();
		watchdogs.add(new Watchdog(this.laufzeit + 1000, this.lagerMit, this.logsV));
		
		Thread tLagermMit = new Thread(this.lagerMit);
		tLagermMit.start();
		
		for(int i = 0; i < this.lieferanten; i++){
			
			Lieferanten lieferant = new Lieferanten(this.lagerMit, this.logsV);
			exLieferant.execute(lieferant);
			watchdogs.add(new Watchdog(this.laufzeit, lieferant, this.logsV));
			
		}
		
		this.idCount = 1;
		for(int i = 0; i < this.montuere; i++){
			
			Montuer montuer = new Montuer(this.idCount, this.lagerMit, this.logsV);
			exMontuer.execute(montuer);
			watchdogs.add(new Watchdog(this.laufzeit, montuer, this.logsV));
			this.idCount++;
			
		}
		
		for(int i = 0; i < watchdogs.size(); i++){
			
			exWatchdog.execute(watchdogs.get(i));
			
		}
        
		// Executer Service nach Beendigung der Threads schließen
		exLieferant.shutdown();
		exMontuer.shutdown();
		exWatchdog.shutdown();
		
	}
	
}
