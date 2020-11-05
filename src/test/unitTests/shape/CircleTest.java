package test.unitTests.shape;

import models.shape.Circle;
import models.shape.JfxShapeFactory;
import models.shape.Point;
import models.shape.ShapeAbstractFactory;
import Utils.Serializer;
import exceptions.SerializeException;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class CircleTest {
    private final Point center = new Point(7,7);
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
    public void circleShouldBeTranslatable() {
        Circle circle1 = (Circle) factory.createCircle(center, 21, Color.ORANGE);
        circle1.translate(1 ,0);
        Point point = new Point (8, 7);
        assertEquals(point, circle1.getTopLeft());
    }

    @Test
    public void circleShouldBeIsAt() {
        Circle circle = (Circle) factory.createCircle(center, 21, Color.ORANGE);
        Point point = new Point(0,0);
        assertFalse(circle.isAt(point));
    }

    @Test
    public void circleShouldBeEqual() {
        Circle circle1 = (Circle) factory.createCircle(center, 21, Color.ORANGE);
        Circle circle2 = (Circle) factory.createCircle(center, 21, Color.ORANGE);
        assertEquals(circle1, circle2);
    }

    @Test
    public void circleShouldBeCloneable() {
        Circle circle1 = (Circle) factory.createCircle(center, 21, Color.ORANGE);
        Circle circle2 = circle1.Clone();
        circle1.setColor(Color.BLUE);
        assertNotEquals(circle1, circle2);
    }

    @Test
    public void circleShouldBeSerializable() {
        Circle circle1 = (Circle) factory.createCircle(center, 21, Color.ORANGE);
        String filename = "circle.test";
        Serializer.serialize(filename, circle1);
        try {
            Circle circle2 = (Circle) Serializer.deserialize(filename);
            assertEquals(circle1, circle2);
        } catch (SerializeException e) {
            e.printStackTrace();
        }
    }
}
