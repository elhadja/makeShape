package models.grahics;

import models.shape.Point;
import models.shape.Shape;
import models.shape.ShapeAbstractFactory;
import models.shape.ShapeComposite;
import Utils.Serializer;
import exceptions.SerializeException;

import java.util.ArrayList;
import java.util.List;

public abstract class UIComponentShapeManager extends UIComponentAbstract implements Cloneable{
    private Shape shapeComposite;

    public UIComponentShapeManager(Point location, double width, double height) {
       super(location, width, height);
       shapeComposite = ShapeAbstractFactory.getInstance().createShapeGroup();
    }

    /**
     * save the component in a filename
     * @param filename the name of the file to save the component
     */
    public void save(String filename) {
        Serializer.serialize(filename, this.shapeComposite);
    }

    /**
     * load a component from a filename
     * @param filename the name of the file to load
     */
    public void load(String filename) {
        try {
            shapeComposite = (Shape) Serializer.deserialize(filename);
        } catch (SerializeException ignored) {

        }
    }

    /**
     * add a shape in the component
     * @param shape the shape to add
     */
    public void addShape(Shape shape) {
        shapeComposite.addShape(shape);
    }

    /**
     * remove a set of shapes from a component
     * @param shapeList the list of the shape(s) to remove
     */
    public void removeShape(List<Shape> shapeList) {
        if (shapeList.size() == 1 && shapeList.get(0) == shapeComposite){
            while(shapeComposite.getSize()>0) {
                shapeComposite.removeShape(shapeComposite.getChildren(0));
            }
            return;
        }
        for (Shape shape : shapeList) {
            shape.getParent().removeShape(shape);
        }
    }

    /**
     * select the shape at @param point.
     * If the shape is already selected, it will be deselected
     * @param point the location to check
     */
    public void onClick(Point point) {
        shapeComposite.selectIfContains(point);
    }

    /**
     * select the shape at @param location
     * @param location the location to check
     */
    public void selectShapeAt(Point location) {
        List<Shape> selectedShapes = new ArrayList<>();
        shapeComposite.addSelectedShapesTo(selectedShapes);
        for (Shape shape : selectedShapes) {
            if (shape.isAt(location)) {
                shape.setSelected(true);
                return;
            }
        }
        shapeComposite.selectIfContains(location);
    }

    /**
     * make a group of shapes
     * The shapes to group must have the same parent. Otherwise an
     * error will be sent to the user
     */
    public void groupShapes() {
        ShapeComposite newShapeComposite = (ShapeComposite) ShapeAbstractFactory.getInstance().createShapeGroup();
        List<Shape> selectedShapes = new ArrayList<>();
        shapeComposite.addSelectedShapesTo(selectedShapes);

        if (selectedShapes.size() > 0){
            Shape newParent;
            if(haveSameParent(selectedShapes))
                newParent = selectedShapes.get(0).getParent();
            else {
                getImplementer().showDialogError("Unsupported Operation"
                        , "shapes must have the same parent");
                return;
            }

            for (Shape shape : selectedShapes) {
                Shape parent = shape.getParent();
                parent.removeShape(shape);
                newShapeComposite.addShape(shape);
            }

            newParent.addShape(newShapeComposite);
            newShapeComposite.setSelected(false);
        }
    }

    /**
     * check whether shapes in @param list have the same parent
     * @param list contains shapes to check
     * @return true if shapes in @param list have the same parent (false otherwise);
     */
    private boolean haveSameParent(List<Shape> list) {
        for (int i=0; i<list.size()-1; i++) {
            if (list.get(i).getParent() != list.get(i+1).getParent())
                return false;
        }
        return true;
    }

    /**
     * undo a group of shapes by extracting them from their parent to their grand parent
     * The shapes to ungroup must have the same parent. Otherwise an
     * error will be sent to the user
     */
    public void ungroupShapes() {
        List<Shape> selectedShapes = new ArrayList<>();
        shapeComposite.addSelectedShapesTo(selectedShapes);
        if (haveSameParent(selectedShapes) && selectedShapes.size() > 0) {
            List<Shape> helpList = new ArrayList<>();
            for (Shape shape : selectedShapes) {
                if (shape instanceof ShapeComposite) {
                    for (int j = 0; j < shape.getSize(); j++)
                        helpList.add(shape.getChildren(j));
                } else
                    helpList.add(shape);
            }
            Shape parent = helpList.get(0).getParent();
            Shape grandParent = (parent == null) ? null : parent.getParent();
            if(grandParent != null) {
                for (Shape selectedShape : helpList) {
                    selectedShape.setSelected(false);
                    if (selectedShape.getParent().getSize() == 1)
                        selectedShape.getParent().getParent().removeShape(selectedShape.getParent());
                    selectedShape.getParent().removeShape(selectedShape);
                    grandParent.addShape(selectedShape);
                }
            }
        }
        else {
            getImplementer().showDialogError("Unsupported Operation"
                    , "shapes must have the same parent");
        }
    }

    /**
     * rotate the selected shapes of the component.
     */
    public void rotateShapes() {
        List<Shape> selectedShapes = new ArrayList<>();
        shapeComposite.addSelectedShapesTo(selectedShapes);
        for (Shape shape: selectedShapes)
            shape.rotate(10);
    }

    /**
     * add all selected of the component in a list
     * @param shapeList the list where to add the selected shapes
     */
    public void addSelectedShapesTo(List<Shape> shapeList) {
        shapeComposite.addSelectedShapesTo(shapeList);
    }

    /**
     * add a list of shapes in the component.
     * @param location where the first shape in the list will be added.
     *                 other shapes of the list will be added bellow the first
     * @param shapes the list of shapes to add
     */
    public void addShapesAt(Point location, List<Shape> shapes) {
        for (Shape shape: shapes) {
            shape.setSelected(false);
            if(!addShapeAt(shape.Clone(), location))
                break;
        }
    }

    /**
     * select the shapes of the component witch is inside an area
     * @param topLeft the topLeft of the area
     * @param width   the width of the area
     * @param height  the height of the area
     */
    public void selectShapesIn(Point topLeft, double width, double height) {
        shapeComposite.selectIfIsIn(topLeft, width, height);
    }

    /**
     * add a shape to a component
     * @param shape the shape to add
     * @param location where the shape will be added
     * @return true if the is added and false otherwise
     */
    public abstract boolean addShapeAt(Shape shape, Point location);

    protected Shape getShapeComposite() {
        return shapeComposite;
    }

    protected void setShapeComposite(Shape shapeComposite) {
        this.shapeComposite = shapeComposite.Clone();
    }
}
