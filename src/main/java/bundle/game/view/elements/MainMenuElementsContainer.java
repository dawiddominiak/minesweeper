package bundle.game.view.elements;

import bundle.game.controller.MainMenuController;
import com.google.inject.Inject;

import javax.swing.*;
import java.awt.*;

public class MainMenuElementsContainer extends JPanel {
    private StartGameWidget startGameWidget;
    private UserNavigationMenu userNavigationMenu;

    @Inject
    public void setStartGameWidget(StartGameWidget startGameWidget) {
        this.startGameWidget = startGameWidget;
    }

    @Inject
    public void setUserNavigationMenuWidget(UserNavigationMenu userNavigationMenu) {
        this.userNavigationMenu = userNavigationMenu;
    }

    public void draw() {
        setLayout(new FlowLayout());

        startGameWidget.draw();
        add(startGameWidget);

        userNavigationMenu.draw();
        add(userNavigationMenu);
    }
}
