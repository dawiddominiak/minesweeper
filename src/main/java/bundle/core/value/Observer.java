package bundle.core.value;

import bundle.game.domain.value.InGameEventNames;

/**
 * Interface to determine Observer object.
 * In that version of popular design pattern Observers can be informed about "in game events".
 *
 * See <a href="https://en.wikipedia.org/wiki/Observer_pattern">Observer pattern</a>
 */
public interface Observer {
    void onNotification(InGameEventNames eventName);
}
