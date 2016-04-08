package bundle.game.domain.entity;

import bundle.core.value.Entity;
import bundle.core.value.Observable;
import bundle.core.value.Observer;
import bundle.game.domain.value.*;
import bundle.game.exception.NoGameFieldStateFoundException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents state of associated game board value
 */
public class GameBoardState implements Entity<GameBoardState>, Observable {
    private UUID gameBoardStateId;
    private GameBoard gameBoard;
    private Map<Coordinates<Integer>, GameFieldState> gameFieldStateMap;
    private Map<InGameEventNames, List<Observer>> observers;

    private GameBoardState(UUID gameBoardStateId) {
        this.gameBoardStateId = gameBoardStateId;
        this.observers = new HashMap<>();
        gameFieldStateMap = new HashMap<>();
    }

    /**
     * Named constructor to create new GameBoardState entity based on associated GameBoard.
     * Additionally we need to inject identity of newly created entity.
     *
     * @param gameBoardStateId identity of newly created entity.
     * @param board GameBoard value associated with newly created state.
     *
     * @return new GameBoardState entity.
     */
    public static GameBoardState createFromGameBoard(UUID gameBoardStateId, GameBoard board) {
        GameBoardState state = new GameBoardState(gameBoardStateId);
        state.gameBoard = board;

        state.gameFieldStateMap = board
                .getFieldMap()
                .entrySet()
                .stream()
                .collect(
                    Collectors.toMap(Map.Entry::getKey, e -> {
                        Map.Entry<Coordinates<Integer>, GameField> entry = (Map.Entry) e;

                        return GameFieldState.createFromGameField(
                            new GameFieldStateId(gameBoardStateId, entry.getKey()),
                            entry.getValue()
                        );
                    })
                )
        ;

        return state;
    }

    /**
     * @return associated GameBoard.
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Calls the game field state associated with gameField to toggle flag.
     * @param gameField gameField associated with game field state.
     */
    public void toggleFlag(GameField gameField) {
        getGameFieldState(gameField).toggleFlag();
        dispatch(InGameEventNames.FLAG_TOGGLED);
    }

    /**
     * Calls the game field state associated with gameField to dig.
     * Digs every safety field in the area outlined by business logic of game.
     *
     * @param gameField gameField associated with game field state.
     */
    public void digOn(GameField gameField) {
        GameFieldState gameFieldState = getGameFieldState(gameField);
        DiggingResult diggingResult = gameFieldState.dig();

        if (diggingResult == DiggingResult.DUG_MINE) {
            dispatch(InGameEventNames.MINE_EXPLODED);
        }

        if (
            diggingResult == DiggingResult.DUG_SAFELY
            && gameBoard.countNumberOfMinesInNeighborhood(gameField) == 0
        ) {
            List<GameFieldState> initialList = new ArrayList<>();
            initialList.add(gameFieldState);

            while (initialList.size() > 0) {
                initialList = nextBreadthFirstStep(initialList);
            }
        }
    }

    /**
     * See <a href="https://en.wikipedia.org/wiki/Breadth-first_search">Breadth-first search algorithm.</a>
     * One step of BFS algorithm.
     * Returns the list of neighboring, not visited fields if it's allowed by business rules.
     * Method automatically does the dig if it's possible,
     * pointing out that neighbor was visited.
     *
     * @param initialList initial list of fields.
     *
     * @return list of allowed, not visited neighbors.
     */
    private List<GameFieldState> nextBreadthFirstStep(List<GameFieldState> initialList) {
        List<GameFieldState> outputList = new ArrayList<>();

        initialList.stream().forEach(gameFieldState -> {
            List<GameField> neighbors = gameBoard.getListOfAllNeighbors(gameFieldState.getGameField());
            List<GameFieldState> notVisitedNeighbors = neighbors
                    .stream()
                    .map(this::getGameFieldState)
                    .filter(GameFieldState::checkIfCanChangeState)
                    .collect(Collectors.toList())
            ;

            neighbors
                    .stream()
                    .forEach(
                            neighbor -> getGameFieldState(neighbor).dig()
                    );

            outputList.addAll(
                notVisitedNeighbors
                    .stream()
                    .filter(neighbor -> gameBoard.countNumberOfMinesInNeighborhood(neighbor.getGameField()) == 0)
                    .collect(Collectors.toList())
            );
        });

        return outputList;
    }

    /**
     * @param gameField game field associated with game field state.
     * @return game field state associated with gameField.
     * @throws NoGameFieldStateFoundException
     */
    public GameFieldState getGameFieldState(GameField gameField) throws NoGameFieldStateFoundException {
        Coordinates<Integer> coordinates = gameField.getCoordinates();
        GameFieldState gameFieldState = getGameFieldState(coordinates);

        if (gameFieldState.getGameField() != gameField) { //checks if gameField is a part of current GameBoard
            throw new NoGameFieldStateFoundException();
        }

        return gameFieldState;
    }

    /**
     * @param coordinates coordinates of game field associated with game field state.
     * @return state of game field located under indicated coordinates.
     * @throws NoGameFieldStateFoundException
     */
    public GameFieldState getGameFieldState(Coordinates<Integer> coordinates) throws NoGameFieldStateFoundException {
        GameFieldState gameFieldState = gameFieldStateMap.get(coordinates);

        if (gameFieldState == null) {
            throw new NoGameFieldStateFoundException();
        }

        return gameFieldState;
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
    @Override
    public boolean sameIdentityAs(GameBoardState other) {
        return other.gameBoardStateId == this.gameBoardStateId;
    }
}
