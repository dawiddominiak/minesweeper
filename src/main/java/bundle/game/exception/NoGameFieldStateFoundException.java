package bundle.game.exception;

import java.util.NoSuchElementException;

/**
 * Exception to be executed when indicated game field state does not exist.
 */
public class NoGameFieldStateFoundException extends NoSuchElementException implements DomainException {
}
