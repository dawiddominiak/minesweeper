package bundle.game.controller;

import bundle.core.controller.AbstractController;
import bundle.game.domain.value.PlainSize;
import bundle.game.view.MainMenu;
import com.google.inject.Inject;

public class MainMenuController extends AbstractController<MainMenu> {

    @Inject
    public void setView(MainMenu view) {
        super.setView(view);
    }

    public void createNewGame(PlainSize<Integer> plainSize, int minesCount) {

    }

    public void exit() {
        System.exit(0);
    }
}
