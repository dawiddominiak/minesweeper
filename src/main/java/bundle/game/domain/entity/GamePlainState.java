package bundle.game.domain.entity;

import bundle.core.value.Entity;
import bundle.core.value.Observable;
import bundle.core.value.Observer;
import bundle.game.domain.value.*;

import java.util.*;
import java.util.stream.Collectors;

public class GamePlainState implements Entity<GamePlainState>, Observable {
    private UUID gamePlainStateId;
    private GamePlain gamePlain;
    private Map<Coordinates<Integer>, GameFieldState> gameFieldStateSet;
    private Map<InGameEventNames, List<Observer>> observers;

    private GamePlainState(UUID gamePlainStateId) {
        this.gamePlainStateId = gamePlainStateId;
        this.observers = new HashMap<>();
    }

    public static GamePlainState createFromGamePlain(UUID gamePlainStateId, GamePlain plain) {
        GamePlainState state = new GamePlainState(gamePlainStateId);
        state.gamePlain = plain;

        state.gameFieldStateSet = plain
                .getFieldSet()
                .entrySet()
                .stream()
                .collect(
                    Collectors.toMap(Map.Entry::getKey, e -> {
                        Map.Entry<Coordinates<Integer>, GameField> entry = (Map.Entry) e;

                        return GameFieldState.createFromGameField(
                            new GameFieldStateId(gamePlainStateId, entry.getKey()),
                            entry.getValue()
                        );
                    })
                )
        ;

        return state;
    }

    @Override
    public void registerObserver(InGameEventNames eventName, Observer observer) {
        if (!observers.containsKey(eventName)) {
            observers.put(eventName, new ArrayList<>());
        }

        List<Observer> observersList = observers.get(eventName);

        if (!observersList.contains(observer)) {
            observersList.add(observer);
        }
    }

    @Override
    public void unregisterObserver(InGameEventNames eventName, Observer observer) {
        if (observers.containsKey(eventName)) {
            List<Observer> observersList = observers.get(eventName);
            observersList.remove(observer);

            if (observersList.isEmpty()) {
                observers.remove(eventName);
            }
        }
    }

    @Override
    public void dispatch(InGameEventNames eventName) {
        if (observers.containsKey(eventName)) {
            observers
                    .get(eventName)
                    .stream()
                    .forEach(observer -> observer.onNotification(eventName))
            ;
        }
    }

    @Override
    public boolean sameIdentityAs(GamePlainState other) {
        return other.gamePlainStateId == this.gamePlainStateId;
    }
}
