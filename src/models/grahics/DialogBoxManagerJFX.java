package models.grahics;

import javafx.scene.control.Alert;

public class DialogBoxManagerJFX {
    private static Alert dialog;

    private static void showDialog(String title, String headerText, String contentText) {
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);
        dialog.setContentText(contentText);
        dialog.showAndWait();
    }
    public static void showErrorAlert(String headerMessage, String errorMessage) {
        dialog = new Alert(Alert.AlertType.ERROR);
        showDialog("Error", headerMessage, errorMessage);
    }

    public static void showWarningAlert(String headerMessage, String warningMessage) {
        dialog = new Alert(Alert.AlertType.WARNING);
        showDialog("Warning", headerMessage, warningMessage);
    }
}
