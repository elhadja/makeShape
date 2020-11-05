package models.grahics;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.shape.Point;

public class PropertiesWindow {

   private final Stage stage;

   private final TextField widthTxt;
   private final TextField heightTxt;
   private final TextField abscisseTxt;
   private final TextField ordonateTxt;
   private final TextField radiusTxt;
   private final TextField numberSidesTxt;
   private final ColorPicker colorPicker;
   private int count;

   private boolean applyProperties() {
      try {
         double newWidth = widthTxt.getText().equals("") ? 0 : Double.parseDouble(widthTxt.getText());
         double newHeight= heightTxt.getText().equals("") ? 0 : Double.parseDouble(heightTxt.getText());
         double newAbs = abscisseTxt.getText().equals("") ? 0 : Double.parseDouble(abscisseTxt.getText());
         double newOrd= ordonateTxt.getText().equals("") ? 0 :Double.parseDouble(ordonateTxt.getText());
         double newRadius =  radiusTxt.getText().equals("") ? 0 :Double.parseDouble(radiusTxt.getText());
         int newNumberSides = numberSidesTxt.getText().equals("") ? 0 :Integer.parseInt(numberSidesTxt.getText());
         Editor.getInstance().setSelectedShapesProperties(colorPicker.getValue(), newWidth, newHeight,
                 new Point(newAbs, newOrd), newRadius, newNumberSides);
         count += 1;
         return true;
      } catch (NumberFormatException e) {
         System.out.println("Vous devez rentrer uniquement des nombres");
      }
      return false;
   }

   public PropertiesWindow(Point point) {
      this.count = 0;
      Point location = point.clone();
      this.widthTxt = new TextField();
      this.heightTxt = new TextField();
      this.abscisseTxt = new TextField();
      this.ordonateTxt = new TextField();
      this.radiusTxt = new TextField();
      this.numberSidesTxt = new TextField();

      this.colorPicker = new ColorPicker();
      this.colorPicker.setValue(Color.WHITE);

      Button applyButton = new Button("appliquer");
      EventHandler<MouseEvent> applyButtonListener = mouseEvent -> {
         System.out.println("apply bouton");
         applyProperties();
         Editor.getInstance().draw();
      };
      applyButton.setOnMouseClicked(applyButtonListener);
      Button okButton = new Button("ok");
      EventHandler<MouseEvent> okButtonListener = new EventHandler<>() {
         @Override
         public void handle(MouseEvent mouseEvent) {
            System.out.println("ok bouton");
            if (applyProperties()) {
               count = 0;
               stage.close();
            }
         }
      };
      okButton.setOnMouseClicked(okButtonListener);
      Button cancelButton = new Button("annuler");
      EventHandler<MouseEvent> cancelButtonListener = new EventHandler<>() {
         @Override
         public void handle(MouseEvent mouseEvent) {
            System.out.println("cancel bouton");
            for (int i = 0; i < count; i++)
               Editor.getInstance().undo();
            count = 0;
            stage.close();
         }
      };
      cancelButton.setOnMouseClicked(cancelButtonListener);

      HBox buttonHorizontalBox = new HBox(15, okButton, applyButton, cancelButton);
      buttonHorizontalBox.setAlignment(Pos.BOTTOM_RIGHT);

      HBox hbox = new HBox(10, new Label("Largeur: "), this.widthTxt);
      hbox.setAlignment(Pos.CENTER);
      HBox hbox1 = new HBox(10, new Label("Hauteur: "), this.heightTxt);
      hbox1.setAlignment(Pos.CENTER);
      HBox hbox2 = new HBox(10, new Label("abscisse: "), this.abscisseTxt);
      hbox2.setAlignment(Pos.CENTER);
      HBox hbox3 = new HBox(10, new Label("ordonnée: "), this.ordonateTxt);
      hbox3.setAlignment(Pos.CENTER);
      HBox hbox4 = new HBox(10, new Label("Couleur: "), colorPicker);
      hbox4.setAlignment(Pos.CENTER);
      HBox radiusHBox = new HBox(10, new Label("rayon"), this.radiusTxt);
      radiusHBox.setAlignment(Pos.CENTER);
      HBox numberSidesHBox = new HBox(10, new Label("Number de côtés"), this.numberSidesTxt);
      numberSidesHBox.setAlignment(Pos.CENTER);

      VBox mainLayout = new VBox(20, hbox, hbox1, radiusHBox, numberSidesHBox, hbox2, hbox3, hbox4, buttonHorizontalBox);
      mainLayout.setAlignment(Pos.CENTER);

      Scene scene = new Scene(mainLayout, 400, 400);
      stage = new Stage();
      stage.setTitle("modification des propriétés");
      stage.setScene(scene);
      stage.setX(location.getX());
      stage.setY(location.getY());
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setOnCloseRequest(windowEvent -> {
         System.out.println("closing properties windows");
         count = 0;
      });
   }

   public void show() {
      stage.show();
   }
}
