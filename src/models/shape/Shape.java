package models.shape;

import javafx.scene.paint.Color;

import java.util.List;

public interface Shape {
    void draw();
    void translate(double xShift, double yShift);
    void rotate (double radians);
    Shape Clone ();
    boolean equals(Object o);

    /**
     * select the shape if it is not selected. Otherwise deselect the shape.
     */
    void select();

    /**
     * check if its area is at @param point
     * @param point the location where to check
     * @return true if the area of the shape is at @param point or false otherwise.
     */
    boolean isAt(Point point);

    /**
     * check if the shape is include in the area formed by @param topLeft @param width and @param height
     * @param topLeft the topLeft of the area
     * @param width the width of the area
     * @param height the height of the area
     * @return true if the area of the shape is include in the area to check. false otherwise
     */
    boolean isIn(Point topLeft, double width, double height);

    /**
     * add a shape in its list of children
     * @param shape the shape to add
     * @exception UnsupportedOperationException the shape must be a ShapeComposite
     */
    void addShape(Shape shape);

    /**
     * remove a shape in its list of children
     * @param shape the shape to remove
     * @exception UnsupportedOperationException the shape must be a ShapeComposite
     */
    void removeShape(Shape shape);

    /**
     * add his reference in the @param shapeList if it is selected
     * @param shapeList the list where the shape will add his reference
     */
    void addSelectedShapesTo(List<Shape> shapeList);

    /**
     * set the attribute 'selected' to true if the shape is inside
     * the area formed by @param topLeft, @param width and @param height
     * @param topLeft the top Left of the area
     * @param width   the width of the area
     * @param height  the height of the area
     */
    void selectIfIsIn(Point topLeft, double width, double height);

    /**
     * select the Shape if the @param point is inside the shape area
     * @param point indicates the location to search
     */
    void selectIfContains(Point point);

    /**
     * select the shape if it has the same parent than @param shape
     * @param shape the shape which will be used to compare parents
     */
    void selectGroupOf(Shape shape);

    /**
     * @return the top left point of the shape
     *
     * Each shape is considered as a rectangle to ease its selection
     * on a click event for example
     */
    Point getTopLeft();

    /**
     * @return the number of children which contains a ShapeComposite
     *
     * @exception UnsupportedOperationException when the shape is not a ShapeComposite
     */
    int getSize();

    /**
     * get a child of a shape
     * @param i then indice of the child to get
     * @return the child at the indice @param i
     * @exception UnsupportedOperationException the shape must be a ShapeComposite
     */
    Shape getChildren(int i);

    /**
     * @return the height of the shape from his top left point
     *
     * Each shape is considered as a rectangle to ease his selection
     * on a click event for example
     */
    double getHeightFromTopLeft();

    /**
     * @return the width of the shape from his top left point
     *
     * Each shape is considered as a rectangle to ease his selection
     * on a click event
     */
    double getWidthFromTopLeft();


    void setSelected(boolean selected);
    boolean isSelected();
    void setTopLeft(Point topLeft);
    Color getColor();
    void setColor(Color color);
    Shape getParent();
    void setParent(Shape parent);
}
