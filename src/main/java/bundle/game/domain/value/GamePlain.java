package bundle.game.domain.value;

import bundle.core.util.Randomness;
import bundle.core.value.ValueObject;
import com.google.common.primitives.Ints;

import java.util.*;
import java.util.stream.Collectors;

public class GamePlain implements ValueObject<GamePlain> {

    private Map<Coordinates<Integer>, GameField> fieldSet;
    private PlainSize plainSize;
    private int minesCount;

    private GamePlain() {

    }

    public Map<Coordinates<Integer>, GameField> getFieldSet() {
        return fieldSet;
    }

    public PlainSize getPlainSize() {
        return plainSize;
    }

    public int getMinesCount() {
        return minesCount;
    }

    public static GamePlain generateFromScratch(PlainSize plainSize, int minesCount) {
        GamePlain plain = new GamePlain();
        plain.plainSize = plainSize;
        plain.minesCount = minesCount;
        plain.fieldSet = new HashMap<>();

        List<Coordinates<Integer>> minesLocations = plain.randomMinesLocations();
        Iterator minesLocationsIterator = minesLocations.iterator();
        Coordinates<Integer> mineLocation = (Coordinates<Integer>) minesLocationsIterator.next();

        for (int x = 1; x <= plainSize.getWidth(); x++) {
            for (int y = 1; y <= plainSize.getHeight(); y++) {
                Coordinates<Integer> fieldCoordinates = new Coordinates<>(x, y);
                GameFieldType fieldType = GameFieldType.CLEAN;

                if (fieldCoordinates.sameValueAs(mineLocation)) {
                    fieldType = GameFieldType.MINED;
                    mineLocation = (Coordinates<Integer>) minesLocationsIterator.next();
                }

                GameField field = new GameField(fieldCoordinates, fieldType);
                plain.fieldSet.put(fieldCoordinates, field);
            }
        }

        return plain;
    }

    private List<Coordinates<Integer>> randomMinesLocations() {

        return Ints
                .asList(Randomness.sampleRandomNumbersWithoutRepetition(
                    1, plainSize.getArea(), minesCount
                ))
                .stream()
                .map(mineLocationMask -> {
                    boolean isItLastElementInLine = (mineLocationMask%plainSize.getWidth() == 0);
                    int x, y;

                    if (isItLastElementInLine) {
                        x = plainSize.getWidth();
                        y = mineLocationMask/plainSize.getWidth();
                    } else {
                        x = mineLocationMask%plainSize.getWidth();
                        y = mineLocationMask/plainSize.getWidth() + 1;
                    }

                    return new Coordinates<>(x, y);
                })
                .collect(Collectors.toList());
    }

    public boolean sameValueAs(GamePlain otherPlain) {
        return this.minesCount == otherPlain.minesCount
            && this.plainSize.sameValueAs(otherPlain.plainSize)
            && sameFieldSetAs(otherPlain)
        ;
    }

    public boolean sameFieldSetAs(GamePlain otherPlain) {
        //TODO: logic here
        return false;
    }
}
