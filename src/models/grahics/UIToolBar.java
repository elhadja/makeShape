package models.grahics;

import models.shape.*;
import models.shape.ShapeAbstractFactory;
import javafx.scene.paint.Color;

import java.util.List;

public class UIToolBar extends UIComponentShapeManager implements Cloneable {
    public static final  String filename = "toolbarFile";
    private final ImageWrapper trash;
    private double nextAvailableLocationInY = getLocation().getY() + 5;
    private final double trashSize = 40;

    /**
     * create a toolbar by loading it from a file. If there is no file,
     * a default shapes will be added (@see addDefaultShapes)
     * @param location the location of the toolbar
     * @param width    the toolbar's width
     * @param height   the toolbar's height
     */
    public UIToolBar(Point location, double width, double height) {
        super(location, width, height);
        load(filename);
        if (getShapeComposite().getSize() == 0) {
            addDefaultShapes();
            save(filename);
        }else {
            computeLocationFor(getShapeComposite());
        }
        ImageWrapperAbsFactory factory = ImageWrapperAbsFactory.getInstance();
        trash = factory.createImage("trash.png",
                new Point(location.getX() + 10, location.getY() + height - 40),
                30,
                30 );
    }

    /**
     * add defaults shapes to the toolbar (when thre is no file to load).
     */
    private void addDefaultShapes() {
        ShapeAbstractFactory factory = ShapeAbstractFactory.getInstance();
        addShapeAt(factory.createCircle(new Point(170, 70), 80, Color.GREEN), new Point(0, 0));
        addShapeAt(factory.createRectangle(new Point(200, 200),40,90, Color.BLUE), new Point(0, 0));
        addShapeAt(factory.createRoundRect(new Point(400, 100),70,50,
                50, 20, Color.ORANGE), new Point(0, 0));
        addShapeAt(factory.createPolygon(new Point(400, 250),70, 10, Color.RED), new Point(0, 0));
        addShapeAt(factory.createPolygon(new Point(270, 400),60, 5, Color.INDIGO), new Point(0, 0));
        addShapeAt(factory.createPolygon(new Point(270, 400),60, 3, Color.INDIGO), new Point(0, 0));
    }

    @Override
    public void draw() {
        super.draw();
        getShapeComposite().draw();
        trash.draw();
        getImplementer().drawFrame(new Point(getLocation().getX(), nextAvailableLocationInY),
                getWidth(),
                2,
                true);
    }

    @Override
    public void removeShape(List<Shape> shapeList) {
        super.removeShape(shapeList);
        nextAvailableLocationInY = getLocation().getY() + 5;
    }

    @Override
    public void addShapesAt(Point location, List<Shape> shapes) {
        if (isTrashIsAt(location)) {
            removeShape(shapes);
            nextAvailableLocationInY = getLocation().getY() + 5;
            for (int i=0; i<getShapeComposite().getSize(); i++)
                computeLocationFor(getShapeComposite().getChildren(i));
        }
        else {
            super.addShapesAt(location, shapes);
        }
    }

    @Override
    public boolean addShapeAt(Shape shape, Point location) {
        double nextLocationCopy = nextAvailableLocationInY;
        shape = computeSize(shape);
        computeLocationFor(shape);
        if (shape.isIn(getLocation(), getWidth(), getHeight()- trashSize + 2))
            addShape(shape);
        else
        {
            nextAvailableLocationInY = nextLocationCopy;
            getImplementer().showDialogError("Some shapes doesn't added to the Toolbar",
                    "There is no place available\n" +
                            "tip: delete another shape in the toolbar and try again");
            return false;
        }
        return true;
    }

    /**
     * set the @param shape's location to the available location in the toolbar
     * and then actualize the next available location
     * @param shape the shape to set the location
     */
    private void computeLocationFor(Shape shape) {
        final double marginBetweenShapes = 10;
        final double marginLeft = 15;
        shape.setTopLeft(new Point(getLocation().getX() + marginLeft, nextAvailableLocationInY));
        nextAvailableLocationInY += shape.getHeightFromTopLeft() + marginBetweenShapes;
    }

    /**
     * compute the size for a shape to allow it to have a place int the toolbar
     * @param shape the shape to compute the size
     * @return the new shape
     */
    private Shape computeSize(Shape shape) {
        if (shape instanceof ShapeComposite)
            return computeSizeForShapeComposite(shape);
        return computeSizeForSimpleShape(shape);
    }

