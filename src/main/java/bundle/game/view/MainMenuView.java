package bundle.game.view;

import bundle.game.view.element.mainmenu.MainMenuElementsContainer;
import com.google.inject.Inject;

import javax.swing.*;

/**
 * View of main menu
 */
public class MainMenuView extends JFrame {
    private MainMenuElementsContainer mainMenuElementsContainer;

    /**
     * Injects main menu elements container widget.
     * @param mainMenuElementsContainer main menu elements container widget to be injected.
     */
    @Inject
    public void setMainMenuElementsContainer(MainMenuElementsContainer mainMenuElementsContainer) {
        this.mainMenuElementsContainer = mainMenuElementsContainer;
    }

    /**
     * Draws current window.
     */
    public void draw() {
        setTitle("Main menu");
        mainMenuElementsContainer.draw();
        setContentPane(mainMenuElementsContainer);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(200, 300);
        setVisible(true);
    }
}