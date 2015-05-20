package ui;

import common.SimpleNotePlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by isae on 19.05.15.
 */
public class Piano extends JPanel implements MouseListener {

    SimpleNotePlayer player = SimpleNotePlayer.getInstance();

    final int ON = 0, OFF = 1;
    final Color jfcBlue = new Color(204, 204, 255);
    final Color pink = new Color(255, 175, 175);
    final int TRANSPOSE = 45;

    boolean record;

    java.util.List<Key> blackKeys = new ArrayList<>();
    java.util.List<Key> keys = new ArrayList<>();
    java.util.List<Key> whiteKeys = new ArrayList<>();
    Key prevKey;
    final int kw = 16, kh = 80;


    public Piano() {
        setLayout(new BorderLayout());
       // setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        Dimension size = new Dimension(42 * kw, kh + 1);
        setMinimumSize(size);
        setSize(size);
        int whiteIDs[] = {0, 2, 4, 5, 7, 9, 11};

        for (int i = 0, x = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++, x += kw) {
                int keyNum = i * 12 + whiteIDs[j];
                whiteKeys.add(new Key(x, 0, kw, kh, keyNum));
            }
        }
        for (int i = 0, x = 0; i < 6; i++, x += kw) {
            int keyNum = i * 12;
            blackKeys.add(new Key((x += kw) - 4, 0, kw / 2, kh / 2, keyNum + 1));
            blackKeys.add(new Key((x += kw) - 4, 0, kw / 2, kh / 2, keyNum + 3));
            x += kw;
            blackKeys.add(new Key((x += kw) - 4, 0, kw / 2, kh / 2, keyNum + 6));
            blackKeys.add(new Key((x += kw) - 4, 0, kw / 2, kh / 2, keyNum + 8));
            blackKeys.add(new Key((x += kw) - 4, 0, kw / 2, kh / 2, keyNum + 10));
        }
        keys.addAll(blackKeys);
        keys.addAll(whiteKeys);

        /*addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                //if (mouseOverCB.isSelected()) {
                if (true) {//listen event if preffered way to trigger is mouse_over
                    Key key = getKey(e.getPoint());
                    if (prevKey != null && prevKey != key) {
                        prevKey.off();
                    }
                    if (key != null && prevKey != key) {
                        key.on();
                    }
                    prevKey = key;
                    repaint();
                }
            }
        });*/
        addMouseListener(this);
    }

    public void mousePressed(MouseEvent e) {
        prevKey = getKey(e.getPoint());
        if (prevKey != null) {
            prevKey.on();
            repaint();
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (prevKey != null) {
            prevKey.off();
            repaint();
            prevKey = null;
        }
    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {
    }


    public Key getKey(Point point) {
        for (Key key : keys) {
            if (key.contains(point)) {
                return key;
            }
        }
        return null;
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Dimension d = getSize();

        g2.setBackground(getBackground());
        g2.clearRect(0, 0, d.width, d.height);

        g2.setColor(Color.white);
        g2.fillRect(0, 0, 42 * kw, kh);

        for (Key whiteKey : whiteKeys) {
            if (whiteKey.isNoteOn()) {
                g2.setColor(record ? pink : jfcBlue);
                g2.fill(whiteKey);
            }
            g2.setColor(Color.black);
            g2.draw(whiteKey);
        }
        for (Key blackKey : blackKeys) {
            if (blackKey.isNoteOn()) {
                g2.setColor(record ? pink : jfcBlue);
                g2.fill(blackKey);
                g2.setColor(Color.black);
                g2.draw(blackKey);
            } else {
                g2.setColor(Color.black);
                g2.fill(blackKey);
            }
        }
    }

    public void closeOutput() {
        player.closeOutputLine();
    }

    /**
     * Black and white keys or notes on the piano.
     */
    class Key extends Rectangle {
        int noteState = OFF;
        int kNum;
        double freq;

        public Key(int x, int y, int width, int height, int num) {
            super(x, y, width, height);
            kNum = num;
            freq = 440d * Math.pow(2d, (double) (kNum - TRANSPOSE) / 12);
        }

        public boolean isNoteOn() {
            return noteState == ON;
        }

        public void on() {
            player.playNote(freq);
            setNoteState(ON);
        }

        public void off() {
            player.stopPlaying();
            setNoteState(OFF);
        }

        public void setNoteState(int state) {
            noteState = state;
        }
    } // End class Key


} // End class Piano
