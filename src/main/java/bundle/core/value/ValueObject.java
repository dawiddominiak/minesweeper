package bundle.core.value;

/**
 * Interface to determine that current class will produce ValueObject's.
 * @see <a href="http://martinfowler.com/bliki/ValueObject.html">Martin Fowler definition of value object</a>.
 * @param <TType> final class.
 */
public interface ValueObject<TType> {
    /**
     * Compares value of current object with value of other object.
     * @param other object to compare.
     * @return boolean flag determines if objects have the same value.
     */
    boolean sameValueAs(TType other);
}
