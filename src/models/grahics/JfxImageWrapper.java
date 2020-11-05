package models.grahics;

import models.shape.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class JfxImageWrapper extends ImageWrapperImpl {
    private final GraphicsContext graphicsContext;
    private Image image;

    public JfxImageWrapper(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    @Override
    public void draw(String path, Point location, double width, double height) {
        if (image == null)
            image = new Image(path);
        graphicsContext.drawImage(image, location.getX(), location.getY(), width, height);
    }

}
