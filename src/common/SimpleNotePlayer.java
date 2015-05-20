package common;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by isae on 19.05.15.
 */
public class SimpleNotePlayer implements NotePlayer {
    private static final int DEFAULT_RATE = 16 * 1024;
    private static final int DEFAULT_LENGTH = 1;
    private static SimpleNotePlayer instance;
    private final Sample quiet = NoteGenerator.getInstance().getQuet(DEFAULT_RATE, DEFAULT_LENGTH);

    private final AudioFormat audioFormat;
    private SourceDataLine line = null;

    private List<Effect> effects = new ArrayList<>();

    private SimpleNotePlayer() {
        audioFormat = new AudioFormat(DEFAULT_RATE, 8, 1, true, true);

        try {
            line = AudioSystem.getSourceDataLine(audioFormat);
            line.open(audioFormat, DEFAULT_RATE);
            line.start();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void addEffect(Effect effect){
        effects.add(effect);
    }

    public static SimpleNotePlayer getInstance() {
        if (instance == null) {
            instance = new SimpleNotePlayer();
        }
        return instance;
    }

    private void play(Sample note, int ms) {
        ms = Math.min(ms, note.length * 1000);
        int length = note.rate * ms / 1000;
        int count = line.write(note.getData(), 0, length);
    }

    @Override
    public void playNote(double freq) {
        Sample note = NoteGenerator.getInstance().getNote(freq, DEFAULT_RATE, DEFAULT_LENGTH);
        for(Effect e: effects){
            e.process(note);
        }
        play(note, DEFAULT_LENGTH * 1000);
    }

    public void closeOutputLine() {
        line.drain();
        line.close();
    }

    public void stopPlaying() {
        line.flush();
    }
}
