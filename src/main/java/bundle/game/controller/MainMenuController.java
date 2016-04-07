package bundle.game.controller;

import bundle.game.domain.value.BoardSize;
import bundle.game.view.MainMenuView;
import com.google.inject.Inject;

public class MainMenuController {
    private MainMenuView view;
    private GameController gameController;

    @Inject
    public void setView(MainMenuView view) {
        this.view = view;
    }

    @Inject
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void show() {
        view.draw();
    }

    public void createNewGame(BoardSize boardSize, int minesCount) {
        view.setVisible(false);
        gameController.showGameBoard(boardSize, minesCount);
    }

    public void exit() {
        System.exit(0);
    }
}
