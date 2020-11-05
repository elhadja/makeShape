package test.unitTests.shape;

import models.shape.*;
import Utils.Serializer;
import exceptions.SerializeException;
import models.shape.JfxShapeFactory;
import models.shape.ShapeAbstractFactory;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class RoundedRectangleTest {
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
        double height = 10;
        double roundW = 7;
        double roundH = 7;
        RoundRect rectangle1 = (RoundRect) factory.createRoundRect(mainPoint,width, height, roundW, roundH, Color.ORANGE) ;
        rectangle1.rotate(0);
        assertEquals(width, rectangle1.getHeight(), 0.00001);
    }

    @Test
    public void rectangleShouldBeTranslatable() {
        RoundRect rectangle1 = (RoundRect) factory.createRoundRect(mainPoint,21, 10, 7, 3, Color.ORANGE);
        rectangle1.translate(1 ,0);
        Point point = new Point (8, 7);
        assertEquals(point, rectangle1.getTopLeft());
    }

    @Test
    public void rectangleShouldBeIsAt() {
        RoundRect rectangle = (RoundRect) factory.createRoundRect(mainPoint,21, 10, 7, 3, Color.ORANGE);
        Point point = new Point(0,0);
        assertFalse(rectangle.isAt(point));
    }

    @Test
    public void rectangleShouldBeEqual() {
        RoundRect rectangle1 = (RoundRect) factory.createRoundRect(mainPoint,21, 10, 7, 3, Color.ORANGE);
        RoundRect rectangle2 = (RoundRect) factory.createRoundRect(mainPoint,21, 10, 7, 3, Color.ORANGE);
        assertEquals(rectangle1, rectangle2);
    }

    @Test
    public void rectangleShouldBeCloneable() {
        RoundRect rectangle1 = (RoundRect) factory.createRoundRect(mainPoint,21, 10, 7, 3, Color.ORANGE);
        Rectangle rectangle2 = rectangle1.Clone();
        rectangle1.setColor(Color.BLUE);
        assertNotEquals(rectangle1, rectangle2);
    }

    @Test
    public void rectangleShouldBeSerializable() {
        RoundRect rectangle1 = (RoundRect) factory.createRoundRect(mainPoint,21, 10, 7, 3, Color.ORANGE);
        String filename = "roundedRectangle.test";
        Serializer.serialize(filename, rectangle1);
        try {
            Rectangle rectangle2 = (Rectangle) Serializer.deserialize(filename);
            assertEquals(rectangle1, rectangle2);
        }catch (SerializeException e) {
            System.out.println("Deserialization failed ...");
        }
    }
}
