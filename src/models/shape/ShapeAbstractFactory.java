package models.shape;

import javafx.scene.paint.Color;

public abstract class ShapeAbstractFactory {
    public static ShapeAbstractFactory instance;

    public Shape createCircle(Point mainPoint, double radius, Color color) {
        return new Circle(mainPoint, radius, color);
    }

    public Shape createRectangle(Point mainPoint, double width, double height, Color color) {
        return new Rectangle(mainPoint, width, height, color);
    }

    public Shape createPolygon(Point mainPoint, double radius, int numberSides, Color color) {
        return new Polygon(mainPoint, radius, numberSides, color);
    }

    public Shape createRoundRect(Point mainPoint, double width, double height, double roundW, double roundH, Color color) {
        return new RoundRect(mainPoint, width, height, roundW, roundH, color);
    }

    public Shape createShapeGroup() {
        return new ShapeComposite();
    }

    public static ShapeAbstractFactory getInstance() {
        if (instance == null)
            throw new UnsupportedOperationException("ShapeAbstractFactory should be initialized");
        return instance;
    }

    public abstract ShapeAbsImpl createShapeImpl();

}
