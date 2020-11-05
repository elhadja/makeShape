package models.grahics;

import models.shape.Point;

public abstract class UIComponentAbstract implements UIComponent, Cloneable {
    private final double width;
    private final double height;
    private final Point location;
    private UIComponentAbsImpl implementer;

    public UIComponentAbstract(Point location, double width, double height) {
        this.location = location.clone();
        this.width = width;
        this.height = height;
    }

    protected UIComponentAbsImpl getImplementer() {
        if (implementer == null) {
            implementer = UIComponentAbstractFactory.getInstance().createImplementer();
        }
        return implementer;
    }

    @Override
    public void draw() {
        getImplementer().drawFrame(location, width, height, true);
    }

    @Override
    public boolean isAt(Point point) {
        return location.getX()<=point.getX() && location.getX()+width>=point.getX() &&
                location.getY()<=point.getY() && location.getY()+height>=point.getY();
    }


    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Point getLocation() {
        return location.clone();
    }
}
