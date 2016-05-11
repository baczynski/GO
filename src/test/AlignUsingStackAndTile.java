package test;

import javafx.application.Application;
import javafx.beans.binding.NumberExpression;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javafx.scene.text.Text;
/**
 * Created by Konrad on 29.04.2016.
 */
public class AlignUsingStackAndTile extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane left = new StackPane();
        left.setStyle(("-fx-background-color: black"));
        Text text = new Text("JavaFX");
        text.setFont(Font.font(null, FontWeight.BOLD,18));
        text.setFill(Color.WHITE);
        StackPane.setAlignment(text, Pos.BASELINE_RIGHT);
        left.getChildren().add(text);

        Text right = new Text("Go");
        right.setFont(Font.font(null,FontWeight.BLACK.BOLD,18));
        right.setFill(Color.BLACK);
        TilePane tiles = new TilePane();
        tiles.setSnapToPixel(false);
        TilePane.setAlignment(right,Pos.BASELINE_LEFT);
        tiles.getChildren().addAll(left,right);

        Scene scene = new Scene(tiles,400,100);
        left.prefWidthProperty().bind(scene.widthProperty().divide(2));
        left.prefHeightProperty().bind(scene.heightProperty());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
