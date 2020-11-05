package models.grahics;

import models.shape.Point;

public class UIMenu extends UIComponentAbstract {
    private final ImageWrapper saveAs;
    private final ImageWrapper load;
    private final ImageWrapper undo;
    private final ImageWrapper redo;

    public UIMenu(Point location, double width, double height) {
        super(location, width, height);
        double ordinate = location.getY() + 10;
        final double imageWidth = 30;
        final double imageHeight = 30;
        ImageWrapperAbsFactory factory = ImageWrapperAbsFactory.getInstance();

        saveAs = factory.createImage("save.png",
                new Point(location.getX() + 40, ordinate),
                imageWidth,
                imageHeight);

        load = factory.createImage("load.png",
                new Point(saveAs.getLocation().getX() + 40, ordinate),
                imageWidth,
                imageHeight);

        undo = factory.createImage("undo.png",
                new Point(load.getLocation().getX() + 40, ordinate),
                imageWidth,
                imageHeight);

        redo = factory.createImage("redo.png",
                new Point(undo.getLocation().getX() + 40, ordinate),
                imageWidth,
                imageHeight);
    }

    @Override
    public void draw() {
        super.draw();
        saveAs.draw();
        load.draw();
        undo.draw();
        redo.draw();
    }

    @Override
    public void onClick(Point point) {
        if (saveAs.isAt(point)) {
            Editor.getInstance().saveDocument();
        } else if (load.isAt(point)) {
            Editor.getInstance().loadDocument();
        } else if (undo.isAt(point)) {
            Editor.getInstance().undo();
        } else if (redo.isAt(point)) {
            Editor.getInstance().redo();
        }
    }

    @Override
    public Object Clone() {
        throw new UnsupportedOperationException();
    }
}
