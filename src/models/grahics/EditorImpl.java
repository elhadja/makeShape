package models.grahics;

import models.shape.Point;

public abstract class EditorImpl {
    public abstract void saveDocument(UIComponentShapeManager component);
    public abstract void loadDocument(UIComponentShapeManager component);
    public abstract void showContextualMenu(Point location);
    public abstract void showPropertiesWindow();
}
