package models.grahics;

import models.shape.Point;
import javafx.scene.canvas.GraphicsContext;

public class JfxImageFactory extends ImageWrapperAbsFactory {
    private static GraphicsContext graphicsContext;

    private JfxImageFactory() {

    }

    @Override
    public ImageWrapper createImage(String path, Point location, double width, double height) {
        return new ImageWrapper(location, path, width,height);
    }

    @Override
    public ImageWrapperImpl createImplementer() {
        return new JfxImageWrapper(graphicsContext);
    }

    public static void initialize (GraphicsContext graphicsContext) {
        if (instance != null)
            throw new UnsupportedOperationException("JfxImageFactory is already initialized");
        JfxImageFactory.graphicsContext = graphicsContext;
        instance = new JfxImageFactory();
    }
}
