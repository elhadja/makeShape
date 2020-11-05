package models.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class JfxShapeImpl extends ShapeAbsImpl {
    private final GraphicsContext graphicsContext;

    public JfxShapeImpl(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    @Override
    public void surroundShape(Point topLeftPoint, double width, double height, boolean isSelected) {
        if (isSelected)
            showSelection(topLeftPoint, width, height);
        else {
            hideSelection(topLeftPoint, width, height);
        }
    }

    /**
     * draw a frame around the shape
     * @param topLeftPoint the top left point of the shape
     * @param width the shape's width from the top left
     * @param height the shape's height from the top left
     */
    private void showSelection(Point topLeftPoint, double width, double height) {
        graphicsContext.strokeRect(topLeftPoint.getX() -2 ,topLeftPoint.getY() -2 ,
                width + 4, height + 4);
    }

    /**
     * remove the frame around the shape
     * @param topLeftPoint the top left point of the shape
     * @param width the shape's width from the top left
     * @param height the shape's height from the top left
     */
    private void hideSelection(Point topLeftPoint, double width, double height) {
        graphicsContext.clearRect(topLeftPoint.getX() -2 ,topLeftPoint.getY() -2 ,
                width + 5, height + 5);
    }

    @Override
    public void drawRectangle(Point topLeft, double width, double height, String color) {
        fillGraphicContext(Color.web(color));
        graphicsContext.fillRect(topLeft.getX(), topLeft.getY(),
                width, height);
    }

    @Override
    public void drawRoundRect(Point topLeft, double width, double height, double roundW, double roundH, String color) {
        fillGraphicContext(Color.web(color));
        graphicsContext.fillRoundRect(topLeft.getX(), topLeft.getY(),
                width, height, roundW, roundH);
    }

    @Override
    public void drawCircle(Point topLeft, double radius, String color) {
        fillGraphicContext(Color.web(color));
        graphicsContext.fillOval(topLeft.getX(),topLeft.getY(),
                radius, radius);
    }

    @Override
    public void drawPolygon(double[] xPoints, double[] yPoints, int numberSides, String color) {
        fillGraphicContext(Color.web(color));
        graphicsContext.fillPolygon(xPoints, yPoints, numberSides);
    }

    private void fillGraphicContext(Color color) {
        graphicsContext.setFill(color);
    }
}
