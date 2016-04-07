package bundle.game.domain.service;

import java.util.UUID;

public class GameService {
    public UUID getNewGameBoardId() {
        return UUID.randomUUID();
    }
}
