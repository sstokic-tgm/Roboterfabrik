package stokic;

import org.apache.commons.cli.*;

/**
 * Kommandozeilen Argumente werden im GNU Style geparst.
 * 
 * @author Stokic Stefan
 * @version 1.1
 */
public class CLIparser {

	 private String[] args;
	 private Options options;
	 
	 public CLIparser(String[] args){
		 
	        this.args = args;
	        this.options = new Options();
	        
	        Option lager = OptionBuilder.withArgName("Verzeichniss")
	        		.hasArg()
	        		.withDescription("Pfad Lager-Verzeichniss")
	        		.create("lager");
	        Option logs = OptionBuilder.withArgName("Verzeichniss")
	        		.hasArg()
	        		.withDescription("Pfad Log-Verzeichniss")
	        		.create("logs");
	        Option lieferanten = OptionBuilder.withArgName("Lieferanten")
	        		.hasArg()
	        		.withDescription("Lieferanten Anzahl")
	        		.create("lieferanten");
	        Option monteure = OptionBuilder.withArgName("Monteure")
	        		.hasArg()
	        		.withDescription("Monteure Anzahl")
	        		.create("monteure");
	        Option laufzeit = OptionBuilder.withArgName("Laufzeit")
	        		.hasArg()
	        		.withDescription("Laufzeit")
	        		.create("laufzeit");
	        
	        this.options.addOption(lager);
	        this.options.addOption(logs);
	        this.options.addOption(lieferanten);
	        this.options.addOption(monteure);
	        this.options.addOption(laufzeit);
	        
	 }
	 
	 /**
	  * Methode die die Argumente parst und wenn sie korrekt sind, wird Sekretariat ausgeführt
	  */
	 public void parse(){
		 
		 GnuParser parser = new GnuParser();
		 
		 try{
			 
			 CommandLine line = parser.parse(this.options, this.args);
			 
			 if(line.hasOption("lager") && line.hasOption("logs") && line.hasOption("lieferanten") && line.hasOption("monteure") && line.hasOption("laufzeit")){
				 
				 String lagerVal = line.getOptionValue("lager");
				 String logsVal = line.getOptionValue("logs");
				 String lieferantenVal = line.getOptionValue("lieferanten");
				 String monteureVal = line.getOptionValue("monteure");
				 String laufzeitVal = line.getOptionValue("laufzeit");
				 
				 int lieferanten, monteure, laufzeit;
				 
				 try{
					 lieferanten = Integer.parseInt(lieferantenVal);
					 monteure = Integer.parseInt(monteureVal);
					 laufzeit = Integer.parseInt(laufzeitVal);
				 
				 }catch(Exception e){
					 
					 this.help();
					 return;
				 }
				 
				 if(lieferanten > 0 && monteure > 0 && laufzeit > 10){
					 
					 Sekretariat sek = new Sekretariat(lieferanten, monteure, laufzeit, lagerVal, logsVal);
					 sek.start();
				 
				 }else{
					
					 this.help();
				 }
				 
			 }else{
				 
				 this.help();
			 }
		 
		 }catch(ParseException e){
			 
			 this.help();
		 }
	 }
	 
	 /**
	  * Methode die eine Hilfestellung zeigt
	  */
	 public void help(){
		 
		 HelpFormatter hf = new HelpFormatter();
		 hf.printHelp("Roboterfabrik", this.options);
	 }
}
