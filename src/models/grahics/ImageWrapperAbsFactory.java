package models.grahics;

import models.shape.Point;

public abstract class ImageWrapperAbsFactory {
    protected static ImageWrapperAbsFactory instance;

    public abstract ImageWrapper createImage(String path, Point location, double width, double height);
    public abstract ImageWrapperImpl createImplementer();
    public static ImageWrapperAbsFactory getInstance() {
        if (instance == null)
            throw new UnsupportedOperationException("ImageWrapperAbsFactory must be initialized");
        return instance;
    }
}
