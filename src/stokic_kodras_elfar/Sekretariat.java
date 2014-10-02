package stokic_kodras_elfar;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

/**
 * Klasse die die Lieferanten, Lagermitarbeiter, Montuere und den Watchdog verwaltet/startet
 * 
 * @author Stokic Stefan
 * @version 1.3
 */
public class Sekretariat {

	private int montuerId;
	private AtomicInteger roboterId;
	
	private int lieferanten;
	private int montuere;
	private int laufzeit;
	
	private String lagerV;
	private String logsV;
	
	private ThreadPoolExecutor exLieferant, exMontuer, exWatchdog;
	private Lagermitarbeiter lagerMit;

	public Sekretariat(int lieferanten, int montuere, int laufzeit, String lagerV, String logsV){

		this.roboterId = new AtomicInteger();
		
		this.lieferanten = lieferanten;
		this.montuere = montuere;
		this.laufzeit = laufzeit;
		this.lagerV = lagerV;
		this.logsV = logsV;

		this.exLieferant = new ThreadPoolExecutor(lieferanten, lieferanten, laufzeit, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(lieferanten));
		this.exMontuer = new ThreadPoolExecutor(montuere, montuere, laufzeit, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(montuere));
		this.exWatchdog = new ThreadPoolExecutor(lieferanten + montuere, lieferanten + montuere + 1, laufzeit, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(lieferanten + montuere));

		this.lagerMit = new Lagermitarbeiter(lagerV, logsV);
	}

	/**
	 * Die einzelnen Threadpools starten
	 */
	public void start(){

		ArrayList<Runnable> watchdogs = new ArrayList<Runnable>();
		watchdogs.add(new Watchdog(laufzeit + 1000, lagerMit, logsV));

		Thread tLagermMit = new Thread(lagerMit);
		tLagermMit.start();

		for(int i = 0; i < lieferanten; i++){

			Lieferanten lieferant = new Lieferanten(lagerMit, logsV);
			exLieferant.execute(lieferant);
			watchdogs.add(new Watchdog(laufzeit, lieferant, logsV));

		}

		montuerId = 1;
		for(int i = 0; i < montuere; i++){

			Montuer montuer = new Montuer(montuerId, lagerMit, lagerV, logsV, this);
			exMontuer.execute(montuer);
			watchdogs.add(new Watchdog(laufzeit, montuer, logsV));
			montuerId++;

		}

		for(int i = 0; i < watchdogs.size(); i++){

			exWatchdog.execute(watchdogs.get(i));

		}
		
		// Executer Service nach Beendigung der Threads schließen
		exLieferant.shutdown();
		exMontuer.shutdown();
		exWatchdog.shutdown();
	}
	
	/**
	 * Liefert eine Roboter-ID zurueck
	 * 
	 * @return eine Roboter-ID
	 */
	public int getRoboterId() {
		
		return roboterId.incrementAndGet();
	}
}
