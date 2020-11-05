package test.unitTests.shape;

import models.shape.Point;
import models.shape.Rectangle;
import models.shape.Shape;
import models.shape.ShapeComposite;
import javafx.scene.paint.Color;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ShapeAbstractTest {

    @Test
    public void selectIfContains() {
        Shape rectangle = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
        Shape rectangle2 = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);

        rectangle.setSelected(false);
        rectangle2.setSelected(true);
        rectangle.selectIfContains(new Point(2, 2));
        rectangle2.selectIfContains(new Point(2, 2));
        rectangle2.selectIfContains(new Point(15, 15));

        assertTrue(rectangle.isSelected());
        assertFalse(rectangle2.isSelected());
    }

    @Test
    public void addSelectedShapesTo() {
        Shape rectangle = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
        Shape rectangle2 = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);

        rectangle.setSelected(true);
        rectangle2.setSelected(false);
        List<Shape> shapeList = new ArrayList<>();
        rectangle.addSelectedShapesTo(shapeList);
        rectangle2.addSelectedShapesTo(shapeList);

        assertEquals(1, shapeList.size());
        assertSame(rectangle, shapeList.get(0));
    }

    @Test
    public void selectGroupOf() {
        Shape rectangle = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
        Shape rectangle2 = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
        Shape rectangle3 = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
        Shape shapeComposite = new ShapeComposite();

        shapeComposite.addShape(rectangle);
        shapeComposite.addShape(rectangle2);
        rectangle.selectGroupOf(rectangle2);
        rectangle2.selectGroupOf(rectangle);
        rectangle2.selectGroupOf(rectangle3);

        assertTrue(rectangle.isSelected());
        assertTrue(rectangle2.isSelected());
        assertFalse(rectangle3.isSelected());
    }

    @Test
    public void isAt() {
        Shape rectangle = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);

        assertTrue(rectangle.isAt(new Point(0, 0)));
        assertTrue(rectangle.isAt(new Point(5, 5)));
        assertTrue(rectangle.isAt(new Point(2, 2)));
        assertFalse(rectangle.isAt(new Point(6, 6)));
        assertFalse(rectangle.isAt(new Point(15, 0)));
    }

    @Test
    public void isIn() {
        Shape rectangle = new Rectangle(new Point(10, 10), 5, 5, Color.WHITE);

        assertTrue(rectangle.isIn(new Point(10, 10), 5, 5));
        assertTrue(rectangle.isIn(new Point(5, 5), 20, 20));
        assertFalse(rectangle.isIn(new Point(10, 9), 5, 5));
        assertFalse(rectangle.isIn(new Point(9, 10), 5, 5));
        assertFalse(rectangle.isIn(new Point(10, 16), 5, 5));
    }

    @Test
    public void select() {
        Shape rectangle = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
        assertFalse(rectangle.isSelected());
        rectangle.select();
        assertTrue(rectangle.isSelected());
        rectangle.select();
        assertFalse(rectangle.isSelected());
    }

    @Test
    public void getChildren() {
       Shape rectangle = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
       try {
           rectangle.getChildren(0);
           fail();
       }catch (UnsupportedOperationException ignored) {
       }
    }

    @Test
    public void getSize() {
        Shape rectangle = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
        try {
            rectangle.getSize();
            fail();
        }catch (UnsupportedOperationException ignored) {
        }
    }

    @Test
    public void getTopLeftPoint() {
        Shape rectangle = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
        assertEquals(rectangle.getTopLeft(), new Point(0, 0));
    }

    @Test
    public void setSelected() {
        Shape rectangle = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
        Shape rectangle2 = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);

        rectangle.setSelected(true);
        rectangle2.setSelected(false);

        assertTrue(rectangle.isSelected());
        assertFalse(rectangle2.isSelected());
    }

    @Test
    public void isSelected() {
        Shape rectangle = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
        assertFalse(rectangle.isSelected());
        rectangle.setSelected(true);
        assertTrue(rectangle.isSelected());
    }

    @Test
    public void getMainPoint() {
        Shape rectangle = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
        assertEquals(rectangle.getTopLeft(), new Point(0, 0));
    }

    @Test
    public void setMainPoint() {
        Shape rectangle = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
        rectangle.setTopLeft(new Point(5, 5));

        assertEquals(rectangle.getTopLeft(), new Point(5, 5));
        try {
            new Rectangle(new Point(-1,  0), 5, 6, Color.WHITE);
            fail();
        } catch (IllegalArgumentException ignored) {

        }
    }

    @Test
    public void getColor() {
        Shape rectangle = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);

        assertEquals(Color.WHITE, rectangle.getColor());
        assertNotEquals(Color.RED, rectangle.getColor());
    }

    @Test
    public void setColor() {
        Shape rectangle = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
        rectangle.setColor(Color.ORANGE);

        assertEquals(Color.ORANGE, rectangle.getColor());
        assertNotEquals(Color.WHITE, rectangle.getColor());
    }

    @Test
    public void getParent() {
        Shape rectangle = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
        Shape rectangle2 = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
        Shape shapeComposite = new ShapeComposite();

        shapeComposite.addShape(rectangle2);

        assertNull(rectangle.getParent());
        assertEquals(rectangle2.getParent(), shapeComposite);

    }

    @Test
    public void setParent() {
        Shape rectangle = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
        Shape rectangle2 = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
        Shape shapeComposite = new ShapeComposite();

        shapeComposite.addShape(rectangle2);
        rectangle.setParent(shapeComposite);
        rectangle2.setParent(null);

        assertNull(rectangle2.getParent());
        assertEquals(rectangle.getParent(), shapeComposite);
    }

    @Test
    public void testEquals() {
        Shape rectangle = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
        Shape rectangle2 = new Rectangle(new Point(5, 0), 5, 5, Color.WHITE);
        Shape rectangle3 = new Rectangle(new Point(0, 0), 5, 5, Color.RED);

        assertEquals(rectangle, rectangle2);
        assertNotEquals(rectangle, rectangle3);
    }
}