package bundle.game.controller;

import bundle.game.domain.entity.GameBoardState;
import bundle.game.domain.entity.GameFieldState;
import bundle.game.domain.service.GameService;
import bundle.game.domain.value.GameBoard;
import bundle.game.domain.value.BoardSize;
import bundle.game.domain.value.GameField;
import bundle.game.view.GameView;
import com.google.inject.Inject;

import java.util.UUID;

/**
 * Class to control all business logic of minesweeper game.
 * It's a kind of bridge between game view and game entities.
 */
public class GameController {
    private GameService gameService;
    private GameView view;

    /**
     * Injects view into current controller.
     * @param view game view to be injected.
     */
    @Inject
    public void setView(GameView view) {
        this.view = view;
    }

    /**
     * Injects gameService into current controller.
     * @param gameService game service to be injected.
     */
    @Inject
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
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

        view.handleGameBoardState(state);
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
        gameBoardState.toggleFlag(gameField);
    }
}
