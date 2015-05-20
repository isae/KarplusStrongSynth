import ui.MainWindow;
import ui.Piano;
import ui.SettingsPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by isae on 19.05.15.
 */
public class Main {
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                MainWindow ex = new MainWindow();
                final Piano piano;

                JPanel pp = new JPanel(new GridLayout(2,1));
                pp.setBorder(new EmptyBorder(10, 20, 10, 5));
                pp.add(piano = new Piano());
                ex.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        piano.closeOutput();
                    }
                });
                JPanel settingsPanel = new SettingsPanel();
                pp.add(settingsPanel);
                ex.add(pp);
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int w = 900;
                int h = 300;
                ex.setLocation(screenSize.width / 2 - w / 2, screenSize.height / 2 - h / 2);
                w = piano.getSize().width + 45;
                ex.setMinimumSize(new Dimension(w, h));
                ex.setVisible(true);
            }
        });
    }
}
