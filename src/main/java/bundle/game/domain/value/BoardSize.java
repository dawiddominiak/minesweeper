package bundle.game.domain.value;

import bundle.core.value.ValueObject;

public class BoardSize implements ValueObject<BoardSize> {
    private int width;
    private int height;

    public BoardSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getArea() {
        return width * height;
    }

    /**
     * @inheritDoc
     */
    public boolean sameValueAs(BoardSize other) {
        return width == other.width && height == other.height;
    }
}
