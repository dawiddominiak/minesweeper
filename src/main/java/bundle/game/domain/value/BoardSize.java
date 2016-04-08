package bundle.game.domain.value;

import bundle.core.value.ValueObject;

/**
 * Value represents size of board.
 */
public class BoardSize implements ValueObject<BoardSize> {
    private int width;
    private int height;

    /**
     * Constructs new board size.
     * @param width width of board.
     * @param height height of board.
     */
    public BoardSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    /**
     * @return width of board
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return height of board
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return area of board
     */
    public int getArea() {
        return width * height;
    }

    /**
     * {@inheritDoc}
     */
    public boolean sameValueAs(BoardSize other) {
        return width == other.width && height == other.height;
    }
}
