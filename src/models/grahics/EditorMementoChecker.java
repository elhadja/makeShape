package models.grahics;

import java.util.ArrayList;
import java.util.List;

public class EditorMementoChecker {
    private final List<EditorMemento> mementos = new ArrayList<>();
    private final List<EditorMemento> undoMementos = new ArrayList<>();

    public void add(EditorMemento memento) {
        mementos.add(memento);
        if (undoMementos.size() > 0)
            undoMementos.clear();
    }

    public EditorMemento getPrevious() {
        if (mementos.size() > 1) {
            undoMementos.add(mementos.get(mementos.size() - 1));
            mementos.remove(mementos.size() - 1);
            return mementos.get(mementos.size() - 1);
        }
        return null;
    }

    public EditorMemento getNext() {
        if (undoMementos.size() > 0) {
            mementos.add(undoMementos.get(undoMementos.size() - 1));
            undoMementos.remove(undoMementos.size() - 1);
            return mementos.get(mementos.size() - 1);
        }
        return null;
    }

    public int getSize() {
        return mementos.size();
    }
}
