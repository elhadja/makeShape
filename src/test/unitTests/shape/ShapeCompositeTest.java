package test.unitTests.shape;

import models.shape.*;
import models.shape.JfxShapeFactory;
import models.shape.ShapeAbstractFactory;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ShapeCompositeTest {
    private Shape shapeComposite;
    private List<Shape> shapeList;
    private Circle circle;
    private Rectangle rectangle;
    private Polygon polygon;
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

    public void initializeData() {
        shapeList = new ArrayList<>();

        rectangle = (Rectangle) factory.createRectangle(new Point(50,50), 60, 60, Color.WHITE);
        circle = (Circle) factory.createCircle(new Point(20, 20), 5, Color.WHITE);
        polygon = (Polygon) factory.createPolygon(new Point(10, 10), 5, 5, Color.WHITE);

        shapeComposite =  factory.createShapeGroup();
        shapeComposite.addShape(rectangle);
        shapeComposite.addShape(circle);
        shapeComposite.addShape(polygon);
    }

    @Test
    public void selectIfContains() {
        initializeData();
        shapeComposite.selectIfContains(rectangle.getTopLeft());
        shapeComposite.selectIfContains(circle.getTopLeft());
        shapeComposite.selectIfContains(polygon.getTopLeft());
        assertTrue(rectangle.isSelected());
        assertTrue(circle.isSelected());
    }

    /**
     * if all shapeComposite's children are not selected, then
     * just its selectedChildren shoulb be added to the list
     */
    @Test
    public void addSelectedShapesTo() {
        initializeData();
        rectangle.setSelected(true);
        circle.setSelected(true);
        polygon.setSelected(false);
        shapeComposite.addSelectedShapesTo(shapeList);
        assertEquals(2, shapeList.size());
    }

    /**
     * if all shapeComposite's children are selected, the shape composite
     * must be added in the list rather than its children
     */
    @Test
    public void addSelectedShapesTo2() {
        initializeData();
        Shape composite = new ShapeComposite();
        Shape rectangle1 = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
        Shape rectangle2 = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);

        composite.addShape(rectangle1);
        composite.addShape(rectangle2);
        composite.setSelected(true);
        shapeComposite.addShape(composite);
        composite.addSelectedShapesTo(shapeList);

        assertEquals(1, shapeList.size());
        assertSame(composite, shapeList.get(0));
    }

    @Test
    public void selectGroupOf() {
        initializeData();
        Shape newChild = factory.createCircle(new Point(5, 5), 5, Color.WHITE);
        shapeComposite.selectGroupOf(newChild);
        shapeComposite.addSelectedShapesTo(shapeList);
        assertEquals(0, shapeList.size());

        shapeComposite.addShape(newChild);
        shapeComposite.selectGroupOf(newChild);
        shapeComposite.addSelectedShapesTo(shapeList);
        assertEquals(4, shapeList.size(), 1e-15);
    }

    @Test
    public void select() {
        initializeData();
        shapeComposite.addSelectedShapesTo(shapeList);
        assertEquals(0, shapeList.size());

        shapeComposite.select();
        shapeComposite.addSelectedShapesTo(shapeList);
        assertEquals(3, shapeList.size());

        shapeList.clear();
        rectangle.setSelected(false);
        shapeComposite.select();
        shapeComposite.addSelectedShapesTo(shapeList);
        assertEquals(shapeComposite.getSize() - 2, shapeList.size());
    }

    @Test
    public void getSize() {
        initializeData();
        assertEquals(3, shapeComposite.getSize());

        Shape newShapeGroup = factory.createShapeGroup();
        assertEquals(0, newShapeGroup.getSize());
    }

    @Test
    public void getTopLeftPoint() {
        initializeData();
        assertEquals(shapeComposite.getTopLeft(), new Point(10, 10));

        Shape rectangle = new Rectangle(new Point(2, 2), 5, 5, Color.WHITE);
        shapeComposite.addShape(rectangle);
        assertEquals(shapeComposite.getTopLeft(), new Point(2, 2));

        ShapeComposite newShapeComposite = new ShapeComposite();
        Shape rectangle2 = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
        newShapeComposite.addShape(rectangle2);
        shapeComposite.addShape(newShapeComposite);

        assertEquals(newShapeComposite.getTopLeft(), new Point(0, 0));
        assertEquals(shapeComposite.getTopLeft(), new Point(0, 0));
    }

    @Test
    public void setSelected() {
        initializeData();
        shapeComposite.setSelected(true);
        shapeComposite.addSelectedShapesTo(shapeList);
        assertEquals(shapeComposite.getSize(), shapeList.size());
    }

    @Test
    public void isSelected() {
        initializeData();
        assertFalse(shapeComposite.isSelected());
        rectangle.setSelected(true);
        assertFalse(shapeComposite.isSelected());
        rectangle.setSelected(false);
        shapeComposite.select();
        assertTrue(shapeComposite.isSelected());
    }

    @Test
    public void getMainPoint() {
        initializeData();
        assertEquals(shapeComposite.getTopLeft(), new Point(10, 10));

        Shape rectangle = new Rectangle(new Point(2, 2), 5, 5, Color.WHITE);
        shapeComposite.addShape(rectangle);
        assertEquals(shapeComposite.getTopLeft(), new Point(2, 2));

        ShapeComposite newShapeComposite = new ShapeComposite();
        Shape rectangle2 = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
        newShapeComposite.addShape(rectangle2);
        shapeComposite.addShape(newShapeComposite);

        assertEquals(newShapeComposite.getTopLeft(), new Point(0, 0));
        assertEquals(shapeComposite.getTopLeft(), new Point(0, 0));
    }

    @Test
    public void setMainPoint() {
        initializeData();
        Rectangle rectangleClone = rectangle.Clone();
        Circle circleClone = circle.Clone();
        Polygon polygoneClone = polygon.Clone();
        Point lastMainPoint = shapeComposite.getTopLeft();
        shapeComposite.setTopLeft(new Point(0, 0));

        assertEquals(shapeComposite.getTopLeft(), new Point(0, 0));

        Point point = new Point(0 + Math.abs(lastMainPoint.getX() - rectangleClone.getTopLeft().getX()),
                                   0 + Math.abs(lastMainPoint.getY() - rectangleClone.getTopLeft().getY()));
        assertEquals(rectangle.getTopLeft(),point);

        point = new Point(0 + Math.abs(lastMainPoint.getX() - circleClone.getTopLeft().getX()),
                0 + Math.abs(lastMainPoint.getY() - circleClone.getTopLeft().getY()));
        assertEquals(circle.getTopLeft(),point);

        point = new Point(0 + Math.abs(lastMainPoint.getX() - polygoneClone.getTopLeft().getX()),
                0 + Math.abs(lastMainPoint.getY() - polygoneClone.getTopLeft().getY()));
        assertEquals(polygon.getTopLeft(),point);
    }

    @Test
    public void getWidthFromTopLeft() {
        initializeData();
        Point topLeft = shapeComposite.getTopLeft();
        assertEquals(110 - topLeft.getX() ,shapeComposite.getWidthFromTopLeft(), 1e-15);
    }

    @Test
    public void getHeightFromTopLeft() {
        initializeData();
        Point topLeft = shapeComposite.getTopLeft();
        assertEquals(110 - topLeft.getY() ,shapeComposite.getHeightFromTopLeft(), 1e-15);
    }

    @Test
    public void getColor() {
        initializeData();
        try {
            shapeComposite.getColor();
            fail();
        }catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void setColor() {
        initializeData();
        shapeComposite.setColor(Color.WHITE);
        assertEquals(rectangle.getColor(), Color.WHITE);
        assertEquals(polygon.getColor(), Color.WHITE);
        assertEquals(circle.getColor(), Color.WHITE);
    }

    @Test
    public void getParent() {
        initializeData();
        if (shapeComposite != rectangle.getParent())
            fail();

        shapeComposite.removeShape(rectangle);
        if (shapeComposite == rectangle.getParent())
            fail();
    }


    @Test
    public void translate() {
        initializeData();
        final double DELTA = 1e-15;
        Point rectangleLastMainPoint = rectangle.getTopLeft();
        Point circleLastMainPoint = circle.getTopLeft();
        Point polygonLastMainPoint = polygon.getTopLeft();

        shapeComposite.translate(5, 5);

        assertEquals(rectangle.getTopLeft().getX(), rectangleLastMainPoint.getX() + 5, DELTA);
        assertEquals(rectangle.getTopLeft().getY(), rectangleLastMainPoint.getY() + 5, DELTA);

        assertEquals(circle.getTopLeft().getX(), circleLastMainPoint.getX()+5, DELTA);
        assertEquals(circle.getTopLeft().getY(), circleLastMainPoint.getY()+5, DELTA);

        assertEquals(polygon.getTopLeft().getX(), polygonLastMainPoint.getX()+5, DELTA);
        assertEquals(polygon.getTopLeft().getY(), polygonLastMainPoint.getY()+5, DELTA);
    }

    @Test
    public void isAt() {
        initializeData();
        assertTrue(shapeComposite.isAt(rectangle.getTopLeft()));
        assertTrue(shapeComposite.isAt(polygon.getTopLeft()));
        assertTrue(shapeComposite.isAt(circle.getTopLeft()));

        assertFalse(shapeComposite.isAt(new Point(1000, 1000)));
    }

    @Test
    public void isIn() {
        Shape rectangle = new Rectangle(new Point(0, 0), 5, 5, Color.WHITE);
        Shape rectangle2 = new Rectangle(new Point(5, 5), 5, 5, Color.WHITE);
        Shape rectangle3 = new Rectangle(new Point(20, 20), 5, 5, Color.WHITE);
        Shape shapeComposite = new ShapeComposite();
        shapeComposite.addShape(rectangle);
        shapeComposite.addShape(rectangle2);
        shapeComposite.addShape(rectangle3);

        assertTrue(shapeComposite.isIn(new Point(0, 0), 25, 25));
        assertFalse(shapeComposite.isIn(new Point(0, 0), 10, 10));
        assertFalse(shapeComposite.isIn(new Point(0, 0), 9, 10));
        assertFalse(shapeComposite.isIn(new Point(0, 0), 10, 9));
    }

    @Test
    public void removeShape() {
        initializeData();
        int initialSize = shapeComposite.getSize();
        shapeComposite.removeShape(rectangle);
        assertEquals(shapeComposite.getSize(), initialSize-1);
        shapeComposite.removeShape(circle);
        assertEquals(shapeComposite.getSize(), initialSize-2);
        shapeComposite.removeShape(polygon);
        assertEquals(shapeComposite.getSize(), initialSize-3);

        if (rectangle.getParent() != null || circle.getParent()!=null || polygon.getParent()!=null)
            fail();
    }

    @Test
    public void addShape() {
        initializeData();
        int initialSize = shapeComposite.getSize();
        Shape newChild = factory.createCircle(new Point(0, 0), 5, Color.WHITE);
        shapeComposite.addShape(newChild);

        assertEquals(initialSize + 1, shapeComposite.getSize());
        if (newChild.getParent() != shapeComposite)
            fail();
    }

    @Test
    public void Clone() {
        initializeData();
        Shape theClone = shapeComposite.Clone();
        for (int i = 0; i< shapeComposite.getSize(); i++) {
            if (!shapeComposite.getChildren(i).equals(theClone.getChildren(i)))
                fail();
        }
    }
}