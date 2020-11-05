package models.shape;

import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.List;

public abstract class ShapeAbstract implements Shape, Cloneable, Serializable {
    private boolean selected;
    private Point topLeft;
    private String color;
    private Shape parent;
    private transient ShapeAbsImpl implementer;

    public ShapeAbstract(Point topLeft, Color color) {
        if (topLeft.getX() < 0 || topLeft.getY() < 0)
            throw new IllegalArgumentException("a point should not have negative values");
        this.topLeft = topLeft.clone();
        this.color = color.toString();
        this.selected = false;
        this.parent = null;
    }

    protected ShapeAbsImpl getImplementer() {
        if (implementer == null) {
            implementer = ShapeAbstractFactory.getInstance().createShapeImpl();
        }
        return implementer;
    }

    /**
     * draw a frame around the shape if it is selected. Otherwise (the shape is not
     * selected) it remove the frame around the shape.
     */
    public void draw() {
        getImplementer().surroundShape(this.getTopLeft(),
                getWidthFromTopLeft(),
                getHeightFromTopLeft(),
                isSelected());
    }

    public void selectIfContains(Point point) {
        if (isAt(point)) {
            select();
        }
    }

    public void addSelectedShapesTo(List<Shape> shapeList) {
        if (isSelected())
            shapeList.add(this);
    }

    public void selectGroupOf(Shape shape) {
        if (shape.getParent() == parent) {
            select();
        }
    }


    public void selectIfIsIn(Point topLeft, double width, double height) {
        if (isIn(topLeft, width, height))
            setSelected(true);
    }

    public boolean isAt(Point point) {
        return this.getTopLeft().getX()<=point.getX() && this.getTopLeft().getX()+getWidthFromTopLeft()>=point.getX() &&
                this.getTopLeft().getY()<=point.getY() && this.getTopLeft().getY()+getHeightFromTopLeft()>=point.getY();
    }

    public boolean isIn(Point topLeft, double width, double height) {
        boolean overflowTopLeft = topLeft.getX()<= this.getTopLeft().getX() && (topLeft.getY() <= this.getTopLeft().getY());
        boolean overflowTopRight = (topLeft.getX() + width) >= (this.getTopLeft().getX()+getWidthFromTopLeft());
        boolean overflowBottomLeft = (topLeft.getY() + height) >= (this.getTopLeft().getY()+getHeightFromTopLeft());

        return overflowTopLeft && overflowTopRight && overflowBottomLeft;
    }

    public void select() {
        setSelected(!isSelected());
    }

    public Shape getChildren(int i) {
        throw new UnsupportedOperationException("getChildren is only supported by a ShapeComposite");
    }

    public int getSize() {
        throw new UnsupportedOperationException("getSize is only supported by a ShapeComposite");
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected () {
        return selected;
    }

    @Override
    public Point getTopLeft() {
        return topLeft.clone();
    }

    public void setTopLeft(Point topLeft) {
        if (topLeft.getX() < 0 || topLeft.getY() < 0)
            throw new IllegalArgumentException("a point should not have negative values");
        this.topLeft = topLeft.clone();
    }

    public Color getColor() {
        return Color.web(color);
    }

    public void setColor(Color color) {
        this.color = color.toString();
    }

    public Shape getParent() {
        return parent;
    }

    public void setParent(Shape parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shape shape = (Shape) o;
        return getColor().equals(shape.getColor());
    }
}
