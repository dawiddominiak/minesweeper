package bundle.game.domain.value;

import bundle.core.value.ValueObject;

public class PlainSize<TType> implements ValueObject<PlainSize<TType>> {
    private TType width;
    private TType height;

    public PlainSize(TType width, TType height){
        this.width = width;
        this.height = height;
    }

    public TType getWidth() {
        return width;
    }

    public TType getHeight() {
        return height;
    }

    /**
     * @inheritDoc
     */
    public boolean sameValueAs(PlainSize<TType> object) {
        return getWidth() == object.getWidth() && getHeight() == object.getHeight();
    }
}
