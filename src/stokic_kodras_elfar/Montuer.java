package stokic_kodras_elfar;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JOptionPane;

/**
 * Klasse die den Roboter zusammenbaut
 * 
 * @author Stokic Stefan
 * @version 1.6
 */
public class Montuer implements Runnable, Stoppable {

	private int id;
	private int[] augeL;
	private int[] augeR;
	private int[] armL;
	private int[] armR;
	private int[] kettenantrieb;
	private int[] rumpf;
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

				log.log(Level.SEVERE, "IOException: " + ioe.getMessage());
			}
		}
	}

	@Override
	public void run(){
		
		buildRobot();
	}

	@Override
	public void stop(){

		this.isRunning = false;
		log.log(Level.INFO, "Montuer beendet!");
	}

	private void buildRobot() {

		// wird spaeter befüllt bsp.: augeL = tmpAugeL.split(",");
		/**
		 * ACHTUNG!!!!!!!!!!!! augeL != this.augeL
		 * this.augeL ist das sortierte
		*/
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


		// usw. wird immer um das vorherige groesser


		// wenn fertig mit jedem Teil dann =>
		augeL = tmpAugeL.split(",");
		augeR = tmpAugeR.split(",");
		armL = tmpArmL.split(",");
		armR = tmpArmR.split(",");
		rumpf = tmpRumpf.split(",");
		kettenantrieb = tmpKettenantrieb.split(",");
		 
		// usw...

		// dann fuer das sortierte vorbereiten
		this.augeL = new int[augeL.length - 1];
		this.augeR = new int[augeR.length - 1];
		this.armL = new int[armL.length - 1];
		this.armR = new int[armR.length - 1];
		this.rumpf = new int[rumpf.length - 1];
		this.kettenantrieb = new int[kettenantrieb.length - 1];
		
		// usw...

		// befuellen
		for(int i = 0; i < this.augeL.length; i++) {

			this.augeL[i] = Integer.parseInt(augeL[i]);
		}

		for(int i = 0; i < this.augeR.length; i++) {

			this.augeR[i] = Integer.parseInt(augeR[i]);
		}

		for(int i = 0; i < this.armL.length; i++) {

			this.armL[i] = Integer.parseInt(armL[i]);
		}
		
		for(int i = 0; i < this.armR.length; i++) {

			this.armR[i] = Integer.parseInt(armR[i]);
		}
		
		for(int i = 0; i < this.rumpf.length; i++) {

			this.rumpf[i] = Integer.parseInt(rumpf[i]);
		}

		for(int i = 0; i < this.kettenantrieb.length; i++) {

			this.kettenantrieb[i] = Integer.parseInt(kettenantrieb[i]);
		}
		// usw...
		
		// die teile arrays sorten
		Arrays.sort(this.augeL);
		Arrays.sort(this.augeR);
		Arrays.sort(this.armL);
		Arrays.sort(this.armR);
		Arrays.sort(this.rumpf);
		Arrays.sort(this.kettenantrieb);
		
		// usw...
		
		String out = "Threadee-ID" + /* threadee-id */ ",Mitarbeiter-ID" + this.id + ",";
		String augeLfinal, augeRfinal, armLfinal, armRfinal, kettenantriebfinal, rumpffinal;
		
		augeLfinal = "Auge,";
		for(int i = 0; i < this.augeL.length; i++) {
			
			augeLfinal += this.augeL[i];
			if(i != (this.augeL.length - 1)) {
				
				augeLfinal += ",";
			}
		}
		
		augeRfinal = "Auge,";
		for(int i = 0; i < this.augeL.length; i++) {
			
			augeRfinal += this.augeL[i];
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
		// usw...
		
		out += augeLfinal + ",";
		out += augeRfinal + ",";
		out += armLfinal + ",";
		out += armRfinal + ",";
		out += rumpffinal + ",";
		out += kettenantrieb;
		
		// usw...
		// beim letzten element braucht man kein "," extra
		
		/**
		 * jetzt wird in die Datei geschrieben, sprich der Roboter
		 * es wird hoechstwahrscheinlich eine schreib methode gebraucht, da hier das schreiben mit dem synchronized versehen
		 * werden muss
		 * 
		 * pseudo-code:
		 * if(!writeRobotToFile(out)) {
		 * 		log.log(Level.SEVERE, "Failed writing");
		 * }
		 * log.log(Level.INFO, "Neuer Roboter erstellt: " + out);
		 */
	}
}
