package models.shape;

import javafx.scene.paint.Color;

import java.io.Serializable;

public class RoundRect extends Rectangle implements Cloneable, Serializable {
    private double roundWidth;
    private double roundHeight;

    public RoundRect(Point mainPoint, double width, double height,
                     double roundW, double roundH, Color color) {
        super(mainPoint, width, height, color);
        this.roundWidth = roundW;
        this.roundHeight = roundH;
    }

    @Override
    public RoundRect Clone() {
        RoundRect tmp = null;
        try {
            tmp = (RoundRect) super.clone();
        }
        catch(CloneNotSupportedException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return tmp;
    }

    @Override
    public void rotate(double angle) {
        super.rotate(angle);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RoundRect roundRect = (RoundRect) o;
        return Double.compare(roundRect.roundWidth, roundWidth) == 0 &&
                Double.compare(roundRect.roundHeight, roundHeight) == 0;
    }

    @Override
    public void draw() {
        getImplementer().surroundShape(this.getTopLeft(), getWidth(), getHeight(), isSelected());
        getImplementer().drawRoundRect(getTopLeft(), getWidth(), getHeight(), roundWidth, roundHeight, getColor().toString());
    }

    @Override
    public String toString() {
        return "RoundRect{" +
                "width=" + getWidth() +
                ", height=" + getHeight() +
                "roundW=" + roundWidth +
                ", roundH=" + roundHeight +
                ", selected=" + isSelected() +
                ", mainPoint=" + getTopLeft() +
                ", color='" + getColor() + '\'' +
                ", parent=" + getParent() +
                '}';
    }
}
