package bundle.game.domain.entity;

import bundle.core.value.*;
import bundle.game.domain.value.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameFieldState implements Entity<GameFieldState>, Observable {

    private GameFieldStateId gameFieldStateId;
    private GameField gameField;
    private Visibility visibility;
    private ActivationState activationState;
    private Map<InGameEventNames, List<Observer>> observers;
    private boolean isFlagSet;


    private GameFieldState(GameFieldStateId gameFieldStateId) {
        this.gameFieldStateId = gameFieldStateId;
        observers = new HashMap<>();
        isFlagSet = false;
    }

    public static GameFieldState createFromGameField(GameFieldStateId gameFieldStateId, GameField gameField) {
        GameFieldState gameFieldState = new GameFieldState(gameFieldStateId);
        gameFieldState.gameField = gameField;
        gameFieldState.visibility = Visibility.HIDDEN;
        gameFieldState.activationState = ActivationState.ACTIVE;

        return gameFieldState;
    }

    public boolean isFlagSet() {
        return isFlagSet;
    }

    public DiggingResult dig() {
        if (!checkIfCanChangeState()) {
            return DiggingResult.ALREADY_DUG;
        }

        makeVisible();
        isFlagSet = false;

        if (gameField.getGameFieldType() == GameFieldType.MINED) {
            dispatch(InGameEventNames.MINE_EXPLODED);

            return DiggingResult.DUG_MINE;
        }

        return DiggingResult.DUG_SAFELY;
    }

    public GameField getGameField() {
        return gameField;
    }

    public boolean checkIfCanChangeState() {
        return visibility == Visibility.HIDDEN && activationState == ActivationState.ACTIVE;
    }

    private void makeVisible() {
        visibility = Visibility.VISIBLE;
        activationState = ActivationState.INACTIVE;
        dispatch(InGameEventNames.FIELD_BECAME_VISIBLE);
    }

    public void toggleFlag() {
        if (checkIfCanChangeState()) {
            isFlagSet = !isFlagSet;
            dispatch(InGameEventNames.FLAG_TOGGLED);
        }
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

    /**
     * @inheritDoc
     */
    public boolean sameIdentityAs(GameFieldState other) {
        return gameFieldStateId.sameValueAs(other.gameFieldStateId);
    }
}
