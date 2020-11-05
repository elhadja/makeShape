package models.shape;

import javafx.scene.canvas.GraphicsContext;

public class JfxShapeFactory extends ShapeAbstractFactory {

    private static GraphicsContext graphicsContext;

    private JfxShapeFactory() {
    }

    public static void initialize(GraphicsContext graphicsContext) {
        if (instance != null)
            throw new UnsupportedOperationException("JfxShapeFactory is already initialized");
        JfxShapeFactory.graphicsContext = graphicsContext;
        instance = new JfxShapeFactory();
    }

    @Override
    public ShapeAbsImpl createShapeImpl() {
        return new JfxShapeImpl(graphicsContext);
    }
}
