package models.shape;

import java.io.Serializable;

public class Point implements Cloneable, Serializable {

    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void rotate(Point center, double angle) {
        double xM;
        double yM;

        xM = x - center.getX();
        yM = y - center.getY();

        x = (xM * Math.cos (angle) + yM * Math.sin (angle) + center.getX());
        y = (- xM * Math.sin (angle) + yM * Math.cos (angle) + center.getY());
    }

    public void translate(double xShift, double yShift) {
        x += xShift;
        y += yShift;
    }

    public boolean isIn(Point rectangleTopLeft, double width, double height) {
        return rectangleTopLeft.getX()<=x && rectangleTopLeft.getX() + width >=x &&
                rectangleTopLeft.getY()<=y && rectangleTopLeft.getY()+height >= y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 &&
                Double.compare(point.y, y) == 0;
    }

    @Override
    public Point clone() {
        Point tmp = null;
        try {
            tmp = (Point) super.clone();
        }
        catch(CloneNotSupportedException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return tmp;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
