package test.unitTests.shape;
import models.shape.Point;
import Utils.Serializer;
import exceptions.SerializeException;
import org.junit.Test;

import static org.junit.Assert.*;

public class PointTest {

    @Test
    public void pointShouldBeRotatable() {
        Point point1 = new Point(7,7);
        Point center = new Point(3,3);
        point1.rotate(center, 180);
        point1.rotate(center, -180);
        Point point2 = new Point(6.999999999999999,6.999999999999999);
        assertEquals(point1, point2);
    }

    @Test
    public void pointShouldBeTranslatable() {
        Point point1 = new Point(7,7);
        point1.translate(1 ,0);
        Point point2 = new Point(8,7);
        assertEquals(point1, point2);
    }

    @Test
    public void pointShouldBeIn() {
        Point point = new Point(7,7);
        Point topLeft = new Point(0,0);
        assertFalse(point.isIn(topLeft, 2, 2));
    }

    @Test
    public void pointShouldBeEqual() {
        Point point1 = new Point(7,7);
        Point point2 = new Point(7,7);
        assertEquals(point1, point2);
    }

    @Test
    public void pointShouldBeCloneable() {
        Point point1 = new Point(7,7);
        Point point2 = point1.clone();
        point2.translate(1, 1);
        assertNotEquals(point1, point2);
    }

    @Test
    public void pointShouldBeSerializable() {
        Point point1 = new Point(7,7);
        String filename = "point.test";
        Serializer.serialize(filename, point1);
        try {
            Point point2 = (Point) Serializer.deserialize(filename);
            assertEquals(point1, point2);
        } catch (SerializeException e) {
            e.printStackTrace();
        }
    }
}
