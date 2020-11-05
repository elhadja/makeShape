package test.unitTests.graphics;

import Utils.JavaFxJUnit4ClassRunner;
import models.grahics.JFX_UIComponentFactory;
import models.grahics.UIWhiteBoard;
import models.shape.*;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(JavaFxJUnit4ClassRunner.class)
public class UIWhiteBoardTest {
    UIWhiteBoard whiteboard;

    @Before
    public void initializeTests() {
        try {
            JFX_UIComponentFactory.initialize(null);
        }catch (Exception e) {
            // the factory is already initialized by another test
        }
    }

    @Test
    public void addShapeAt() {
        Shape rectangle = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
        Shape rectangle2 = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
        Shape rectangle3 = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
        Shape shapeComposite = new ShapeComposite();
        whiteboard = new UIWhiteBoard(new Point(0, 0),500, 500);

        shapeComposite.addShape(rectangle3);
        whiteboard.addShapeAt(rectangle, new Point(0, 20)); // right overflow
        whiteboard.addShapeAt(rectangle2, new Point(15, 20)); // right overflow
        whiteboard.addShapeAt(shapeComposite, new Point(30, 20)); // right overflow
        Shape whiteBoardShapeComposite = rectangle.getParent();

        assertEquals(3, whiteBoardShapeComposite.getSize());
        assertEquals(new Point(0, 20), whiteBoardShapeComposite.getChildren(0).getTopLeft());
        assertEquals(new Point(15, 20), whiteBoardShapeComposite.getChildren(1).getTopLeft());
        assertEquals(new Point(30, 20), whiteBoardShapeComposite.getChildren(2).getTopLeft());
    }

    @Test
    public void setSelectedShapesProperties() {
        Shape rectangle = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
        Shape circle = new Circle(new Point(0, 0), 5,  Color.WHITE);
        whiteboard = new UIWhiteBoard(new Point(0, 0),500, 500);

        whiteboard.addShapeAt(rectangle, new Point(5, 5));
        whiteboard.addShapeAt(circle, new Point(5, 5));
        Shape whiteBoardShapeComposite = rectangle.getParent();

        Shape rectangleToModify = whiteBoardShapeComposite.getChildren(0);
        Shape circleToModify = whiteBoardShapeComposite.getChildren(1);
        whiteBoardShapeComposite.setSelected(true);
        whiteboard.setSelectedShapesProperties(Color.BLUE, 40, 40, new Point(10, 10), 30, 3);

        assertEquals(rectangleToModify.getColor(), Color.BLUE);
        assertEquals(rectangleToModify.getWidthFromTopLeft(), 40, 1e-15);
        assertEquals(rectangleToModify.getHeightFromTopLeft(), 40, 1e-15);
        assertEquals(new Point(10, 10), rectangleToModify.getTopLeft());

        assertEquals(circleToModify.getColor(), Color.BLUE);
        assertEquals(((Circle)circleToModify).getRadius(), 30, 1e-15);
        assertEquals(new Point(10, 10), circleToModify.getTopLeft());
    }

    @Test
    public void setSelectedShapesProperties1() {
        Shape shape = new Circle(new Point(0, 0), 5,  Color.WHITE);
        whiteboard = new UIWhiteBoard(new Point(0, 0),500, 500);

        whiteboard.addShapeAt(shape, new Point(5, 5));
        Shape whiteBoardShapeComposite = shape.getParent();

        Shape shapeToModify = whiteBoardShapeComposite.getChildren(0);
        shapeToModify.setSelected(true);
        whiteboard.setSelectedShapesProperties(Color.BLUE, 0, 0, new Point(10, 10), 40, 0);

        assertEquals(shapeToModify.getColor(), Color.BLUE);
        assertEquals(shapeToModify.getWidthFromTopLeft(), 40, 1e-15);
        assertEquals(shapeToModify.getHeightFromTopLeft(), 40, 1e-15);
        assertEquals(new Point(10, 10), shapeToModify.getTopLeft());
    }
}