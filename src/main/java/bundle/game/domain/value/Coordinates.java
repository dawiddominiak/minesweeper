package bundle.game.domain.value;

import bundle.core.value.ValueObject;

/**
 * Reusable class represents coordinates.
 * Coordinates are valueObjects that
 *
 * @param <TType>
 */
public class Coordinates <TType> implements ValueObject<Coordinates<TType>> {

    private TType x;
    private TType y;

    public Coordinates(TType x, TType y) {
        this.x = x;
        this.y = y;
    }

    public TType getX() {
        return x;
    }

    public TType getY() {
        return y;
    }

    public boolean sameValueAs(Coordinates<TType> other) {
        return x == other.x && y == other.y;
    }

    public boolean equals(Object other) {
        //other must be of the same class
        if (!this.getClass().equals(other.getClass())) return false;

        return sameValueAs((Coordinates<TType>) other);
    }
}
