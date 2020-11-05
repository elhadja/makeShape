package test.unitTests.graphics;

import models.grahics.UIComponentShapeManager;
import models.grahics.UIWhiteBoard;
import models.shape.JfxShapeFactory;
import models.shape.Point;
import models.shape.Rectangle;
import models.shape.Shape;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UIComponentShapeManagerTest {

    UIComponentShapeManager whiteboard;

    @Before
    public void initializeTests() {
        try {
            JfxShapeFactory.initialize(null);
        }catch (Exception e) {
            // the factory is already initialized by another test
        }
        whiteboard = new UIWhiteBoard(new Point(0, 0),500, 500);
    }

    /**
     * groupShapes tests that the Whiteboard's main ShapeComposite can be grouped.
     * this case happen when all shapes in the shapeComposite is selected
     */
    @Test
    public void groupShapes() {
        Shape rectangle1 = new Rectangle(new Point(5,5), 100, 50, Color.WHITE);
        Shape rectangle2 = new Rectangle(new Point(6,6), 100, 50, Color.WHITE);

        whiteboard.addShape(rectangle1);
        whiteboard.addShape(rectangle2);
        Shape previousParent = rectangle1.getParent();
        previousParent.setSelected(true);
        whiteboard.groupShapes();

        assertEquals(rectangle1.getParent(), rectangle2.getParent());
        assertSame(rectangle1.getParent(), previousParent.getChildren(0));
    }



    /**
     * groupShapes1 tests if two shapes (rectangle1 and rectangle2)
     * can be grouped together
     */
    @Test
    public void groupShapes1() {
        Shape rectangle1 = new Rectangle(new Point(5,5), 100, 50, Color.WHITE);
        Shape rectangle2 = new Rectangle(new Point(6,6), 100, 50, Color.WHITE);
        Shape rectangle3 = new Rectangle(new Point(6,6), 100, 50, Color.WHITE);

        whiteboard.addShape(rectangle1);
        whiteboard.addShape(rectangle2);
        whiteboard.addShape(rectangle3);
        rectangle1.setSelected(true);
        rectangle2.setSelected(true);
        whiteboard.groupShapes();

        assertEquals(rectangle1.getParent(), rectangle2.getParent());
        assertNotEquals(rectangle1.getParent(), rectangle3.getParent());
        assertEquals(rectangle1.getParent().getParent(), rectangle3.getParent());
    }

    /**
     * groupShapes2 test if two groups of shapes can be grouped together
     */
    @Test
    public void groupShapes2() {
        Shape rectangle1 = new Rectangle(new Point(5,5), 100, 50, Color.WHITE);
        Shape rectangle2 = new Rectangle(new Point(6,6), 100, 50, Color.WHITE);
        Shape rectangle3 = new Rectangle(new Point(6,6), 100, 50, Color.WHITE);
        Shape rectangle4 = new Rectangle(new Point(6,6), 100, 50, Color.WHITE);
        Shape rectangle5 = new Rectangle(new Point(6,6), 100, 50, Color.WHITE);

        whiteboard.addShape(rectangle1);
        whiteboard.addShape(rectangle2);
        whiteboard.addShape(rectangle3);
        whiteboard.addShape(rectangle4);
        whiteboard.addShape(rectangle5);

        rectangle1.setSelected(true);
        rectangle2.setSelected(true);
        whiteboard.groupShapes();
        rectangle1.setSelected(false);
        rectangle2.setSelected(false);

        rectangle3.setSelected(true);
        rectangle4.setSelected(true);
        whiteboard.groupShapes();


        assertSame(rectangle1.getParent(), rectangle2.getParent());
        assertSame(rectangle3.getParent(), rectangle4.getParent());
        assertNotSame(rectangle1.getParent(), rectangle3.getParent());
        assertSame(rectangle1.getParent().getParent(), rectangle3.getParent().getParent());
    }

    /**
     * groupShapes3 tests that if we attempt to group nothing (when there is no selected shapes)
     * so nothing happens (shapes will not be modified)
     */
    @Test
    public void groupShapes3() {
        Shape rectangle1 = new Rectangle(new Point(5,5), 100, 50, Color.WHITE);
        Shape rectangle2 = new Rectangle(new Point(6,6), 100, 50, Color.WHITE);

        whiteboard.addShape(rectangle1);
        whiteboard.addShape(rectangle2);
        Shape previousParent = rectangle1.getParent();
        whiteboard.groupShapes();

        assertEquals(rectangle1.getParent(), rectangle2.getParent());
        assertEquals(rectangle1.getParent(), previousParent);
    }



    /**
     * ungroupShapes tests that if we attempt to ungroup shapes which have not the
     * same parent, then nothing happens.
     */
    @Test
    public void ungroupShapes() {
        Shape rectangle1 = new Rectangle(new Point(5,5), 100, 50, Color.WHITE);
        Shape rectangle2 = new Rectangle(new Point(6,6), 100, 50, Color.WHITE);
        Shape rectangle3 = new Rectangle(new Point(6,6), 100, 50, Color.WHITE);
        Shape rectangle4 = new Rectangle(new Point(6,6), 100, 50, Color.WHITE);
        UIComponentShapeManager whiteboard = new UIWhiteBoard(new Point(0, 0),500, 500);

        whiteboard.addShape(rectangle1);
        whiteboard.addShape(rectangle2);
        whiteboard.addShape(rectangle3);
        whiteboard.addShape(rectangle4);

        rectangle1.setSelected(true);
        rectangle2.setSelected(true);
        whiteboard.groupShapes();
        rectangle1.setSelected(false);
        rectangle2.setSelected(false);

        rectangle3.setSelected(true);
        rectangle4.setSelected(true);
        whiteboard.groupShapes();
        rectangle3.setSelected(false);
        rectangle4.setSelected(false);

        Shape rectangle1Parent = rectangle1.getParent();
        Shape rectangle3PrevParent = rectangle3.getParent();
        rectangle1.setSelected(true);
        rectangle3.setSelected(true);
        try {
            whiteboard.ungroupShapes();
            fail();
        }catch (IllegalStateException e){
            assertSame(rectangle1.getParent(), rectangle1Parent);
            assertSame(rectangle3.getParent(), rectangle3PrevParent);
        }
    }


    /**
     * ungroupShapes1 tests that it's possible to ungroup just one shape from a group of shapes
     */
    @Test
    public void ungroupShapes1() {
        Shape rectangle1 = new Rectangle(new Point(5,5), 100, 50, Color.WHITE);
        Shape rectangle2 = new Rectangle(new Point(6,6), 100, 50, Color.WHITE);
        Shape rectangle3 = new Rectangle(new Point(6,6), 100, 50, Color.WHITE);
        UIComponentShapeManager whiteboard = new UIWhiteBoard(new Point(0, 0),500, 500);

        whiteboard.addShape(rectangle1);
        whiteboard.addShape(rectangle2);
        whiteboard.addShape(rectangle3);

        rectangle1.setSelected(true);
        rectangle2.setSelected(true);
        whiteboard.groupShapes();
        rectangle1.setSelected(false);
        rectangle2.setSelected(false);

        rectangle1.setSelected(true);
        whiteboard.ungroupShapes();

        assertSame(rectangle1.getParent(), rectangle3.getParent());
        assertNotSame(rectangle1.getParent(), rectangle2.getParent());
    }

    /**
     * tests that's passible to ungroup a shapeComposite by extracting its children
     */
    @Test
    public void ungroupShapes2() {
        Shape rectangle1 = new Rectangle(new Point(5,5), 100, 50, Color.WHITE);
        Shape rectangle2 = new Rectangle(new Point(6,6), 100, 50, Color.WHITE);
        Shape rectangle3 = new Rectangle(new Point(6,6), 100, 50, Color.WHITE);
        UIComponentShapeManager whiteboard = new UIWhiteBoard(new Point(0, 0),500, 500);

        whiteboard.addShape(rectangle1);
        whiteboard.addShape(rectangle2);
        whiteboard.addShape(rectangle3);

        Shape root = rectangle1.getParent();

        rectangle1.setSelected(true);
        rectangle2.setSelected(true);
        rectangle3.setSelected(true);

        whiteboard.groupShapes();

        rectangle1.setSelected(true);
        rectangle2.setSelected(true);
        rectangle3.setSelected(true);

        whiteboard.ungroupShapes();

        assertSame(rectangle1.getParent(), rectangle2.getParent());
        assertSame(rectangle1.getParent(), rectangle3.getParent());
        assertSame(rectangle1.getParent(), root);
    }

    /**
     * tests that if we try to ungroup a root shape (a shapeGroup shape with no parent)
     * then nothing happen
     */
    @Test
    public void ungroupShapes3() {
        Shape rectangle1 = new Rectangle(new Point(5,5), 100, 50, Color.WHITE);
        Shape rectangle2 = new Rectangle(new Point(6,6), 100, 50, Color.WHITE);
        UIComponentShapeManager whiteboard = new UIWhiteBoard(new Point(0, 0),500, 500);

        whiteboard.addShape(rectangle1);
        whiteboard.addShape(rectangle2);

        Shape root = rectangle1.getParent();

        rectangle1.setSelected(true);
        rectangle2.setSelected(true);

        whiteboard.ungroupShapes();

        assertSame(rectangle1.getParent(), rectangle2.getParent());
        assertSame(rectangle1.getParent(), root);
    }
}