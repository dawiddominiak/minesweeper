package bundle.game.view.elements;

import bundle.core.view.ControllerAwareWidget;
import bundle.game.controller.MainMenuController;

import javax.swing.*;

public class UserNavigationMenu extends ControllerAwareWidget<MainMenuController> {

    public UserNavigationMenu(MainMenuController controller) {
        setController(controller);
        init();
    }

    private void init() {
        BoxLayout currentLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(currentLayout);

        JButton credentialsButton = new MainMenuButton();
        credentialsButton.setText("Credentials");
        add(credentialsButton);

        JButton exitButton = new MainMenuButton();
        exitButton.setText("Exit");
        exitButton.addActionListener(e -> {
            getController().exit();
        });
        add(exitButton);
    }
}
