package bundle.game.domain.value;

import bundle.core.value.ValueObject;

import java.util.HashMap;

public class GamePlain implements ValueObject<GamePlain> {

    private HashMap<Coordinates<Integer>, GameField> fieldSet;
    private PlainSize<Integer> plainSize;
    private int minesCount;

    private GamePlain() {

    }

    public static GamePlain generateFromScratch(PlainSize<Integer> plainSize, int minesCount) {
        GamePlain plain = new GamePlain();
        plain.plainSize = plainSize;
        plain.minesCount = minesCount;

        //TODO: randomize new plain

        return plain;
    }

    public int getMinesCount() {
        return minesCount;
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
