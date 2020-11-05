package test.unitTests.shape;

import models.shape.JfxShapeFactory;
import models.shape.Polygon;
import models.shape.Point;
import models.shape.ShapeAbstractFactory;
import Utils.Serializer;
import exceptions.SerializeException;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class PolygonTest {

    private ShapeAbstractFactory factory;
    private final Point mainPoint = new Point(7,7);

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
        Polygon polygon1 = (Polygon) factory.createPolygon(mainPoint,21, 10, Color.ORANGE);
        Polygon polygon2 = (Polygon) factory.createPolygon(mainPoint,21, 10, Color.ORANGE);
        polygon1.rotate(15);
        assertNotSame(polygon2, polygon1);
    }

    @Test
    public void polygonShouldBeTranslatable() {
        Polygon polygon1 = (Polygon) factory.createPolygon(mainPoint,21, 10, Color.ORANGE);
        polygon1.translate(1 ,0);
        Point point = new Point (8, 7);
        assertEquals(point, polygon1.getTopLeft());
    }

    @Test
    public void polygonShouldBeIsAt() {
        Polygon polygon = (Polygon) factory.createPolygon(mainPoint,21, 10, Color.ORANGE);
        Point point = new Point(9,9);
        assertTrue(polygon.isAt(point));
    }

    @Test
    public void polygonShouldBeEqual() {
        Polygon polygon1 = (Polygon) factory.createPolygon(mainPoint,21, 10, Color.ORANGE);
        Polygon polygon2 = (Polygon) factory.createPolygon(mainPoint,21, 10, Color.ORANGE);
        assertEquals(polygon1, polygon2);
    }

    @Test
    public void polygonShouldBeCloneable() {
        Polygon polygon1 = (Polygon) factory.createPolygon(mainPoint,21, 10, Color.ORANGE);
        Polygon polygon2 = polygon1.Clone();
        assertNotSame(polygon1, polygon2);
        assertEquals(polygon1, polygon2);
    }

    @Test
    public void polygonShouldBeSerializable() {
        Polygon polygon1 = (Polygon) factory.createPolygon(mainPoint,21, 10, Color.ORANGE);
        String filename = "polygon.test";
        Serializer.serialize(filename, polygon1);
        try {
            Polygon polygon2 = (Polygon) Serializer.deserialize(filename);
            assertEquals(polygon1, polygon2);
        }catch (SerializeException e) {
            e.printStackTrace();
        }
    }
}
