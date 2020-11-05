package test.unitTests.shape;

import models.shape.JfxShapeFactory;
import models.shape.Rectangle;
import models.shape.Point;
import models.shape.ShapeAbstractFactory;
import Utils.Serializer;
import exceptions.SerializeException;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class RectangleTest {
    private final Point mainPoint = new Point(7,7);
    private ShapeAbstractFactory factory;

    @Before
    public void initializeTests() {
        try {
            JfxShapeFactory.initialize(null);
        }catch (Exception e) {
            // the factory is already initialized by another test
        }
        factory = ShapeAbstractFactory.getInstance();
    }

    @Test
    public void pointShouldBeRotatable() {
        double width = 21;
        Rectangle rectangle1 = (Rectangle) factory.createRectangle(mainPoint, 21, 10, Color.ORANGE);
        rectangle1.rotate(0);
        assertEquals(width, rectangle1.getHeight(), 0.00001);
    }

    @Test
    public void rectangleShouldBeTranslatable() {
        Rectangle rectangle1 = (Rectangle) factory.createRectangle(mainPoint, 21, 10, Color.ORANGE);
        rectangle1.translate(1 ,0);
        Point point = new Point (8, 7);
        assertEquals(point, rectangle1.getTopLeft());
    }

    @Test
    public void rectangleShouldBeIsAt() {
        Rectangle rectangle = (Rectangle) factory.createRectangle(mainPoint, 21, 10, Color.ORANGE);
        Point point = new Point(0,0);
        assertFalse(rectangle.isAt(point));
    }

    @Test
    public void rectangleShouldBeEqual() {
        Rectangle rectangle1 = (Rectangle) factory.createRectangle(mainPoint, 21, 10, Color.ORANGE);
        Rectangle rectangle2 = (Rectangle) factory.createRectangle(mainPoint, 21, 10, Color.ORANGE);
        assertEquals(rectangle1, rectangle2);
    }

    @Test
    public void rectangleShouldBeCloneable() {
        Rectangle rectangle1 = (Rectangle) factory.createRectangle(mainPoint, 21, 10, Color.ORANGE);
        Rectangle rectangle2 = rectangle1.Clone();
        rectangle1.setColor(Color.BLUE);
        assertNotEquals(rectangle1, rectangle2);
    }

    @Test
    public void rectangleShouldBeSerializable() {
        Rectangle rectangle1 = (Rectangle) factory.createRectangle(mainPoint, 21, 10, Color.ORANGE);
        String filename = "rectangle.test";
        Serializer.serialize(filename, rectangle1);
        try {
            Rectangle rectangle2 = (Rectangle) Serializer.deserialize(filename);
            assertEquals(rectangle1, rectangle2);
        } catch (SerializeException e) {
            e.printStackTrace();
        }
    }
}
