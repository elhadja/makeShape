package test.unitTests.graphics;

import Utils.JavaFxJUnit4ClassRunner;
import models.grahics.Editor;
import models.grahics.JfxEditorImpl;
import models.grahics.UIToolBar;
import models.grahics.UIWhiteBoard;
import models.shape.Point;
import models.grahics.EditorMemento;
import models.grahics.EditorMementoChecker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


@RunWith(JavaFxJUnit4ClassRunner.class)
public class EditorMementoCheckerTest {

    @Before
    public void initializeTests() {
        try {
            Editor.initialize(500, 500, new JfxEditorImpl(null, null));
        }catch (Exception e) {
            // the editor is already initialized by another test
        }
    }

    @Test
    public void EditorMementoChecker() {
        EditorMementoChecker mementoChecker = new EditorMementoChecker();
        assertEquals(0, mementoChecker.getSize());
    }

    @Test
    public void add() {
        EditorMementoChecker mementoChecker = new EditorMementoChecker();
        EditorMemento memento1 = new EditorMemento(new UIToolBar(new Point(5,5), 100, 100),
                new UIWhiteBoard(new Point(0, 0), 200, 200));
        EditorMemento memento2 = new EditorMemento(new UIToolBar(new Point(5,5), 100, 100),
                new UIWhiteBoard(new Point(0, 0), 200, 200));

        mementoChecker.add(memento1);
        mementoChecker.add(memento2);

        assertEquals(2, mementoChecker.getSize());
    }

    @Test
    public void getPrevious() {
        EditorMementoChecker mementoChecker = new EditorMementoChecker();
        EditorMemento memento1 = new EditorMemento(new UIToolBar(new Point(5,5), 100, 100),
                new UIWhiteBoard(new Point(0, 0), 200, 200));
        EditorMemento memento2 = new EditorMemento(new UIToolBar(new Point(5,5), 100, 100),
                new UIWhiteBoard(new Point(0, 0), 200, 200));

        mementoChecker.add(memento1);
        mementoChecker.add(memento2);

        assertSame(memento1, mementoChecker.getPrevious());
        assertNull(mementoChecker.getPrevious());
    }

    @Test
    public void getNext() {
        EditorMementoChecker mementoChecker = new EditorMementoChecker();
        EditorMemento memento1 = new EditorMemento(new UIToolBar(new Point(5,5), 100, 100),
                new UIWhiteBoard(new Point(0, 0), 200, 200));
        EditorMemento memento2 = new EditorMemento(new UIToolBar(new Point(5,5), 100, 100),
                new UIWhiteBoard(new Point(0, 0), 200, 200));

        mementoChecker.add(memento1);
        mementoChecker.add(memento2);
        mementoChecker.getPrevious();

        assertSame(memento2, mementoChecker.getNext());
        assertNull(mementoChecker.getNext());
    }
}