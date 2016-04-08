package bundle.game.exception;

import java.util.NoSuchElementException;

/**
 * Exception to be executed when indicated game field does not exist.
 */
public class NoGameFieldFoundException extends NoSuchElementException implements DomainException {
}
