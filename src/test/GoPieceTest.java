package test;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.SceneBuilder;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPaneBuilder;
import javafx.stage.Stage;
import main.GoPiece;
import main.GoSquare;
import main.Owner;

/**
 * Created by Konrad on 01.05.2016.
 */
public class GoPieceTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Node white, black;
        primaryStage.setScene(SceneBuilder.create()
                .root(HBoxBuilder.create()
                        .snapToPixel(false)
                        .children(
                                white = StackPaneBuilder.create()
                                        .children(
                                                new GoSquare(),
                                                new GoPiece(Owner.WHITE)
                                        )
                                        .build(),
                                black = StackPaneBuilder.create()
                                        .children(
                                                new GoSquare(),
                                                new GoPiece(Owner.BLACK)
                                        )
                                        .build()
                        )
                        .build())
                .build());
        HBox.setHgrow(white, Priority.ALWAYS);
        HBox.setHgrow(black, Priority.ALWAYS);
        primaryStage.show();
    }
}
