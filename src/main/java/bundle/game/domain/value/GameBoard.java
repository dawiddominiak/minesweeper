package bundle.game.domain.value;

import bundle.core.util.Randomness;
import bundle.core.value.ValueObject;
import bundle.game.exception.NoGameFieldFoundException;
import com.google.common.primitives.Ints;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Immutable object to represent game board.
 */
public class GameBoard implements ValueObject<GameBoard> {

    private Map<Coordinates<Integer>, GameField> fieldMap;
    private BoardSize boardSize;
    private int minesCount;

    private GameBoard() {
        fieldMap = new HashMap<>();
    }

    /**
     * Named constructor to generate random game board based on board size and mines count.
     * @param boardSize board size
     * @param minesCount number of mines
     *
     * @return new GameBoard value object.
     */
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
                board.fieldMap.put(fieldCoordinates, field);
            }
        }

        return board;
    }

    /**
     * @return map of fields contained by GameBoard.
     */
    public Map<Coordinates<Integer>, GameField> getFieldMap() {
        return fieldMap;
    }

    /**
     * @return current BoardSize
     */
    public BoardSize getBoardSize() {
        return boardSize;
    }

    /**
     * @return number of mines
     */
    public int getMinesCount() {
        return minesCount;
    }

    /**
     * @param coordinates coordinates of field.
     *
     * @return field located under indicated coordinates.
     */
    public GameField getField(Coordinates<Integer> coordinates) {
        GameField field = fieldMap.get(coordinates);

        if (field == null) {
            throw new NoGameFieldFoundException();
        }

        return fieldMap.get(coordinates);
    }

    /**
     * Counts number of mines in fields connected with indicated field.
     * @param gameField field to count neighboring mines.
     * @return number of mines.
     */
    public int countNumberOfMinesInNeighborhood(GameField gameField) {
        List<GameField> neighboringFields = getListOfAllNeighbors(gameField);

        long numberOfMines = neighboringFields
                .stream()
                .filter(neighbor -> neighbor.getGameFieldType() == GameFieldType.MINED)
                .count()
        ;

        return Ints.saturatedCast(numberOfMines);
    }

    /**
     * Gets list of neighboring fields on board.
     * @param gameField gameField to find neighbors.
     * @return list of all neighbors.
     */
    public List<GameField> getListOfAllNeighbors(GameField gameField) {
        List<GameField> neighbors = new ArrayList<>();
        Coordinates<Integer> coordinates = gameField.getCoordinates();
        int currentX = coordinates.getX();
        int currentY = coordinates.getY();
        int x, y;

        for (x = currentX - 1; x <= currentX + 1; ++x) {
            for (y = currentY - 1; y <= currentY + 1; ++y) {
                if (x != 0 || y != 0) {
                    try {
                        GameField neighbor = getField(new Coordinates<>(x, y));
                        neighbors.add(neighbor);
                    } catch (NoGameFieldFoundException e) {}
                }
            }
        }

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

    /**
     * {@inheritDoc}
     */
    public boolean sameValueAs(GameBoard otherBoard) {
        return this.minesCount == otherBoard.minesCount
            && this.boardSize.sameValueAs(otherBoard.boardSize)
            && sameFieldMapAs(otherBoard.getFieldMap())
        ;
    }

    /**
     * Checks if indicated field map contains exactly the same elements.
     * @param otherFieldMap field map to compare.
     * @return true if field maps are same, false in other case.
     */
    public boolean sameFieldMapAs(Map<Coordinates<Integer>, GameField> otherFieldMap) {
        return fieldMap.entrySet().size() == otherFieldMap.entrySet().size()
        && fieldMap.entrySet().stream().allMatch(entry -> otherFieldMap.get(entry.getKey()) == entry.getValue());
    }
}
