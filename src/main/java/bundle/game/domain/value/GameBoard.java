package bundle.game.domain.value;

import bundle.core.util.Randomness;
import bundle.core.value.ValueObject;
import com.google.common.primitives.Ints;

import java.util.*;
import java.util.stream.Collectors;

public class GameBoard implements ValueObject<GameBoard> {

    private Map<Coordinates<Integer>, GameField> fieldSet;
    private BoardSize boardSize;
    private int minesCount;

    private GameBoard() {

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

    public static GameBoard generateFromScratch(BoardSize boardSize, int minesCount) {
        GameBoard board = new GameBoard();
        board.boardSize = boardSize;
        board.minesCount = minesCount;
        board.fieldSet = new HashMap<>();

        List<Coordinates<Integer>> minesLocations = board.randomMinesLocations();
        Iterator minesLocationsIterator = minesLocations.iterator();
        Coordinates<Integer> mineLocation = (Coordinates<Integer>) minesLocationsIterator.next();

        for (int x = 1; x <= boardSize.getWidth(); x++) {
            for (int y = 1; y <= boardSize.getHeight(); y++) {
                Coordinates<Integer> fieldCoordinates = new Coordinates<>(x, y);
                GameFieldType fieldType = GameFieldType.CLEAN;

                if (fieldCoordinates.sameValueAs(mineLocation)) {
                    fieldType = GameFieldType.MINED;
                    mineLocation = (Coordinates<Integer>) minesLocationsIterator.next();
                }

                GameField field = new GameField(fieldCoordinates, fieldType);
                board.fieldSet.put(fieldCoordinates, field);
            }
        }

        return board;
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
