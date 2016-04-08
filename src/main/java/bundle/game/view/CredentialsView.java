package bundle.game.view;

import javax.swing.*;
import java.awt.*;

public class CredentialsView extends JFrame {

    public CredentialsView() {
        setTitle("Credentials");

        setLayout(new GridLayout(0, 1));
        add(new Label("@Author Dawid Dominiak"));
        add(new Label("@Email poczta@dawiddominiak.pl"));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(getPreferredSize());
    }
}
