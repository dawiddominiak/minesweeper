package bundle.game.domain.value;

import bundle.core.value.ValueObject;

import java.util.UUID;

public class GameFieldStateId implements ValueObject<GameFieldStateId> {
    private UUID gameBoardStateId;
    private Coordinates<Integer> coordinates;

    public GameFieldStateId(UUID gameBoardStateId, Coordinates<Integer> coordinates) {
        this.gameBoardStateId = gameBoardStateId;
        this.coordinates = coordinates;
    }

    @Override
    public boolean sameValueAs(GameFieldStateId other) {
        return gameBoardStateId.equals(other.gameBoardStateId) && coordinates == other.coordinates;
    }
}
