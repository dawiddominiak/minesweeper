package bundle.game.view.elements;

import bundle.game.controller.MainMenuController;
import com.google.inject.Inject;

import javax.swing.*;

public class UserNavigationMenu extends JPanel {
    private MainMenuController controller;

    @Inject
    public UserNavigationMenu(MainMenuController controller) {
        this.controller = controller;
    }

    public void draw() {
        BoxLayout currentLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(currentLayout);

        JButton credentialsButton = new MainMenuButton();
        credentialsButton.setText("Credentials");
        add(credentialsButton);

        JButton exitButton = new MainMenuButton();
        exitButton.setText("Exit");
        exitButton.addActionListener(e -> {
            controller.exit();
        });
        add(exitButton);
    }
}
