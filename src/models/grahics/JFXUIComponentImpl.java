package models.grahics;

import models.shape.Point;
import javafx.scene.canvas.GraphicsContext;

public class JFXUIComponentImpl extends UIComponentAbsImpl {
    private final GraphicsContext graphicsContext;

    public JFXUIComponentImpl(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    @Override
    public void drawFrame(Point topLeft, double width, double height, boolean clearRect) {
        if (clearRect)
            graphicsContext.clearRect(topLeft.getX(), topLeft.getY(), width, height);
        graphicsContext.strokeRect(topLeft.getX(),topLeft.getY(), width, height);
    }

    @Override
    public void showDialogError(String headerMessageError,String errorMessage) {
        DialogBoxManagerJFX.showErrorAlert(headerMessageError, errorMessage);
    }
}
