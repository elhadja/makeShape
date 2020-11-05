package models.shape;

import javafx.scene.paint.Color;

import java.io.Serializable;

public class Circle extends ShapeAbstract implements Cloneable, Serializable {
    public static final long serialVersionUID = 42L;
    private double radius;

    public Circle(Point mainPoint, double radius, Color color) {
        super(mainPoint, color);
        this.radius = radius;
    }

    @Override
    public void translate(double xShift, double yShift) {
        Point newMainPoint = getTopLeft();
        newMainPoint.translate(xShift, yShift);
        setTopLeft(newMainPoint);
    }

    @Override
    public void rotate(double angle) {
        // nothing to do
    }

    @Override
    public void draw() {
        super.draw();
        getImplementer().drawCircle(getTopLeft(), radius(), getColor().toString());
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
        return radius();
    }

    @Override
    public double getHeightFromTopLeft() {
        return radius();
    }

    public double radius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public Circle Clone() {
        Circle tmp = null;
        try {
            tmp = (Circle) super.clone();
            tmp.setTopLeft(getTopLeft().clone());
        }
        catch(CloneNotSupportedException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return tmp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Circle circle = (Circle) o;
        return Double.compare(circle.radius, radius) == 0;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "radius=" + radius +
                ", selected=" + isSelected() +
                ", mainPoint=" + getTopLeft() +
                ", color='" + getColor() + '\'' +
                ", parent=" + getParent() +
                '}';
    }
}
