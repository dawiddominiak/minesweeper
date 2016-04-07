package bundle.game.domain.value;

import bundle.core.util.Randomness;
import bundle.core.value.ValueObject;
import bundle.game.exception.NoGameFieldFoundException;
import com.google.common.primitives.Ints;

import java.util.*;
import java.util.stream.Collectors;

public class GameBoard implements ValueObject<GameBoard> {

    private Map<Coordinates<Integer>, GameField> fieldSet;
    private BoardSize boardSize;
    private int minesCount;

    private GameBoard() {
        fieldSet = new HashMap<>();
    }

    public static GameBoard generateFromScratch(BoardSize boardSize, int minesCount) {
        GameBoard board = new GameBoard();
        board.boardSize = boardSize;
        board.minesCount = minesCount;
        Coordinates<Integer> mineLocation = null;

        List<Coordinates<Integer>> minesLocations = board.randomMinesLocations();
        Iterator minesLocationsIterator = minesLocations.iterator();

        if (minesLocationsIterator.hasNext()) {
            mineLocation = (Coordinates<Integer>) minesLocationsIterator.next();
        }

        for (int y = 1; y <= boardSize.getHeight(); y++) {
            for (int x = 1; x <= boardSize.getWidth(); x++) {
                Coordinates<Integer> fieldCoordinates = new Coordinates<>(x, y);
                GameFieldType fieldType = GameFieldType.CLEAN;

                if (mineLocation != null && fieldCoordinates.sameValueAs(mineLocation)) {
                    fieldType = GameFieldType.MINED;

                    if (minesLocationsIterator.hasNext()) {
                        mineLocation = (Coordinates<Integer>) minesLocationsIterator.next();
                    }
                }

                GameField field = new GameField(fieldCoordinates, fieldType);
                board.fieldSet.put(fieldCoordinates, field);
            }
        }

        return board;
    }

    public Map<Coordinates<Integer>, GameField> getFieldSet() {
        return fieldSet;
    }

    public BoardSize getBoardSize() {
        return boardSize;
    }

    public int getMinesCount() {
        return minesCount;
    }

    public GameField getField(Coordinates<Integer> coordinates) {
        GameField field = fieldSet.get(coordinates);

        if (field == null) {
            throw new NoGameFieldFoundException();
        }

        return fieldSet.get(coordinates);
    }

    public int countNumberOfMinesInNeighborhood(GameField gameField) {
        List<GameField> neighboringFields = getListOfAllNeighbors(gameField);

        long numberOfMines = neighboringFields
                .stream()
                .filter(neighbor -> neighbor.getGameFieldType() == GameFieldType.MINED)
                .count()
        ;

        return Ints.saturatedCast(numberOfMines);
    }

    public List<GameField> getListOfAllNeighbors(GameField gameField) {
        List<GameField> neighbors = getListOfTroubleNeighbors(gameField);
        Coordinates<Integer> coordinates = gameField.getCoordinates();
        int currentX = coordinates.getX();
        int currentY = coordinates.getY();

        try {
            GameField northeasternNeighbor = getField(new Coordinates<>(currentX + 1, currentY + 1));
            neighbors.add(northeasternNeighbor);
        } catch (NoGameFieldFoundException e) {}

        try {
            GameField southeasternNeighbor = getField(new Coordinates<>(currentX + 1, currentY - 1));
            neighbors.add(southeasternNeighbor);
        } catch (NoGameFieldFoundException e) {}

        try {
            GameField southwesternNeighbor = getField(new Coordinates<>(currentX - 1, currentY - 1));
            neighbors.add(southwesternNeighbor);
        } catch (NoGameFieldFoundException e) {}

        try {
            GameField northwesternNeighbor = getField(new Coordinates<>(currentX - 1, currentY + 1));
            neighbors.add(northwesternNeighbor);
        } catch (NoGameFieldFoundException e) {}

        return neighbors;
    }

    /**
     * @param gameField GameField to find neighbors
     * @return list of TRouBLe (top right bottom left :)) neighbors of current GameField
     */
    public List<GameField> getListOfTroubleNeighbors(GameField gameField) {
        List<GameField> neighbors = new ArrayList<>();
        Coordinates<Integer> coordinates = gameField.getCoordinates();
        int currentX = coordinates.getX();
        int currentY = coordinates.getY();

        try {
            GameField northernNeighbor = getField(new Coordinates<>(currentX, currentY + 1));
            neighbors.add(northernNeighbor);
        } catch (NoGameFieldFoundException e) {}

        try {
            GameField easternNeighbor = getField(new Coordinates<>(currentX + 1, currentY));
            neighbors.add(easternNeighbor);
        } catch (NoGameFieldFoundException e) {}

        try {
            GameField southernNeighbor = getField(new Coordinates<>(currentX, currentY - 1));
            neighbors.add(southernNeighbor);
        } catch (NoGameFieldFoundException e) {}

        try {
            GameField westernNeighbor = getField(new Coordinates<>(currentX - 1, currentY));
            neighbors.add(westernNeighbor);
        } catch (NoGameFieldFoundException e) {}

        return neighbors;
    }

    private List<Coordinates<Integer>> randomMinesLocations() {

        return Ints
                .asList(Randomness.sampleRandomNumbersWithoutRepetition(
                    1, boardSize.getArea(), minesCount
                ))
                .stream()
                .map(mineLocationMask -> {
                    boolean isItLastElementInLine = (mineLocationMask% boardSize.getWidth() == 0);
                    int x, y;

                    if (isItLastElementInLine) {
                        x = boardSize.getWidth();
                        y = mineLocationMask/ boardSize.getWidth();
                    } else {
                        x = mineLocationMask% boardSize.getWidth();
                        y = mineLocationMask/ boardSize.getWidth() + 1;
                    }

                    return new Coordinates<>(x, y);
                })
                .collect(Collectors.toList());
    }

    public boolean sameValueAs(GameBoard otherBoard) {
        return this.minesCount == otherBoard.minesCount
            && this.boardSize.sameValueAs(otherBoard.boardSize)
            && sameFieldSetAs(otherBoard)
        ;
    }

    public boolean sameFieldSetAs(GameBoard otherBoard) {
        //TODO: logic here
        return false;
    }
}
