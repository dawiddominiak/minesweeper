package bundle.game.domain.value;

import bundle.core.value.ValueObject;

/**
 * Reusable class represents coordinates.
 * Coordinates are valueObjects that keep information about location in two-dimensional space.
 *
 * @param <TType> Type of
 */
public class Coordinates <TType extends Comparable<TType>> implements ValueObject<Coordinates<TType>> {

    private TType x;
    private TType y;

    /**
     * Constructs new coordinates.
     *
     * @param x position on the x axis.
     * @param y position on the y axis.
     */
    public Coordinates(TType x, TType y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return position on the x axis.
     */
    public TType getX() {
        return x;
    }

    /**
     * @return position on the y axis.
     */
    public TType getY() {
        return y;
    }

    /**
     * {@inheritDoc}
     */
    public boolean sameValueAs(Coordinates<TType> other) {
        return x.compareTo(other.x) == 0 && y.compareTo(other.y) == 0;
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object other) {
        //other must be of the same class
        if (!this.getClass().equals(other.getClass())) return false;

        return sameValueAs((Coordinates<TType>) other);
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        String hashCodeString = x.toString() + ", " + y.toString();

        return hashCodeString.hashCode();
    }
}
