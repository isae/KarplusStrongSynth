package ui; /**
 * Created by isae on 19.05.15.
 */

import java.awt.EventQueue;
import javax.swing.*;

public class MainWindow extends JFrame {

    public MainWindow() {

        initUI();
    }

    private void initUI() {

        setTitle("KarplusStrongSynth");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


}
