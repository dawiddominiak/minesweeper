package bundle.game.domain.value;

import bundle.core.value.ValueObject;

public class PlainSize implements ValueObject<PlainSize> {
    private int width;
    private int height;

    public PlainSize(int width, int height){
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
    public boolean sameValueAs(PlainSize other) {
        return width == other.width && height == other.height;
    }
}
