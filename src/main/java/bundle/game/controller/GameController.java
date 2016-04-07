package bundle.game.controller;


import bundle.game.domain.entity.GameBoardState;
import bundle.game.domain.service.GameService;
import bundle.game.domain.value.GameBoard;
import bundle.game.domain.value.BoardSize;
import bundle.game.view.GameView;
import com.google.inject.Inject;

import java.util.UUID;

public class GameController {
    private GameService gameService;
    private GameView view;

    @Inject
    private void setView(GameView view) {
        this.view = view;
    }

    @Inject
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    public void showGameBoard(BoardSize boardSize, int minesCount) {
        GameBoard board = GameBoard.generateFromScratch(boardSize, minesCount);
        UUID stateId = gameService.getNewGameBoardId();
        GameBoardState state = GameBoardState.createFromGameBoard(stateId, board);

        view.handleGameBoardState(state);
    }
}
