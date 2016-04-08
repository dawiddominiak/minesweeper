package bundle.game.domain.entity;

import bundle.core.value.*;
import bundle.game.domain.value.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents state of associated game field value.
 */
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

    /**
     * Named constructor.
     * Creates new GameFieldState entity based on GameField.
     * Additionally we need to inject identity of newly created entity.
     *
     * @param gameFieldStateId identity of new entity.
     * @param gameField GameField associated with current state.
     *
     * @return gameFieldState instance of GameFieldState
     */
    public static GameFieldState createFromGameField(GameFieldStateId gameFieldStateId, GameField gameField) {
        GameFieldState gameFieldState = new GameFieldState(gameFieldStateId);
        gameFieldState.gameField = gameField;
        gameFieldState.visibility = Visibility.HIDDEN;
        gameFieldState.activationState = ActivationState.ACTIVE;

        return gameFieldState;
    }

    /**
     * Checks if flag is placed in associated game field.
     *
     * @return true if flag is placed, false if not.
     */
    public boolean isFlagSet() {
        return isFlagSet;
    }

    /**
     * Simulates the action of digging in associated game field.
     * Follow the rules of game digging is possible
     * only if current field wasn't already dug.
     *
     * If there is a mine in associated game field,
     * digging causes mine explosion event.
     *
     * In other case digging action is safe.
     *
     * Causes side effects such as making game field visible
     * or causing mine explosion.
     *
     * @return digging result.
     */
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

    /**
     * @return game field associated with current state.
     */
    public GameField getGameField() {
        return gameField;
    }

    /**
     * Placing a flag in associated game field if there is no flag placed.
     * Removing a flag from associated game field if flag is placed.
     */
    public void toggleFlag() {
        if (checkIfCanChangeState()) {
            isFlagSet = !isFlagSet;
            dispatch(InGameEventNames.FLAG_TOGGLED);
        }
    }

    /**
     * Checks if any actions are still possible in current state.
     * It depends on visibility and activation state.
     * @return true if actions are still possible, false if object is already closed for changes.
     */
    public boolean checkIfCanChangeState() {
        return visibility == Visibility.HIDDEN && activationState == ActivationState.ACTIVE;
    }

    private void makeVisible() {
        visibility = Visibility.VISIBLE;
        activationState = ActivationState.INACTIVE;
        dispatch(InGameEventNames.FIELD_BECAME_VISIBLE);
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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
     * {@inheritDoc}
     */
    public boolean sameIdentityAs(GameFieldState other) {
        return gameFieldStateId.sameValueAs(other.gameFieldStateId);
    }
}
