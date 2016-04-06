package bundle.game.controller;


import bundle.game.domain.entity.GamePlainState;
import bundle.game.domain.service.GameService;
import bundle.game.domain.value.GamePlain;
import bundle.game.domain.value.PlainSize;
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

    public void showGamePlain(PlainSize plainSize, int minesCount) {
        GamePlain plain = GamePlain.generateFromScratch(plainSize, minesCount);
        UUID stateId = gameService.getNewGamePlainId();
        GamePlainState state = GamePlainState.createFromGamePlain(stateId, plain);

        view.handleGamePlainState(state);
    }
}
