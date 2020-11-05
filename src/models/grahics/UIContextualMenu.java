package models.grahics;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import models.shape.Point;

public class UIContextualMenu {
    private final ContextMenu contextMenu;

    public UIContextualMenu() {
        contextMenu = new ContextMenu();
        MenuItem groupItem = new MenuItem("group Shapes");
        MenuItem ungroupItem = new MenuItem("Ungroup Shapes");
        MenuItem editItem = new MenuItem("edit");
        MenuItem rotateItem = new MenuItem("rotate");

        groupItem.setOnAction(actionEvent -> {
            System.out.println("group shapes");
            Editor.getInstance().groupShapes();
        });

        ungroupItem.setOnAction(actionEvent -> {
            System.out.println("ungroup shapes");
            Editor.getInstance().ungroupShapes();
        });

        editItem.setOnAction(actionEvent -> {
            System.out.println("Edit properties");
            Editor.getInstance().showPropertiesWindows();
        });

        rotateItem.setOnAction(actionEvent -> {
            System.out.println("Edit properties");
            Editor.getInstance().rotateShapes();
        });
        contextMenu.getItems().addAll(groupItem, ungroupItem, editItem, rotateItem);
    }
    public void show(Node scene, Point location) {
        contextMenu.show(scene, location.getX(), location.getY());
    }
}
