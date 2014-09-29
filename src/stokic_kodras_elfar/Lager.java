package stokic_kodras_elfar;

import java.io.File;
import java.io.IOException;

public class Lager {
	private File auge;
	private File rumpf;
	private File kettenantrieb;
	private File arm;
	
	public Lager(){
		auge = new File("auge.txt");
		rumpf = new File("./lager/rumpf.txt");
		kettenantrieb = new File("./lager/kettenantrieb.txt");
		arm = new File("./lager/arm.txt");
		
		try {
		if(!auge.exists())	
			auge.createNewFile();
		if(!rumpf.exists());
			rumpf.createNewFile();
		if(!kettenantrieb.exists());
			kettenantrieb.createNewFile();
		if(!arm.exists());
			arm.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public File getAuge() {
		return auge;
	}

	public File getRumpf() {
		return rumpf;
	}


	public File getKettenantrieb() {
		return kettenantrieb;
	}


	public File getArm() {
		return arm;
	}

}
