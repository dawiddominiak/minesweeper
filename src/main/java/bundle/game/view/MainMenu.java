package bundle.game.view;

import bundle.game.controller.MainMenuController;
import bundle.game.view.elements.MainMenuElementsContainer;
import com.google.inject.Inject;

import javax.swing.*;

public class MainMenu extends JFrame {

    private MainMenuElementsContainer mainMenuElementsContainer;
    private MainMenuController controller;

    @Inject
    public void setMainMenuElementsContainer(MainMenuElementsContainer mainMenuElementsContainer) {
        this.mainMenuElementsContainer = mainMenuElementsContainer;
    }

    @Inject
    public void setController(MainMenuController controller) {
        this.controller = controller;
    }

    @Inject
    public void draw() {
        setTitle("Main menu");
        mainMenuElementsContainer.draw();
        setContentPane(mainMenuElementsContainer);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(500, 500);
        setVisible(true);
    }
}