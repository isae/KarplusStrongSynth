package common;

/**
 * Created by isae on 21.05.15.
 */
public class DistortionEffect implements Effect {
    private byte treshold;

    public DistortionEffect(byte treshold) {
        this.treshold = treshold;
    }

    public int getTreshold() {
        return treshold;
    }

    public void setTreshold(byte treshold) {
        this.treshold = treshold;
    }

    @Override
    public void process(Sample sample) {
        byte[] data = sample.getData();
        for (int i = 0; i < data.length; ++i) {
            if (Math.abs(data[i]) > treshold) {
                data[i] = (byte) (data[i] > 0 ? treshold : -treshold);
            }

        }

    }
}
