package lepton;

import java.io.Serializable;

public class controlMessage implements Serializable {

	public int speeda,speedb,speedc,speedd,camhor,camver,armhor,armver;
	
	public controlMessage(int speeda,int speedb,int speedc,int speedd,int camhor,int camver,int armhor,int armver){
		this.speeda = speeda; // speed motor a
		this.speedb = speedb; // speed motor b
		this.speedc = speedc; // speed motor c
		this.speedd = speedd; // speed motor d
		this.camhor = camhor; // camera horizontal
		this.camver = camver; // camera vertical
		this.armhor = armhor; // arm horizontal
		this.armver = armver; // arm vertical
	}
	
}
