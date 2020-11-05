package test;
import models.grahics.*;
import models.shape.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
    private boolean dragAndDrop = false;
    private static boolean DEBUG = false;

    @Override
    public void start(Stage primaryStage) {
        double width = 800;
        double height = 650;
        primaryStage.setTitle("AL Project");
        primaryStage.setResizable(false);
        Group root = new Group();
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Editor.initialize(width, height, new JfxEditorImpl(canvas, gc));
        Editor.getInstance().draw();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(onKeyPressedHandler);
        canvas.setOnMousePressed(onMousePressedHandler);
        canvas.setOnMouseReleased(onMouseReleasedHandler);
        canvas.setOnMouseDragged(onMouseDraggedHandler);
        canvas.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent contextMenuEvent) {
                Editor.getInstance().showContextualMenuAt(new Point(contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY()),
                        new Point(contextMenuEvent.getX(), contextMenuEvent.getY()));
            }
        });
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Editor.getInstance().saveToolBar();
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private final EventHandler<KeyEvent> onKeyPressedHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            }
            else if (keyEvent.getCode() == KeyCode.D) {
                DEBUG = !DEBUG;
                Editor.getInstance().draw();
            }
        }
    };

    private final EventHandler<MouseEvent> onMousePressedHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                mouseEvent.setDragDetect(true);

                if (mouseEvent.isControlDown()) {
                    Editor.getInstance().addShapeToSelectedShapes(new Point(mouseEvent.getX(), mouseEvent.getY()));
                } else {
                    Editor.getInstance().onClick(new Point(mouseEvent.getX(), mouseEvent.getY()));
                }
            }
        }
    };

    private final EventHandler<MouseEvent> onMouseReleasedHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (dragAndDrop) {
                dragAndDrop = false;
                Editor.getInstance().endDragAndDropAt(new Point(mouseEvent.getX(), mouseEvent.getY()));
            }
        }
    };

    private final EventHandler<MouseEvent> onMouseDraggedHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (!dragAndDrop) {
                dragAndDrop = true;
                Editor.getInstance().startDragAndDropAt(new Point(mouseEvent.getX(), mouseEvent.getY()));
            }
            Editor.getInstance().actualizeScreen(new Point(mouseEvent.getX(), mouseEvent.getY()));
            mouseEvent.setDragDetect(false);
        }
    };

    public static boolean isDebug() {
       return DEBUG;
    }

    public static void main(String[] args) {
        launch(args);
    }
}