package test;

import javafx.application.Application;
import javafx.scene.SceneBuilder;
import javafx.scene.layout.StackPaneBuilder;
import javafx.stage.Stage;
import main.GoSquare;

/**
 * Created by Konrad on 30.04.2016.
 */
public class GoSquareTest extends Application {

    public static void main(String [] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        primaryStage.setScene(SceneBuilder.create()
        .root(StackPaneBuilder.create()
        .children(new GoSquare()).build())
        .build());
        primaryStage.show();
    }
}
