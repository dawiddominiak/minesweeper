package bundle.game.domain.value;

import bundle.core.value.ValueObject;

public class GameField implements ValueObject<GameField> {
    private Coordinates<Integer> coordinates;
    private GameFieldType gameFieldType;

    public GameField(Coordinates<Integer> coordinates, GameFieldType gameFieldType) {
        this.coordinates = coordinates;
        this.gameFieldType = gameFieldType;
    }

    public Coordinates<Integer> getCoordinates() {
        return coordinates;
    }

    public GameFieldType getGameFieldType() {
        return gameFieldType;
    }

    /**
     * @inheritDoc
     */
    public boolean sameValueAs(GameField other) {
        return this.coordinates.sameValueAs(other.coordinates)
            && gameFieldType == other.gameFieldType
        ;
    }
}
