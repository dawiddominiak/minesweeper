package bundle.game.domain.value;

import bundle.core.value.ValueObject;

import java.util.UUID;

/**
 * Value that represent identity of game field state.
 */
public class GameFieldStateId implements ValueObject<GameFieldStateId> {
    private UUID gameBoardStateId;
    private Coordinates<Integer> coordinates;

    /**
     * Constructs GameFieldStateId object based on gameBoardStateId and coordinates.
     * Combination of both parameters guarantees uniqueness.
     * @param gameBoardStateId game board state id.
     * @param coordinates coordinates of field on game board.
     */
    public GameFieldStateId(UUID gameBoardStateId, Coordinates<Integer> coordinates) {
        this.gameBoardStateId = gameBoardStateId;
        this.coordinates = coordinates;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean sameValueAs(GameFieldStateId other) {
        return gameBoardStateId.equals(other.gameBoardStateId) && coordinates == other.coordinates;
    }
}
