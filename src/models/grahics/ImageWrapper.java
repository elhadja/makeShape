package models.grahics;

import models.shape.Point;

public class ImageWrapper {
    private final ImageWrapperImpl implementer;
    private Point location;
    private double width;
    private double height;
    private final String name;

    public ImageWrapper(Point location, String name, double width, double height) {
        implementer = ImageWrapperAbsFactory.getInstance().createImplementer();
        this.location = location.clone();
        this.width = width;
        this.height = height;
        this.name = name;
    }

    public void draw() {
        implementer.draw(name, location, width, height);
    }

    public boolean isAt(Point location) {
        return this.location.getX()<=location.getX() && this.location.getX()+width>=location.getX() &&
                this.location.getY()<=location.getY() && this.location.getY()+width>=location.getY();
    }

    public Point getLocation() {
        return location.clone();
    }
}
