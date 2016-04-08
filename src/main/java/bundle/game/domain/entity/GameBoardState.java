package bundle.game.domain.entity;

import bundle.core.value.Entity;
import bundle.core.value.Observable;
import bundle.core.value.Observer;
import bundle.game.domain.value.*;
import bundle.game.exception.NoGameFieldStateFoundException;

import java.util.*;
import java.util.stream.Collectors;

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

    public static GameBoardState createFromGameBoard(UUID gameBoardStateId, GameBoard board) {
        GameBoardState state = new GameBoardState(gameBoardStateId);
        state.gameBoard = board;

        state.gameFieldStateMap = board
                .getFieldSet()
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

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void toggleFlag(GameField gameField) {
        getGameFieldState(gameField).toggleFlag();
        dispatch(InGameEventNames.FLAG_TOGGLED);
    }

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

    public GameFieldState getGameFieldState(GameField gameField) {
        Coordinates<Integer> coordinates = gameField.getCoordinates();
        GameFieldState gameFieldState = getGameFieldState(coordinates);

        if (gameFieldState.getGameField() != gameField) { //checks if gameField is a part of current GameBoard
            throw new NoGameFieldStateFoundException();
        }

        return gameFieldState;
    }

    public GameFieldState getGameFieldState(Coordinates<Integer> coordinates) {
        GameFieldState gameFieldState = gameFieldStateMap.get(coordinates);

        if (gameFieldState == null) {
            throw new NoGameFieldStateFoundException();
        }

        return gameFieldState;
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

    public Map<Coordinates<Integer>, GameFieldState> getGameFieldStateMap() {
        return gameFieldStateMap;
    }

    @Override
    public boolean sameIdentityAs(GameBoardState other) {
        return other.gameBoardStateId == this.gameBoardStateId;
    }
}
