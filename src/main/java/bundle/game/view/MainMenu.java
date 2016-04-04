package bundle.game.view;

import bundle.core.view.ControllerAwareView;
import bundle.core.view.ControllerAwareWidget;
import bundle.game.controller.MainMenuController;
import bundle.game.view.elements.MainMenuElementsContainer;

import javax.inject.Inject;
import javax.swing.*;

public class MainMenu extends ControllerAwareView<MainMenuController> {

    @Inject
    public MainMenu(MainMenuController controller) {
        setTitle("Main menu");
        setController(controller);
        ControllerAwareWidget<MainMenuController> mainMenuElementsContainer
                = new MainMenuElementsContainer(controller);
        setContentPane(mainMenuElementsContainer);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(500, 500);
        setVisible(true);
    }
}