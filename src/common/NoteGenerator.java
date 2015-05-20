package common;

/**
 * Created by isae on 19.05.15.
 */
public class NoteGenerator {
    private static NoteGenerator instance;

    private NoteGenerator() {
    }

    public static NoteGenerator getInstance() {
        if (instance == null) {
            instance = new NoteGenerator();

        }
        return instance;
    }

    public Sample getNote(double freq, int rate, int length) {
        Sample result = new Sample(rate, length);
        byte[] data = result.getData();
        for (int i = 0; i < data.length; i++) {
            double period = (double) result.rate / freq;
            double angle = 2.0 * Math.PI * i / period;
            data[i] = (byte) (Math.sin(angle) * 127f);
        }
        return result;
    }

    public Sample getQuet(int rate, int length) {
        return new Sample(rate, length);
    }
}
