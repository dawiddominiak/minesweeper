package bundle.core.value;

import bundle.game.domain.value.InGameEventNames;

public interface Observer {
    void onNotification(InGameEventNames eventName);
}
