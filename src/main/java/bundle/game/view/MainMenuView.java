package bundle.game.view;

import bundle.game.view.element.mainmenu.MainMenuElementsContainer;
import com.google.inject.Inject;

import javax.swing.*;

public class MainMenuView extends JFrame {

    private MainMenuElementsContainer mainMenuElementsContainer;

    @Inject
    public void setMainMenuElementsContainer(MainMenuElementsContainer mainMenuElementsContainer) {
        this.mainMenuElementsContainer = mainMenuElementsContainer;
    }

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