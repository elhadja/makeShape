package models.shape;

import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Polygon extends ShapeAbstract implements Cloneable, Serializable {
    private ArrayList<Point> points;
    private int numberSides;
    private double radius;
    private double angle;
    
    public Polygon (Point mainPoint, double radius, int numberSides, Color color) {
        super(mainPoint, color);
        points = new ArrayList<>();
        setRadius(radius);
        this.numberSides = numberSides;
        angle = computeAngle();
        computePoints();
        rotate(angle/2);
    }

    private double computeAngle() {
        return Math.PI * 2 / numberSides;
    }

    /**
     * computes polygon's points
     */
    private void computePoints() {
        points.clear();
        for (int index=0; index<numberSides; index++) {
            double i = (Math.sin(index*angle) * radius)+ getTopLeft().getX() + radius;
            double j = (Math.cos(index*angle) * radius)+ getTopLeft().getY() + radius;
            points.add(new Point(i, j));
        }
    }

    @Override
    public void translate(double xShift, double yShift) {
        Point newPoint = getTopLeft();
        newPoint.translate(xShift, yShift);
        setTopLeft(newPoint);
        for (int i = 0; i< numberSides; i++)
            points.get(i).translate(xShift, yShift);
    }

    @Override
    public void rotate(double radians) {
        Point center = new Point(getTopLeft().getX() + radius, getTopLeft().getY() + radius);
        for (int i = 0; i< numberSides; i++)
        {
            points.get(i).rotate(center, -radians); // (-) to rotate in Clockwise
        }
    }

    @Override
    public void draw() {
        super.draw();
        getImplementer().drawPolygon(xPoints(), yPoints(), numberSides, getColor().toString());
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
    public Polygon Clone() {
        Polygon tmp = null;
        try {
            tmp = (Polygon) super.clone();
            tmp.setTopLeft(getTopLeft().clone());
            tmp.points = new ArrayList<>();
            for (int i=0; i<points.size(); i++)
                tmp.points.add(points.get(i).clone());
        }
        catch(CloneNotSupportedException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return tmp;
    }

    public double[] xPoints() {
        double[] xs = new double[numberSides];
        for (int i = numberSides -1; i>=0; i--) {
            xs[i] = points.get(i).getX();
        }
        return xs;
    }

    public double[] yPoints() {
        double[] ys = new double[numberSides];
        for (int i = numberSides -1; i>=0; i--) {
            ys[i] = points.get(i).getY();
        }
        return ys;
    }

    @Override
    public double getWidthFromTopLeft() {
        return 2*getRadius();
    }

    @Override
    public double getHeightFromTopLeft() {
        return getWidthFromTopLeft();
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
        computePoints();
    }

    public void setTopLeft(Point topLeft) {
        super.setTopLeft(topLeft);
        computePoints();
    }

    public void setNumberSides(int numberSides) {
        if (numberSides < 2)
            throw new IllegalArgumentException("a polygon should have most than 2 sides");
        this.numberSides = numberSides;
        this.angle = computeAngle();
        computePoints();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Polygon polygon = (Polygon) o;

        boolean pointAreEquals = true;
        for (int i=0; i<xPoints().length; i++) {
            if (points.get(i).getX() != polygon.xPoints()[i] ||
                    points.get(i).getY() != polygon.yPoints()[i]) {
                pointAreEquals = false;
                break;
            }
        }

       return numberSides == polygon.numberSides &&
                Double.compare(polygon.radius, radius) == 0 &&
                Double.compare(polygon.angle, angle) == 0 &&
               pointAreEquals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(points, numberSides, radius, angle);
    }

    @Override
    public String toString() {
        return "Polygon{" +
                "points=" + points +
                ", numberSides=" + numberSides +
                ", radius=" + radius +
                ", angle=" + angle +
                ", selected=" + isSelected() +
                ", mainPoint=" + getTopLeft() +
                ", color='" + getColor() + '\'' +
                ", parent=" + getParent() +
                '}';
    }
}
