package models.grahics;

import models.shape.Point;

public interface UIComponent {
     void draw();
     double getWidth();
     double getHeight();
     Point getLocation();
     Object Clone();

     /**
      * check if the @param point is in the area of the component
      * @param point the point to check
      * @return true if the @param point is in the area of the component or false otherwise
      */
     boolean isAt(Point point);

     /**
      * report to the component that a click happened
      * @param point the location where the click happened
      */
     void onClick(Point point);
}