package bundle.game.domain.entity;

import bundle.core.value.Entity;
import bundle.game.domain.value.GameField;

import java.util.UUID;

public class GameFieldState implements Entity<GameFieldState> {

    private UUID gameFieldStateId;
    private GameField gameField;

    private GameFieldState(UUID gameFieldStateId) {
        this.gameFieldStateId = gameFieldStateId;
    }

    public static GameFieldState createFromGameField(UUID gameFieldStateId, GameField gameField) {
        GameFieldState gameFieldState = new GameFieldState(gameFieldStateId);
        gameFieldState.gameField = gameField;

        return gameFieldState;
    }

    public UUID getGameFieldStateId() {
        return gameFieldStateId;
    }

    /**
     * @inheritDoc
     */
    public boolean sameIdentityAs(GameFieldState other) {
        return gameFieldStateId.equals(other.getGameFieldStateId());
    }
}
