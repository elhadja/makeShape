package models.grahics;

import models.shape.Point;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.FileChooser;

import java.io.File;

public class JfxEditorImpl extends EditorImpl {
    private final Node canvas;
    private final UIContextualMenu contextualMenu;
    private final PropertiesWindow propertiesWindow;
    private final GraphicsContext graphicsContext;

    public JfxEditorImpl(Node canvas, GraphicsContext graphicsContext) {
        this.canvas = canvas;
        this.graphicsContext = graphicsContext;
        contextualMenu = new UIContextualMenu();
        propertiesWindow = new PropertiesWindow(new Point(400, 200));
    }

    @Override
    public void saveDocument(UIComponentShapeManager component) {
        FileChooser dialog = new FileChooser();
        File file = dialog.showSaveDialog(null);
        if (file != null) {
            component.save(file.toString());
        }
    }

    @Override
    public void loadDocument(UIComponentShapeManager component) {
        FileChooser dialog = new FileChooser();
        File file = dialog.showOpenDialog(null);
        if (file != null) {
            component.load(file.toString());
        }
    }

    @Override
    public void showContextualMenu(Point location) {
        contextualMenu.show(canvas, location);
    }

    @Override
    public void showPropertiesWindow() {
        propertiesWindow.show();
    }

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }
}
