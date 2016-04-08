package bundle.core.value;

import bundle.game.domain.value.InGameEventNames;

/**
 * Interface to deterine Observable object.
 * In that version of popular design pattern Observables can dispatch "in game events".
 *
 * See <a href="https://en.wikipedia.org/wiki/Observer_pattern">Observer pattern</a>
 */
public interface Observable {
    /**
     * Registers new observer that is observing raises of associated "in game event".
     * @param eventName name of associated event.
     * @param observer Observer object
     */
    void registerObserver(InGameEventNames eventName, Observer observer);

    /**
     * Unregisters observer that is observing raises of associated "in game event".
     * @param eventName name of associated event.
     * @param observer Observer object.
     */
    void unregisterObserver(InGameEventNames eventName, Observer observer);

    /**
     * Dispatches information that associated event has been raised.
     * @param eventName name of raised event.
     */
    void dispatch(InGameEventNames eventName);
}
