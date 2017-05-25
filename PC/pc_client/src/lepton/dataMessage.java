package lepton;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class dataMessage implements Serializable {

	ImageIcon pii1,pii2,pii3,pii4;
	
	public dataMessage(ImageIcon pii1,ImageIcon pii2,ImageIcon pii3, ImageIcon pii4){
		this.pii1 = pii1;
		this.pii2 = pii2;
		this.pii3 = pii3;
		this.pii4 = pii4;
	}
	
}
