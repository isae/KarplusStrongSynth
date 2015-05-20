package common;

/**
 * Created by isae on 19.05.15.
 */
public class Sample {


    public final int rate;// = 16 * 1024; // ~16KHz
    public final int length;// = 2;
    private byte[] data;

    public Sample(int rate, int length) {
        this.length = length;
        this.rate = rate;
        this.data = new byte[rate * length];
    }



    public byte[] getData() {
        return data;
    }
}
