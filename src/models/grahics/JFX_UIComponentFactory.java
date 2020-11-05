package models.grahics;

import javafx.scene.canvas.GraphicsContext;

public class JFX_UIComponentFactory extends UIComponentAbstractFactory {
   private static GraphicsContext graphicContext;

   private JFX_UIComponentFactory() {}

    @Override
    public UIComponentAbsImpl createImplementer() {
        return new JFXUIComponentImpl(graphicContext);
    }

    public static void initialize(GraphicsContext graphicsContext) {
        if (instance != null)
            throw new UnsupportedOperationException("JfxUIComponentFactory is already initialized");
        JFX_UIComponentFactory.graphicContext = graphicsContext;
        instance = new JFX_UIComponentFactory();
    }

}
