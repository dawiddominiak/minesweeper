package bundle.core.value;

import bundle.game.domain.value.InGameEventNames;

public interface Observable {
    void registerObserver(InGameEventNames eventName, Observer observer);
    void unregisterObserver(InGameEventNames eventName, Observer observer);
    void dispatch(InGameEventNames eventName);
}
