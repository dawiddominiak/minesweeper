package bundle.game.controller;

import bundle.game.domain.value.BoardSize;
import bundle.game.view.MainMenuView;
import com.google.inject.Inject;

/**
 * Class to control behavior of main menu.
 */
public class MainMenuController {
    private MainMenuView view;
    private GameController gameController;

    /**
     * Injects view into current controller.
     * @param view menu view to be injected.
     */
    @Inject
    public void setView(MainMenuView view) {
        this.view = view;
    }

    /**
     * Injects game controller.
     * @param gameController game controller to be injected.
     */
    @Inject
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * Calls view to render main menu.
     */
    public void show() {
        view.draw();
    }

    /**
     * Hides current menu and calls game controller to show new game board.
     *
     * @param boardSize size of game board.
     * @param minesCount number of mines.
     */
    public void createNewGame(BoardSize boardSize, int minesCount) {
        view.setVisible(false);
        gameController.showGameBoard(boardSize, minesCount);
    }

    /**
     * Exits current application
     */
    public void exit() {
        System.exit(0);
    }
}
