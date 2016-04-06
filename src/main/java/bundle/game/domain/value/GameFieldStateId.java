package bundle.game.domain.value;

import bundle.core.value.ValueObject;

import java.util.UUID;

public class GameFieldStateId implements ValueObject<GameFieldStateId> {
    private UUID gamePlainStateId;
    private Coordinates<Integer> coordinates;

    public GameFieldStateId(UUID gamePlainStateId, Coordinates<Integer> coordinates) {
        this.gamePlainStateId = gamePlainStateId;
        this.coordinates = coordinates;
    }

    @Override
    public boolean sameValueAs(GameFieldStateId other) {
        return gamePlainStateId.equals(other.gamePlainStateId) && coordinates == other.coordinates;
    }
}
