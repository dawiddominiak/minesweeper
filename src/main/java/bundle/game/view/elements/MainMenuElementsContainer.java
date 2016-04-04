package bundle.game.view.elements;

import bundle.core.view.ControllerAwareWidget;
import bundle.game.controller.MainMenuController;

import java.awt.*;

public class MainMenuElementsContainer extends ControllerAwareWidget<MainMenuController> {

    public MainMenuElementsContainer(MainMenuController controller) {
        setController(controller);
        init();
    }

    private void init() {
        MainMenuController controller = getController();

        FlowLayout layout = new FlowLayout();
        setLayout(layout);

        ControllerAwareWidget<MainMenuController> startGame = new StartGameWidget(controller);
        add(startGame);

        ControllerAwareWidget<MainMenuController> userNavigation = new UserNavigationMenu(controller);
        add(userNavigation);
    }
}
