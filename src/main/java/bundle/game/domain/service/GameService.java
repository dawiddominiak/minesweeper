package bundle.game.domain.service;

import java.util.UUID;

public class GameService {
    public UUID getNewGamePlainId() {
        return UUID.randomUUID();
    }
}
