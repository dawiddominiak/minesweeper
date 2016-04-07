package bundle.game.view.element.mainmenu;

import bundle.game.controller.MainMenuController;
import com.google.inject.Inject;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
        exitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
                    controller.exit();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        add(exitButton);
    }
}
