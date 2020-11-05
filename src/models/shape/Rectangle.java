package models.shape;

import javafx.scene.paint.Color;

import java.io.Serializable;

public class Rectangle extends ShapeAbstract implements Serializable {
    private double width;
    private double height;

    public Rectangle(Point mainPoint, double width, double height, Color color) {
        super(mainPoint, color);
        setWidth(width);
        setHeight(height);
    }

    @Override
    public void draw() {
        super.draw();
        getImplementer().drawRectangle(getTopLeft(), width, height, getColor().toString());
    }

    @Override
    public void rotate(double angle) {
        double tmp = width;
        width = height;
        height = tmp;
    }

    @Override
    public Rectangle Clone() {
        Rectangle tmp = null;
        try {
            tmp = (Rectangle) super.clone();
            tmp.setTopLeft(getTopLeft().clone());
        }
        catch(CloneNotSupportedException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return tmp;
    }

    @Override
    public void translate(double xShift, double yShift) {
        Point newMainPoint = getTopLeft();
        newMainPoint.translate(xShift, yShift);
        setTopLeft(newMainPoint);
    }

    @Override
    public void removeShape(Shape shape) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addShape(Shape shape) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getWidthFromTopLeft() {
        return getWidth();
    }

    @Override
    public double getHeightFromTopLeft() {
        return getHeight();
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Rectangle rectangle = (Rectangle) o;
        return Double.compare(rectangle.width, width) == 0 &&
                Double.compare(rectangle.height, height) == 0;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "width=" + width +
                ", height=" + height +
                ", selected=" + isSelected() +
                ", mainPoint=" + getTopLeft() +
                ", color='" + getColor() + '\'' +
                ", parent=" + getParent() +
                '}';
    }
}

