package models.shape;

import javafx.scene.paint.Color;
import test.Main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShapeComposite extends ShapeAbstract implements Serializable {
    private List<Shape> children;

    public ShapeComposite() {
        super(new Point(0, 0), Color.WHITE);
        children = new ArrayList<>();
    }

    @Override
    public void translate(double xShift, double yShift) {
        for (Shape child : children) {
            child.translate(xShift, yShift);
        }
    }

    @Override
    public void rotate(double radians) {
        for (Shape child : children) {
            child.rotate(radians);
        }
    }

    @Override
    public void draw() {
        if (Main.isDebug() && getParent() != null) {
            getImplementer().surroundShape(new Point(this.getTopLeft().getX()-2, this.getTopLeft().getY()-2),
                    getWidthFromTopLeft()+4, getHeightFromTopLeft()+4, true);
        }
        for (Shape child : children) {
            child.draw();
        }
    }

    @Override
    public void select() {
        for (Shape child : children) {
            child.select();
        }
    }

    @Override
    public void selectIfContains(Point point) {
        for (Shape child : children) {
            child.selectIfContains(point);
        }
    }

    @Override
    public void selectIfIsIn(Point topLeft, double width, double height) {
        for (Shape child: children)
            child.selectIfIsIn(topLeft, width, height);
    }

    @Override
    public void selectGroupOf(Shape shape) {
        for (Shape child : children) {
            child.selectGroupOf(shape);
        }
    }

    @Override
    public boolean isAt(Point point) {
        for (Shape child : children) {
            if (child.isAt(point))
                return true;
        }
        return false;
    }

    @Override
    public boolean isIn(Point topLeft, double width, double height) {
        for (Shape child : children) {
            if (!child.isIn(topLeft, width, height))
                return false;
        }
        return true;
    }

    @Override
    public void removeShape(Shape shape) {
            shape.setParent(null);
            int i=0;
            while(i<children.size() & shape != children.get(i))
                i++;
            children.remove(i);
    }

    @Override
    public void addShape(Shape shape) {
        shape.setParent(this);
        children.add(shape);
    }

    @Override
    public void addSelectedShapesTo(List<Shape> shapeList) {
        if (getParent() != null && children.size()>0 && isSelected())
            shapeList.add(this);
        else{
            for (Shape child : children) {
                child.addSelectedShapesTo(shapeList);
            }
        }
    }

    @Override
    public void setColor(Color color) {
        for (Shape child : children) {
            child.setColor(color);
        }
    }

    @Override
    public boolean isSelected() {
        for (Shape child : children) {
            if (!child.isSelected())
                return false;
        }
        return children.size()>0;
    }

    @Override
    public void setSelected(boolean selected) {
        for (Shape child : children) child.setSelected(selected);
    }

    @Override
    public Shape getChildren(int i) {
        if (i>=0 && i< children.size())
            return children.get(i);
        throw new IllegalArgumentException();
    }

    @Override
    public int getSize() {
        return children.size();
    }

    @Override
    public double getWidthFromTopLeft() {
        Point mainPoint = this.getTopLeft();
        double maxX = 0;
        for (Shape child : children) {
            if (child.getTopLeft().getX() + child.getWidthFromTopLeft() > maxX)
                maxX = child.getTopLeft().getX() + child.getWidthFromTopLeft();
        }
        return Math.abs(mainPoint.getX() - maxX);
    }

    @Override
    public double getHeightFromTopLeft() {
        Point mainPoint = this.getTopLeft();
        double maxY = 0;
        for (Shape child : children) {
            if (child.getTopLeft().getY() + child.getHeightFromTopLeft() > maxY)
                maxY = child.getTopLeft().getY() + child.getHeightFromTopLeft();
        }
        return Math.abs(mainPoint.getY() - maxY);
    }

    @Override
    public Point getTopLeft() {
        if (children.size() == 0)
            return new Point(0, 0);
        double x=2000, y=2000;
        for (Shape child : children) {
            if (child.getTopLeft().getX() < x)
                x = child.getTopLeft().getX();
            if (child.getTopLeft().getY() < y)
                y = child.getTopLeft().getY();
        }
        return new Point(x, y);
    }

    @Override
    public void setTopLeft(Point topLeft) {
        Point lastMainPoint = getTopLeft();
        for (Shape shape: children) {
           double x, y;
           x = topLeft.getX() + Math.abs(lastMainPoint.getX() - shape.getTopLeft().getX());
           y = topLeft.getY() + Math.abs(lastMainPoint.getY() - shape.getTopLeft().getY());
           shape.setTopLeft(new Point(x, y));
        }
    }

    @Override
    public Color getColor() {
        throw new UnsupportedOperationException("ShapeGroup.getColor is an unsupported operation");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shape shape = (Shape) o;
       for(int i=0; i<children.size(); i++) {
           if (!children.get(i).equals(shape.getChildren(i)))
               return false;
       }
       return true;
    }

    @Override
    public Shape Clone() {
        ShapeComposite tmp = null;
        try {
            tmp = (ShapeComposite) super.clone();
            tmp.children = new ArrayList<>();
            for (Shape child : children) {
                tmp.addShape(child.Clone());
            }
        }catch (CloneNotSupportedException ignored) {

        }
        return tmp;
    }
}
