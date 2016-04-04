package bundle.core.value;

/**
 * Interface to determine that current class will produce Entities.
 * @see <a href="http://martinfowler.com/bliki/EvansClassification.html">Classification of domain objects</a>.
 * @param <TType> final class.
 */
public interface Entity<TType> {
    /**
     * Compares identity of current class with identity of other entity object.
     * @param other object to compare.
     * @return boolean flag determines if objects have the same identity.
     */
    boolean sameIdentityAs(TType other);
}
