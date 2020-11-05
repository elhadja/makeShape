package models.grahics;

import models.shape.Point;

public abstract class UIComponentAbstractFactory {
    public static UIComponentAbstractFactory instance;

    public UIComponent createComponent(ComponentEnum type, Point topLeft, double width, double height) {
        switch (type) {
            case MENU:
                return new UIMenu(topLeft, width, height);
            case TOOLBAR:
                return new UIToolBar(topLeft, width, height);
            case WHITEBOARD:
                return new UIWhiteBoard(topLeft, width, height);
            default:
                throw new IllegalArgumentException("JFX_UIComponentFactory.createComponent: unknown type component");
        }
    }
    public abstract UIComponentAbsImpl createImplementer();
    public static UIComponentAbstractFactory getInstance() {
        if (instance == null)
            throw new UnsupportedOperationException("UIComponentAbsFactory should be initialized");
        return instance;
    }
}
