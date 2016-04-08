package bundle.game.controller;

import bundle.game.domain.entity.GameBoardState;
import bundle.game.domain.entity.GameFieldState;
import bundle.game.domain.service.GameService;
import bundle.game.domain.value.GameBoard;
import bundle.game.domain.value.BoardSize;
import bundle.game.domain.value.GameField;
import bundle.game.exception.TooManyFlagsSetException;
import bundle.game.view.GameView;
import com.google.inject.Inject;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.UUID;

/**
 * Class to control all business logic of minesweeper game.
 * It's a kind of bridge between game view and game entities.
 */
public class GameController {
    private GameService gameService;
    private MainMenuController mainMenuController;
    private GameView view;

    /**
     * Injects gameService into current controller.
     * @param gameService game service to be injected.
     */
    @Inject
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Injects mainMenuController into current controller.
     * @param mainMenuController main menu controller to be injected.
     */
    @Inject
    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }

    /**
     * Creates model objects that represents game board and it's state.
     * Calls view to render game board based on model objects.
     *
     * @param boardSize size of newly created board.
     * @param minesCount number of mines in newly created game board.
     */
    public void showGameBoard(BoardSize boardSize, int minesCount) {
        GameBoard board = GameBoard.generateFromScratch(boardSize, minesCount);
        UUID stateId = gameService.getNewGameBoardId();
        GameBoardState state = GameBoardState.createFromGameBoard(stateId, board);

        GameView view = new GameView(this);

        if (this.view != null) {
            closeWindow();
        }

        this.view = view;

        view.handleGameBoardState(state);
    }

    /**
     * Runs show action from main menu controller.
     * Makes current frame invisible.
     */
    public void moveToMainMenu() {
        mainMenuController.makeVisible();
        closeWindow();
    }

    private void closeWindow() {
        view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
    }

    /**
     * Executes GameBoardState dig action.
     *
     * @param gameFieldState game field state with game field to be dug.
     * @param gameBoardState current game board state.
     */
    public void dispatchDigAction(GameFieldState gameFieldState, GameBoardState gameBoardState) {
        GameField gameField = gameFieldState.getGameField();
        gameBoardState.digOn(gameField);
    }

    /**
     * Executes GameBoardState toggle flag action.
     *
     * @param gameFieldState game field state with flag to be toggled.
     * @param gameBoardState current game board state.
     */
    public void dispatchToggleFlagAction(GameFieldState gameFieldState, GameBoardState gameBoardState) {
        GameField gameField = gameFieldState.getGameField();

        try {
            gameBoardState.toggleFlag(gameField);
        } catch (TooManyFlagsSetException e) { }
    }
}
