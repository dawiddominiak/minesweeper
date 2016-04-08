package bundle.game.domain.service;

import java.util.UUID;

/**
 * See <a href="http://martinfowler.com/bliki/EvansClassification.html">Eric Evans - domain classes classification.</a>
 */
public class GameService {
    /**
     * Produces new game board identities.
     * @return UUID as new game board identity.
     */
    public UUID getNewGameBoardId() {
        return UUID.randomUUID();
    }
}
