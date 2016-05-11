package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class CenterUsingBind extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Text text = new Text("JavaFx Go");
        text.setTextOrigin(VPos.TOP);
        text.setFont(Font.font(null, FontWeight.BOLD,18));
        Scene scene = new Scene(new Group(text),400,100);
        text.layoutXProperty().bind(scene.widthProperty().subtract(text.prefWidth(-1)).divide(2));
        text.layoutYProperty().bind(scene.heightProperty().subtract(text.prefHeight(-1)).divide(2));
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
