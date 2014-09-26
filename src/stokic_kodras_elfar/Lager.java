package stokic_kodras_elfar;

import java.io.File;
import java.io.IOException;

public class Lager {
	private File auge;
	private File rumpf;
	private File kettenantrieb;
	private File arm;
	
	public Lager(){
		auge = new File("C:/Users/Meta/Dropbox/Schule/4AHITT/SEW/EclipseWorkspace/Roboterfabrik/lager/auge.txt");
		rumpf = new File("C:/Users/Meta/Dropbox/Schule/4AHITT/SEW/EclipseWorkspace/Roboterfabrik/lager/rumpf.txt");
		kettenantrieb = new File("C:/Users/Meta/Dropbox/Schule/4AHITT/SEW/EclipseWorkspace/Roboterfabrik/lager/kettenantrieb.txt");
		arm = new File("C:/Users/Meta/Dropbox/Schule/4AHITT/SEW/EclipseWorkspace/Roboterfabrik/lager/arm.txt");
		
		try {
			auge.createNewFile();
			rumpf.createNewFile();
			kettenantrieb.createNewFile();
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
