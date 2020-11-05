package models.grahics;

import models.shape.JfxShapeFactory;
import models.shape.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Editor {
    private static Editor instance;
    private final double width;
    private final double height;
    private final UIComponent menu;
    private UIComponentShapeManager toolBar;
    private UIComponentShapeManager whiteBoard;
    private final EditorImpl implementer;
    private final EditorMementoChecker mementoChecker = new EditorMementoChecker();

    private UIComponentShapeManager componentSourceForDragAndDrop;
    private Point dragNDropStartPoint;
    private boolean selectShapesByDragNDrop = false;


    private Editor(double width, double height, EditorImpl implementer) {
        this.width = width;
        this.height = height;

        createFactories(implementer);
        UIComponentAbstractFactory componentFactory = UIComponentAbstractFactory.getInstance();

        menu =  componentFactory.createComponent(ComponentEnum.MENU, new Point(5, 5), width, 50);
        toolBar = (UIComponentShapeManager) componentFactory.createComponent(ComponentEnum.TOOLBAR,
                new Point(5, 60),
                100,
                height-55);

        whiteBoard = (UIComponentShapeManager) componentFactory.createComponent(ComponentEnum.WHITEBOARD,
                new Point(110, 60),
                width - 110,
                height-55);

        this.implementer = implementer;
        mementoChecker.add(makeMemento());
    }

    /**
     * initialize all factories according to the @param implementer
     * @param implementer the implementer used by the editor
     */
    private void createFactories(EditorImpl implementer) {
        if (implementer instanceof JfxEditorImpl) {
            GraphicsContext graphicsContext = ((JfxEditorImpl) implementer).getGraphicsContext();

            JfxShapeFactory.initialize(graphicsContext);
            JFX_UIComponentFactory.initialize(graphicsContext);
            JfxImageFactory.initialize(graphicsContext);
        }else {
            throw new UnsupportedOperationException("unknown implementer");
        }
    }

    /**
     * initialize the editor by creating an instance
     * @param width the editor's width
     * @param height the editor's height
     * @param implementer the implementer used for the GUI
     */
    public static void initialize(double width, double height, EditorImpl implementer) {
        if(instance != null)
            throw new UnsupportedOperationException("The editor is already initialized");
        instance = new Editor(width, height, implementer);
    }

    public static Editor getInstance() {
        if (instance == null)
            throw new UnsupportedOperationException("The editor should be initialized first");
        return instance;
    }

    public void draw() {
        menu.draw();
        toolBar.draw();
        whiteBoard.draw();
    }

    /**
     * show the contextual menu
     * Normaly these two parameter below mean the same thing. But the contextual menu
     * location is about all the screen while the shapeLocation is  about the screen of the application
     * @param menuLocation the location where the contextual menu will be show
     * @param shapeLocation the location where the click happened
     */
    public void showContextualMenuAt(Point menuLocation, Point shapeLocation) {
        if (toolBar.isAt(shapeLocation)) {
            UIComponentAbstractFactory.getInstance().createImplementer().showDialogError("Unsupported operation",
                    "the contextual menu is not available for the toolbar");
        } else {
            whiteBoard.selectShapeAt(shapeLocation);
            getImplementer().showContextualMenu(menuLocation);
        }
    }

    private EditorImpl getImplementer() {
        return implementer;
    }

    /**
     * show the window for setting shapes properties
     */
    public void showPropertiesWindows() {
        getImplementer().showPropertiesWindow();
    }

    public void setSelectedShapesProperties(Color color,
                                            double width,
                                            double height,
                                            Point location,
                                            double radius,
                                            int numberSides) {
        ((UIWhiteBoard)whiteBoard).setSelectedShapesProperties(color, width, height, location, radius, numberSides);
        mementoChecker.add(makeMemento());
        whiteBoard.draw();
    }

    public void undo() {
        EditorMemento memento = mementoChecker.getPrevious();
        setMemento(memento);
        draw();
    }

    public void redo() {
        EditorMemento memento = mementoChecker.getNext();
        setMemento(memento);
        draw();
    }

    public void saveDocument() {
        getImplementer().saveDocument(whiteBoard);
    }

    public void loadDocument() {
        getImplementer().loadDocument(whiteBoard);
        whiteBoard.draw();
    }

    public void saveToolBar() {
        toolBar.save(UIToolBar.filename);
    }

    public void rotateShapes() {
        whiteBoard.rotateShapes();
        whiteBoard.draw();
    }

    public EditorMemento makeMemento() {
        return new EditorMemento(toolBar, whiteBoard);
    }

    /**
     * report that a click happened on the GUI and trigger the corresponding action
     * @param point where the click happened
     */
    public void onClick(Point point) {
            if (menu.isAt(point)) {
                    menu.onClick(point);
            }else {
                selectOneShape(point);
            }
            draw();
    }

    /**
     * select the shape at location @param point (if there is one). All other shapes will be deselected
     * @param point the location to check if there is a shape
     */
    private void selectOneShape(Point point) {
        UIComponentShapeManager component = getCorrespondingComponentAt(point);
        List<Shape> selectedShapes = new ArrayList<>();
        if (component != null) {
            component.addSelectedShapesTo(selectedShapes);
            for (Shape shape : selectedShapes) {
                if (shape.isAt(point))
                    return;
            }
            for (Shape shape : selectedShapes) {
                shape.setSelected(false);
            }
            component.selectShapeAt(point);
        }
    }

    public void groupShapes() {
        whiteBoard.groupShapes();
        mementoChecker.add(makeMemento());
        whiteBoard.draw();
    }

    public void ungroupShapes() {
        whiteBoard.ungroupShapes();
        mementoChecker.add(makeMemento());
        whiteBoard.draw();
    }

    /**
     * select a shape
     * If the shape is already selected then it will be deselected
     * @param shapeLocation where is the shape to select
     */
    public void addShapeToSelectedShapes(Point shapeLocation) {
            UIComponentShapeManager component = getCorrespondingComponentAt(shapeLocation);
            if (component != null) {
                component.onClick(shapeLocation);
            }
            draw();
    }

    /**
     * report to the editor that a drag and drop is detected
     * It detects if the drag and drop is to move shapes or to select shapes
     * @param location where the drag and drop start
     */
    public void startDragAndDropAt(Point location) {
        UIComponentShapeManager component = getCorrespondingComponentAt(location);

        if (component != null) {
            component.selectShapeAt(location);
            List<Shape> selectedShapes = new ArrayList<>();
            component.addSelectedShapesTo(selectedShapes);
            if (selectedShapes.size() == 0) {
                selectShapesByDragNDrop = true;
                dragNDropStartPoint = location;
            }
            componentSourceForDragAndDrop = component;
        }
    }

    /**
     * report that a drag and drop is finished
     * @param location where the drag and drop finished
     */
    public void endDragAndDropAt(Point location) {
        UIComponentShapeManager targetComponent = getCorrespondingComponentAt(location);

        if (targetComponent != null) {
            if (selectShapesByDragNDrop && (targetComponent == componentSourceForDragAndDrop)) {
                selectByDragAndDrop(componentSourceForDragAndDrop, location);
            }else{
                movesShapes(targetComponent, location);
            }
            draw();
        }
    }

    /**
     * select shapes witch are in the area of an drag and drop (a rectangular area from the start of the drag and drop
     * to its end).
     * @param component the component where the drag and drop ended
     * @param location  where the drag and drop ended
     */
    private void selectByDragAndDrop(UIComponentShapeManager component, Point location) {
        double x = dragNDropStartPoint.getX();
        double y = dragNDropStartPoint.getY();
        double width = location.getX() - dragNDropStartPoint.getX();
        double height = location.getY() - dragNDropStartPoint.getY();
        if (width < 0) {
            width = Math.abs(width);
           x = x - width;
        }
        if (height < 0) {
            height = Math.abs(height);
            y = y - height;
        }
        component.selectShapesIn(new Point(x, y), width, height);
        selectShapesByDragNDrop = false;
    }

    /**
     * show dynamically the selection of the shapes when the user move the mouse by drawing a rectangular frame
     * @param location the current location of the mouse
     */
    public void actualizeScreen(Point location) {
        UIComponentShapeManager targetComponent = getCorrespondingComponentAt(location);
        if (targetComponent == null)
            return;
        if(selectShapesByDragNDrop && targetComponent==componentSourceForDragAndDrop) {
            double x = dragNDropStartPoint.getX();
            double y = dragNDropStartPoint.getY();
            double width = location.getX() - dragNDropStartPoint.getX();
            double height = location.getY() - dragNDropStartPoint.getY();
            if (width < 0) {
                width = Math.abs(width);
                x = x - width;
            }
            if (height < 0) {
                height = Math.abs(height);
                y = y - height;
            }
            componentSourceForDragAndDrop.draw();
            UIComponentAbstractFactory.getInstance().createImplementer().drawFrame(new Point(x, y), width, height, false);
        }
    }

    /**
     * move shapes from a component to another component (by copy). The components can be the same
     * @param targetComponent the component where to move selected shapes
     * @param newLocation the new location for the moved shapes
     */
    private void movesShapes(UIComponentShapeManager targetComponent, Point newLocation) {
        List<Shape> selectedShapes = new ArrayList<>();
        componentSourceForDragAndDrop.addSelectedShapesTo(selectedShapes);
        targetComponent.addShapesAt(newLocation, selectedShapes);
        mementoChecker.add(makeMemento());
    }

    /**
     * check the UI componentShapeManager (Toolbar and whiteboard) at the @param location
     * @param location where to check if the component is
     * @return the corresponding component or null if no one is at @param location
     */
    private UIComponentShapeManager getCorrespondingComponentAt(Point location) {
        if (whiteBoard.isAt(location))
            return whiteBoard;
        else if (toolBar.isAt(location))
            return toolBar;
        else
            return null;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    private void setMemento(EditorMemento memento) {
        if (memento != null) {
            toolBar = (UIComponentShapeManager) memento.getToolBar();
            whiteBoard = (UIComponentShapeManager) memento.getWhiteBoard();
        }
    }
}
