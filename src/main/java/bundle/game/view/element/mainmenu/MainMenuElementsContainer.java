package bundle.game.view.element.mainmenu;

import com.google.inject.Inject;

import javax.swing.*;
import java.awt.*;

/**
 * Container to keep menu widgets
 */
public class MainMenuElementsContainer extends JPanel {
    private StartGameWidget startGameWidget;
    private UserNavigationMenu userNavigationMenu;

    /**
     * Injects start game widget
     * @param startGameWidget start game widget to be injected
     */
    @Inject
    public void setStartGameWidget(StartGameWidget startGameWidget) {
        this.startGameWidget = startGameWidget;
    }

    /**
     * Inejcts user navigation menu
     * @param userNavigationMenu user navigation menu widget to be injected
     */
    @Inject
    public void setUserNavigationMenuWidget(UserNavigationMenu userNavigationMenu) {
        this.userNavigationMenu = userNavigationMenu;
    }

    /**
     * Renders container
     */
    public void draw() {
        setLayout(new FlowLayout());

        startGameWidget.draw();
        add(startGameWidget);

        userNavigationMenu.draw();
        add(userNavigationMenu);
    }
}
