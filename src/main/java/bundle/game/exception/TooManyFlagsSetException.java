package bundle.game.exception;

import java.util.NoSuchElementException;

/**
 * Exception to be executed when there are more flags than mines in game field.
 */
public class TooManyFlagsSetException extends NoSuchElementException implements DomainException {
}
