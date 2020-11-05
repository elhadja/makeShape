package models.grahics;

import models.shape.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class UIWhiteBoard extends UIComponentShapeManager implements Cloneable {

    public UIWhiteBoard(Point location, double width, double height) {
        super(location, width, height);
    }

    @Override
    public void draw() {
        super.draw();
        getShapeComposite().draw();
    }

    @Override
    public void addShapesAt(Point location, List<Shape> shapes) {
        double nextLocation = location.getY();
        for (Shape shape: shapes) {
            shape.setSelected(false);
            addShapeAt(shape.Clone(), new Point(location.getX(), nextLocation));
            nextLocation += shape.getHeightFromTopLeft() + 5;
        }
    }

    @Override
    public boolean addShapeAt(Shape shape, Point location) {
        shape.setTopLeft(location.clone());
        if (shape.isIn(getLocation(), getWidth(), getHeight())) {
            addShape(shape);
        } else {
            getImplementer().showDialogError("Some shapes doesn't added to the whiteboard",
                    "shape(s) must not overlow the whiteboard");
            return false;
        }
        return true;
    }

    /**
     * Set the properties of the whiteboard's selected shapes
     * @param color the new color
     * @param width the new width (only for rectangles)
     * @param height the new height (only for rectangles)
     * @param location the new location
     * @param radius the new radius (for rectangles and polygons)
     * @param numberSides the new numberSides (only for a Polygon)
     */
    public void setSelectedShapesProperties(Color color,
                                            double width,
                                            double height,
                                            Point location,
                                            double radius,
                                            int numberSides) {

        List<Shape> selectedShapes = new ArrayList<>();
        addSelectedShapesTo(selectedShapes);

        for (Shape currentShape : selectedShapes) {
            if (!color.equals(Color.WHITE))
                currentShape.setColor(color);
            boolean overflowLocation = setLocation(currentShape, location);
            boolean overflowSize = setSize(currentShape, width, height, radius, numberSides);
            if (overflowLocation || overflowSize) {
                UIComponentAbstractFactory.getInstance().createImplementer().showDialogError("Some shapes wasn't modified",
                        "The entered values make shape(s) overflow the whiteboard");
            }
        }
    }

    /**
     * set the location of a shape
     * If location is not valid the shape will not be modified
     * @param currentShape the shape to set the location
     * @param location the new location
     * @return true if the new location provokes an overflow or false otherwise
     */
    private boolean setLocation(Shape currentShape,Point location) {
        if (location.getX() > 0 && location.getY() > 0) {
            Point lastLocation = currentShape.getTopLeft();
            currentShape.setTopLeft(location);
            if (!currentShape.isIn(getLocation(), getWidth(), getHeight())) {
                currentShape.setTopLeft(lastLocation);
                return true;
            }
        }
        return false;
    }

    /**
     * set a size for a shape.
     * @param currentShape the shape to set the size
     * @param width the new width (only for rectangle)
     * @param height the new height (only for rectangle)
     * @param radius the new radius (only for polygon and circle)
     * @param numberSides the new number sides (only for polygon)
     * @return true if the new size provokes an overflow or false otherwise
     */
    private boolean setSize(Shape currentShape, double width, double height, double radius, int numberSides) {
        boolean overflow = false;
        if (currentShape instanceof Rectangle) {
            overflow = setRectangleSize((Rectangle)currentShape, width, height);
        }
        else if (currentShape instanceof Circle && radius > 0) {
            overflow = setCircleSize((Circle) currentShape, radius);
        }
        else if (currentShape instanceof Polygon) {
            overflow = setPolygonSize((Polygon) currentShape, radius, numberSides);
        }
        else if (currentShape instanceof ShapeComposite) {
            for (int j=0; j<currentShape.getSize(); j++)
                overflow = overflow || setSize(currentShape.getChildren(j), width, height, radius, numberSides);
        }
        return overflow;
    }

    /**
     * set the size of a rectangle
     * @param rectangle the rectangle to set the size
     * @param width the new width
     * @param height the new height
     * @return true if the new location provokes an overflow or false otherwise
     */
    private boolean setRectangleSize(Rectangle rectangle, double width, double height) {
        double lastWidth = rectangle.getWidth();
        double lastHeigh = rectangle.getHeight();
        if (width > 0)
            rectangle.setWidth(width);
        if (height > 0)
            rectangle.setHeight(height);
        if (!rectangle.isIn(getLocation(), getWidth(), getHeight())) {
            rectangle.setWidth(lastWidth);
            rectangle.setHeight(lastHeigh);
            return true;
        }
        return false;
    }

    /**
     * set the size of a circle
     * If the radius is not valid the shape will not be modified
     * @param circle the cirlce to set the size
     * @param radius the new radius
     * @return true if the new location provokes an overflow or false otherwise
     */
    private boolean setCircleSize(Circle circle, double radius) {
        double lastRadius = circle.getRadius();
        circle.setRadius(radius);
        if (!circle.isIn(getLocation(), getWidth(), getHeight())) {
            circle.setRadius(lastRadius);
            return true;
        }
        return false;
    }

    /**
     * set the size of a polygon
     * If the radius and the number of sides is not valid the shape will not be modified
     * @param polygon the polygon to set a new size
     * @param radius the new radius
     * @param numberSides the new number of sides
     * @return true if the new location provokes an overflow or false otherwise
     */
    private boolean setPolygonSize(Polygon polygon, double radius, int numberSides) {
        if (numberSides > 2)
            polygon.setNumberSides(numberSides);
        double lastRadius = polygon.getRadius();
        if (radius > 0)
            polygon.setRadius(radius);
        if (!polygon.isIn(getLocation(), getWidth(), getHeight())) {
            polygon.setRadius(lastRadius);
            return true;
        }
        return false;
    }

    @Override
    public Object Clone() {
        UIWhiteBoard temp = null;
        try {
            temp = (UIWhiteBoard) super.clone();
            temp.setShapeComposite(getShapeComposite());

        }catch (Exception ignored) {

        }
        return temp;
    }
}
