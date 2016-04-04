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

    public boolean sameValueAs(Coordinates<TType> object) {
        return getX() == object.getX() && getY() == object.getY();
    }
}
