package lepton;

import java.io.Serializable;

/**
 * Created by 270307 on 5/3/2017.
 */

public class tangoPacket implements Serializable {

    public byte[] barr;
    public float depth;

    public tangoPacket(byte[] barr,float depth){
        this.barr = barr;
        this.depth = depth;
    }

}
