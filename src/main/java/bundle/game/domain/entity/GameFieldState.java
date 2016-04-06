package bundle.game.domain.entity;

import bundle.core.value.Entity;
import bundle.game.domain.value.GameField;
import bundle.game.domain.value.GameFieldStateId;

public class GameFieldState implements Entity<GameFieldState> {

    private GameFieldStateId gameFieldStateId;
    private GameField gameField;

    private GameFieldState(GameFieldStateId gameFieldStateId) {
        this.gameFieldStateId = gameFieldStateId;
    }

    public static GameFieldState createFromGameField(GameFieldStateId gameFieldStateId, GameField gameField) {
        GameFieldState gameFieldState = new GameFieldState(gameFieldStateId);
        gameFieldState.gameField = gameField;

        return gameFieldState;
    }

    /**
     * @inheritDoc
     */
    public boolean sameIdentityAs(GameFieldState other) {
        return gameFieldStateId.sameValueAs(other.gameFieldStateId);
    }
}