    /**
     * computes new size for the simple shape (rectangle, circle, polygon) to allow it to have a place in
     * the toolbar
     * @param shape the shape to compute size
     * @return the new shape
     */
    private Shape computeSizeForSimpleShape(Shape shape) {
        final int padding = 50;
        if (shape instanceof Polygon) {
            while (shape.getWidthFromTopLeft() > getWidth() - padding)
                ((Polygon)shape).setRadius(((Polygon) shape).getRadius()-1);
        } else if (shape instanceof Rectangle) {
            while (shape.getWidthFromTopLeft() > getWidth() - padding)
                ((Rectangle) shape).setWidth(((Rectangle) shape).getWidth() - 1);
            ((Rectangle) shape).setHeight(((Rectangle) shape).getWidth());
        } else if (shape instanceof Circle) {
            while (shape.getWidthFromTopLeft() > getWidth() - padding)
                ((Circle)shape).setRadius(((Circle) shape).getRadius()-1);
        } else if (shape instanceof ShapeComposite) {
            for (int i=0; i<shape.getSize(); i++)
                computeSizeForSimpleShape(shape.getChildren(i));
        }
        else{
            throw new UnsupportedOperationException("Toolbar.computeSize: unknown shape");
        }
        return shape;
    }

    /**
     * compute the size for a ShapeComposite to allow it to have a place in the toolbar
     * @param shape the shape Composite to compute the size
     * @return the new shape Composite
     */
    private Shape computeSizeForShapeComposite(Shape shape) {
        Shape shapeClone = shape.Clone();
        setMinSize(shapeClone);

        int minShapeX = getMinX(shape);
        int minShapeY = getMinY(shape);
        double xMin = shape.getChildren(minShapeX).getTopLeft().getX();
        double yMin = shape.getChildren(minShapeY).getTopLeft().getY();
        double xRef = xMin + shape.getChildren(minShapeX).getTopLeft().getX();
        double yRef = yMin + shape.getChildren(minShapeY).getTopLeft().getY();

        // reduce distances between shapes and place them in the same way
        // they where placed before
        for (int i=0; i<shapeClone.getSize(); i++) {
            Shape child = shapeClone.getChildren(i);
            double newX = child.getTopLeft().getX();
            double newY = child.getTopLeft().getY();
            if (xMin != child.getTopLeft().getX()) {
                double dist = shape.getChildren(i).getTopLeft().getX() - xRef;
                if (dist > 0)
                    newX = shapeClone.getChildren(minShapeX).getTopLeft().getX() +
                        shapeClone.getChildren(minShapeX).getWidthFromTopLeft() + dist;
            }
            if (yMin != child.getTopLeft().getY()) {
                double dist = shape.getChildren(i).getTopLeft().getY() - yRef;
                if (dist > 0)
                newY = shapeClone.getChildren(minShapeY).getTopLeft().getY() +
                        shapeClone.getChildren(minShapeY).getHeightFromTopLeft() + dist;
            }
            child.setTopLeft(new Point(newX, newY));
        }
        return shapeClone;
    }

    /**
     * seek the index of the shape witch has the minimum abscissa
     * @param shapeComposite a group of shapes
     * @return the index of the shape witch has the minimum abscissa
     */
    private int getMinX(Shape shapeComposite) {
        int indexMinX = 0;
        for (int i=0; i<shapeComposite.getSize(); i++) {
            Shape child = shapeComposite.getChildren(i);
            if (child.getTopLeft().getX() < indexMinX) {
                indexMinX = i;
            }
        }
        return indexMinX;
    }

    /**
     * seek the index of the shape witch has the minimum ordinate
     * @param shapeComposite a group of shapes
     * @return the index of the shape witch has the minimum ordinate
     */
    private int getMinY(Shape shapeComposite) {
        int indexMinY = 0;
        for (int i=0; i<shapeComposite.getSize(); i++) {
            Shape child = shapeComposite.getChildren(i);
            if (child.getTopLeft().getY() < indexMinY) {
                indexMinY = i;
            }
        }
        return indexMinY;
    }

    /**
     * minimize the size of a shape
     * @param shape the shape to minimise
     */
    private void setMinSize(Shape shape) {
        final double minSize = 15;
        if (shape instanceof Polygon) {
            ((Polygon)shape).setRadius(minSize/2);
        } else if (shape instanceof Rectangle) {
                ((Rectangle) shape).setWidth(minSize);
            ((Rectangle) shape).setHeight(minSize);
        } else if (shape instanceof Circle) {
                ((Circle)shape).setRadius(minSize);
        } else if (shape instanceof ShapeComposite) {
            for (int i=0; i<shape.getSize(); i++)
                setMinSize(shape.getChildren(i));
        }
    }

    @Override
    public Object Clone() {
        UIToolBar temp = null;
        try {
            temp = (UIToolBar) super.clone();
            temp.setShapeComposite(getShapeComposite());
        }catch (Exception ignored) {

        }
        return temp;
    }

    private boolean isTrashIsAt(Point point) {
        return getLocation().getY() + getHeight() - trashSize <= point.getY();
    }
}
