package ui;

import common.DistortionEffect;
import common.SimpleNotePlayer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Created by isae on 21.05.15.
 */
public class SettingsPanel extends JPanel implements ChangeListener {

    SimpleNotePlayer player = SimpleNotePlayer.getInstance();
    public JButton recordB;
    JSlider distortionS;
    JSlider presS;
    JSlider revbS;

    JCheckBox muteCB;
    JCheckBox soloCB;
    JCheckBox monoCB;
    JCheckBox mouseOverCB;

    public SettingsPanel() {
        setBorder(new EmptyBorder(5,10,5,10));

        JPanel p = new JPanel();

        distortionS = createSlider("Distortion", p,127);
        final DistortionEffect distortion = new DistortionEffect((byte) 127);
        player.addEffect(distortion);
        distortionS.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                distortion.setTreshold((byte) distortionS.getValue());
            }
        });
       /* presS = createSlider("Pressure", p);
        revbS = createSlider("Reverb", p);*/


        p.add(Box.createHorizontalStrut(10));
        add(p);

    }

    private JSlider createSlider(String name, JPanel p, int value) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 127, value);
        slider.addChangeListener(this);
        TitledBorder tb = new TitledBorder(new EtchedBorder());
        tb.setTitle(name + " = "+value);
        slider.setBorder(tb);
        p.add(slider);
        p.add(Box.createHorizontalStrut(5));
        return slider;
    }


    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        int value = slider.getValue();
        TitledBorder tb = (TitledBorder) slider.getBorder();
        String s = tb.getTitle();
        tb.setTitle(s.substring(0, s.indexOf('=')+1) + String.valueOf(value));
        slider.repaint();
    }


}
