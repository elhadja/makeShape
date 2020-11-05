package models.grahics;

import models.shape.Point;

public abstract class UIComponentAbsImpl {
    /**
     * draw a rectangular frame
     * @param topLeft the top left of the frame
     * @param width the width of the frame
     * @param height the height of the frame
     */
    public abstract void drawFrame(Point topLeft, double width, double height, boolean clearRect);

    /**
     * shows an dialog box
     * @param headerMessageError the title of the message
     * @param errorMessage the content of the error message
     */
    public abstract void showDialogError(String headerMessageError, String errorMessage);
}
