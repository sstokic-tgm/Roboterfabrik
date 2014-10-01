package stokic_kodras_elfar;

import java.io.*;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 * Klasse die den Roboter zusammenbaut
 * 
 * @author Stokic Stefan, El-Far Matthias, Kodras Dominik
 * @version 2.0
 */
public class Montuer implements Runnable, Stoppable {

	private int montuerId;
	private int[] augeL;
	private int[] augeR;
	private int[] armL;
	private int[] armR;
	private int[] kettenantrieb;
	private int[] rumpf;
	
	private Lagermitarbeiter lagerMit;
	private Sekretariat sek;
	private String logV;
	private String lagerV;
	
	//static logger damit nicht jeder Lieferant ein eigenes Logfile erstellt
	private final static Logger log = Logger.getLogger("Montuer");
	private static boolean hasFileHandler = false;

	private static BufferedWriter bw;
	private static boolean isReadyToWrite;

	public Montuer(int montuerId, Lagermitarbeiter lagerMit, String lagerV, String logV, Sekretariat sek){

		this.montuerId = montuerId;
		this.lagerMit = lagerMit;
		this.sek = sek;
		this.lagerV = lagerV;

		if(!hasFileHandler) {

			this.logV = logV;		
			this.logV += "/Montuer.log";
			try{

				File f = new File(this.logV);
				if(!f.exists()){

					f.getParentFile().mkdirs();
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

				log.log(Level.SEVERE, "IOException: " + ioe.getMessage());
			}
		}
	}

	@Override
	public void run(){

		buildRobot();

		try {

			Thread.sleep(20);

		}catch(InterruptedException ie) {

			log.log(Level.SEVERE, "Montuer unterbrochen!");
		}
		//}
	}

	@Override
	public void stop(){

		log.log(Level.INFO, "Montuer beendet!");
	}

	/**
	 * Baut den Roboter
	 */
	private void buildRobot() {

		String[] augeL, augeR, armL, armR, kettenantrieb, rumpf;
		String tmpAugeL, tmpAugeR, tmpArmL, tmpArmR, tmpKettenantrieb, tmpRumpf;

		tmpAugeL = lagerMit.getPart(RoboParts.AUGE);
		log.log(Level.INFO, "Hole linkes Auge aus dem Lager!");
		if(tmpAugeL == null) {

			log.log(Level.WARNING, "Kein linkes Auge vorhanden!");
			return;
		}

		tmpAugeR = lagerMit.getPart(RoboParts.AUGE);
		log.log(Level.INFO, "Hole rechtes Auge aus dem Lager!");
		if(tmpAugeR == null) {

			log.log(Level.WARNING, "Kein rechtes Auge vorhanden, ich lege zurueck!");
			lagerMit.returnPart(RoboParts.AUGE, tmpAugeL);
			return;
		}

		tmpArmL = lagerMit.getPart(RoboParts.ARM);
		log.log(Level.INFO, "Hole linken Arm aus dem Lager!");
		if(tmpArmL == null) {

			log.log(Level.WARNING, "Kein linkes Arm vorhanden, ich lege zurueck!");
			lagerMit.returnPart(RoboParts.AUGE, tmpAugeL);
			lagerMit.returnPart(RoboParts.AUGE, tmpAugeR);
			return;
		}
		tmpArmR = lagerMit.getPart(RoboParts.ARM);
		log.log(Level.INFO, "Hole rechten Arm aus dem Lager!");
		if(tmpArmR == null) {

			log.log(Level.WARNING, "Kein rechter Arm vorhanden, ich lege zurueck!");
			lagerMit.returnPart(RoboParts.AUGE, tmpAugeL);
			lagerMit.returnPart(RoboParts.AUGE, tmpAugeR);
			lagerMit.returnPart(RoboParts.ARM, tmpArmL);
			return;
		}
		tmpRumpf = lagerMit.getPart(RoboParts.RUMPF);
		log.log(Level.INFO, "Hole Rumpf aus dem Lager!");
		if(tmpRumpf == null) {

			log.log(Level.WARNING, "Kein Rumpf vorhanden, ich lege zurueck!");
			lagerMit.returnPart(RoboParts.AUGE, tmpAugeL);
			lagerMit.returnPart(RoboParts.AUGE, tmpAugeR);
			lagerMit.returnPart(RoboParts.ARM, tmpArmL);
			lagerMit.returnPart(RoboParts.ARM, tmpArmR);
			return;
		}
		tmpKettenantrieb = lagerMit.getPart(RoboParts.KETTENANTRIEB);
		log.log(Level.INFO, "Hole Kettenantrieb aus dem Lager!");
		if(tmpKettenantrieb == null) {

			log.log(Level.WARNING, "Kein Kettenantrieb vorhanden, ich lege zurueck!");
			lagerMit.returnPart(RoboParts.AUGE, tmpAugeL);
			lagerMit.returnPart(RoboParts.AUGE, tmpAugeR);
			lagerMit.returnPart(RoboParts.ARM, tmpArmL);
			lagerMit.returnPart(RoboParts.ARM, tmpArmR);
			lagerMit.returnPart(RoboParts.RUMPF, tmpRumpf);
			return;
		}

		augeL = tmpAugeL.split(",");
		augeR = tmpAugeR.split(",");
		armL = tmpArmL.split(",");
		armR = tmpArmR.split(",");
		rumpf = tmpRumpf.split(",");
		kettenantrieb = tmpKettenantrieb.split(",");

		// dann fuer das sortierte vorbereiten
		this.augeL = new int[augeL.length - 1];
		this.augeR = new int[augeR.length - 1];
		this.armL = new int[armL.length - 1];
		this.armR = new int[armR.length - 1];
		this.rumpf = new int[rumpf.length - 1];
		this.kettenantrieb = new int[kettenantrieb.length - 1];

		// befuellen
		for(int i = 1; i < this.augeL.length; i++) {

			this.augeL[i] = Integer.parseInt(augeL[i]);
		}

		for(int i = 1; i < this.augeR.length; i++) {

			this.augeR[i] = Integer.parseInt(augeR[i]);
		}

		for(int i = 1; i < this.armL.length; i++) {

			this.armL[i] = Integer.parseInt(armL[i]);
		}

		for(int i = 1; i < this.armR.length; i++) {

			this.armR[i] = Integer.parseInt(armR[i]);
		}

		for(int i = 1; i < this.rumpf.length; i++) {

			this.rumpf[i] = Integer.parseInt(rumpf[i]);
		}

		for(int i = 1; i < this.kettenantrieb.length; i++) {

			this.kettenantrieb[i] = Integer.parseInt(kettenantrieb[i]);
		}


		// die teile arrays sorten
		Arrays.sort(this.augeL);
		Arrays.sort(this.augeR);
		Arrays.sort(this.armL);
		Arrays.sort(this.armR);
		Arrays.sort(this.rumpf);
		Arrays.sort(this.kettenantrieb);


		String out = "Threadee-ID" + sek.getRoboterId() + ",Mitarbeiter-ID" + this.montuerId + ",";
		String augeLfinal, augeRfinal, armLfinal, armRfinal, kettenantriebfinal, rumpffinal;

		augeLfinal = "Auge,";
		for(int i = 0; i < this.augeL.length; i++) {

			augeLfinal += this.augeL[i];
			if(i != (this.augeL.length - 1)) {

				augeLfinal += ",";
			}
		}

		augeRfinal = "Auge,";
		for(int i = 0; i < this.augeR.length; i++) {

			augeRfinal += this.augeR[i];
			if(i != (this.augeR.length - 1)) {

				augeRfinal += ",";
			}
		}

		armLfinal = "Arm,";
		for(int i = 0; i < this.armL.length; i++) {

			armLfinal += this.armL[i];
			if(i != (this.armL.length - 1)) {

				armLfinal += ",";
			}
		}

		armRfinal = "Arm,";
		for(int i = 0; i < this.armR.length; i++) {

			armRfinal += this.armR[i];
			if(i != (this.armR.length - 1)) {

				armRfinal += ",";
			}
		}

		rumpffinal = "Rumpf,";
		for(int i = 0; i < this.rumpf.length; i++) {

			rumpffinal += this.rumpf[i];
			if(i != (this.rumpf.length - 1)) {

				rumpffinal += ",";
			}
		}

		kettenantriebfinal = "Kettenantrieb,";
		for(int i = 0; i < this.kettenantrieb.length; i++) {

			kettenantriebfinal += this.kettenantrieb[i];
			if(i != (this.kettenantrieb.length - 1)) {

				kettenantriebfinal += ",";
			}
		}

		out += augeLfinal + ",";
		out += augeRfinal + ",";
		out += armLfinal + ",";
		out += armRfinal + ",";
		out += rumpffinal + ",";
		out += kettenantriebfinal;

		if(!writeRobotToFile(lagerV + "/auslieferung.csv", out)) {

			log.log(Level.SEVERE, "writeRobotToFile failed!");

		}else {

			log.log(Level.INFO, "Roboter erzeugt: " + out);
		}
	}

	/**
	 * Zum sicheren schreiben des fertigen Roboters in eine Datei. Da mehrere Threads auf eine Datei zugreifen wird diese Methode
	 * synchronized, um DeadLocks und andere Probleme zu vermeiden.
	 * 
	 * @param path der Pfad der Datei
	 * @param data Daten die geschrieben werden, in dem Fall der fertige Roboter
	 * @return falls erfolgreich oder nicht
	 */
	private synchronized boolean writeRobotToFile(String path, String data) {

		try {

			File f = new File(path);
			if(!f.exists()) {

				f.getParentFile().mkdirs();
				f.createNewFile();
			}

			FileWriter fw = new FileWriter(f.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);

			isReadyToWrite = true;

		}catch(IOException ioe) {

			isReadyToWrite = false;
		}

		if(!isReadyToWrite) {

			return false;
		}

		try {

			bw.write(data + System.getProperty("line.seperator"));
			bw.flush();

			return true;

		}catch(IOException ioe) {

			return false;
		}
	}
}
