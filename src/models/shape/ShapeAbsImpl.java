package models.shape;

public abstract class ShapeAbsImpl {
    /**
     * frame the shape if it is selected or remove the frame if it is not selected
     * @param topLeftPoint the top left point of the shape
     * @param width the shape's width from the topLeft
     * @param height the shape's height from the topLeft
     * @param isSelected indicate if the shape is selected
     */
    public abstract void surroundShape(Point topLeftPoint,
                                       double width,
                                       double height,
                                       boolean isSelected);

    public abstract void drawRectangle(Point topLeft,
                                       double width,
                                       double height,
                                       String Color);

    public abstract void drawRoundRect(Point topLeft,
                                       double width,
                                       double height,
                                       double roundW,
                                       double roundH,
                                       String Color);

    public abstract void drawCircle(Point topLeft, double radius, String color);

    public abstract void drawPolygon(double[] xPoints,
                                     double[] yPoints,
                                     int numberSides,
                                     String color);
}
