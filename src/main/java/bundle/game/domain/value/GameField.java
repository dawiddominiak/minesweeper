package bundle.game.domain.value;

import bundle.core.value.ValueObject;

/**
 * Immutable object to represent game field.
 */
public class GameField implements ValueObject<GameField> {
    private Coordinates<Integer> coordinates;
    private GameFieldType gameFieldType;

    /**
     * Named constructor to generate game field based on coordinates and game field type.
     * @param coordinates coordinates
     * @param gameFieldType type of field
     */
    public GameField(Coordinates<Integer> coordinates, GameFieldType gameFieldType) {
        this.coordinates = coordinates;
        this.gameFieldType = gameFieldType;
    }

    /**
     * @return coordinates of current field.
     */
    public Coordinates<Integer> getCoordinates() {
        return coordinates;
    }

    /**
     * @return type of current field.
     */
    public GameFieldType getGameFieldType() {
        return gameFieldType;
    }

    /**
     * {@inheritDoc}
     */
    public boolean sameValueAs(GameField other) {
        return this.coordinates.sameValueAs(other.coordinates)
            && gameFieldType == other.gameFieldType
        ;
    }
}
