package stokic_kodras_elfar;


/**
 * Startklasse, die den CLIparser benutzt
 * 
 * Argumente:
 * lager	Pfad Lager-Verzeichniss, 
 * logs		Pfad Log-Verzeichniss
 * lieferanten		Lieferanten Anzahl
 * monteure			Monteure Anzahl
 * laufzeit			Laufzeit
 * 
 * @author Stokic Stefan
 * @version 1.0
 */
public class Roboterfabrik {
	
	public static void main(String[] args){
		
		CLIparser cli = new CLIparser(args);
		cli.parse();
	}
}
