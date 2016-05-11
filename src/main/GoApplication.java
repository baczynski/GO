package main;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SceneBuilder;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.DropShadowBuilder;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.InnerShadowBuilder;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.EllipseBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.stage.Stage;

import java.util.List;

/**
 * Created by Konrad on 01.05.2016.
 */
public class GoApplication extends Application {

    GoModel model= GoModel.getInstance();


    private StackPane createScore(Owner owner) {
        Region background;
        Ellipse piece;
        Text score;
        Text remaining;
        GoModel model = GoModel.getInstance();
        StackPane stack =
                StackPaneBuilder.create()
                        .prefHeight(100)
                        .children(
                                background = RegionBuilder.create()
                                        .style("-fx-background-color: " + owner.opposite().getColorStyle())
                                        .build(),
                                FlowPaneBuilder.create()
                                        .hgap(20)
                                        .vgap(10)
                                        .alignment(Pos.CENTER)
                                        .children(score = TextBuilder.create()
                                                        .font(Font.font(null, FontWeight.BOLD, 100))
                                                        .fill(owner.getColor())
                                                        .build(),
                                                VBoxBuilder.create()
                                                        .alignment(Pos.CENTER)
                                                        .spacing(10)
                                                        .children(
                                                                piece = EllipseBuilder.create()
                                                                        .effect((DropShadowBuilder.create()
                                                                                .color(Color.DODGERBLUE).spread(0.2).build()))
                                                                        .radiusX(32).radiusY(20).fill(owner.getColor()).build(),
                                                                remaining = TextBuilder.create()
                                                                        .font(Font.font(null, FontWeight.BOLD, 12))
                                                                        .fill(owner.getColor())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build();

        InnerShadow innerShadow = InnerShadowBuilder.create().color(Color.DODGERBLUE).choke(0.5).build();
        background.effectProperty().bind(Bindings.when(model.turn.isEqualTo(owner)).then(innerShadow).otherwise((InnerShadow) null));

        DropShadow dropShadow = DropShadowBuilder.create()
                .color(Color.DODGERBLUE)
                .spread(0.2)
                .build();
        piece.effectProperty().bind(Bindings.when(model.turn.isEqualTo(owner)).then(dropShadow).otherwise((DropShadow) null));
        return stack;

    }

    private Node createScoreBoxes(){
        TilePane tiles =  TilePaneBuilder.create()
                .snapToPixel(false)
                .prefColumns(2)
                .children(
                        createScore(Owner.BLACK),
                        createScore(Owner.WHITE))
                .build();
        tiles.prefTileWidthProperty().bind(Bindings.selectDouble(tiles.parentProperty(),"width").divide(2));
        return tiles;
    }

    private Node createTitle(){
        StackPane left = new StackPane();
        left.setStyle("-fx-background-color: black");
        Text text = new Text("JavaFX");
        text.setFont(Font.font(null,FontWeight.BOLD,18));
        text.setFill(Color.WHITE);
        StackPane.setAlignment(text,Pos.CENTER_RIGHT);
        left.getChildren().add(text);
        Text right = new Text("GO");
        right.setFont(Font.font(null,FontWeight.BOLD,18));
        TilePane tiles = new TilePane();

        tiles.setSnapToPixel(false);
        TilePane.setAlignment(right,Pos.CENTER_LEFT);
        tiles.getChildren().addAll(left,right);
        tiles.setPrefTileHeight(40);
        tiles.prefTileWidthProperty().bind(Bindings.selectDouble(tiles.parentProperty(),"width").divide(2));
        return tiles;
    }
    private Node createBackground(){
        return RegionBuilder.create()
                .style("-fx-background-color: radial-gradient(radius 100%,white,gray)")
                .build();
    }
    private Node tiles(){
        GridPane board = new GridPane();
        for(int i=0;i<GoModel.BOARD_SIZE;i++){
            for(int j=0;j<GoModel.BOARD_SIZE;j++){
                GoSquare square = new GoSquare(i,j);
                GoPiece piece = new GoPiece();
                piece.ownerProperty().bind(model.board[i][j]);
                board.add(StackPaneBuilder.create().children(
                        square,
                        piece
                ).build(),i,j);

            }
        }
        board.setPrefSize(400,400);
        return board;
    }

    public static void main(String [] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(SceneBuilder.create()
                .width(600)
                .height(400)
                .root(BorderPaneBuilder
                        .create()
                        .top(createTitle())
                        .center(StackPaneBuilder.create().children(
                                createBackground(),tiles()
                        ).build())
                        .bottom(createScoreBoxes())
                        .build())
                .build());

        primaryStage.show();

        Node game = BorderPaneBuilder.create()
                .top(createTitle())
                .center(StackPaneBuilder.create().children(
                                createBackground(),
                        tiles()
                ).build())
                .build();

        Node restart = restart();

        Node scores = scores();

        primaryStage.setScene(SceneBuilder.create()
                .width(800)
                .height(800)
                .root(AnchorPaneBuilder.create().children(
                        game,
                        scores,
                        restart
                        ).build()
                ).build());

        AnchorPane.setTopAnchor(game,0d);
        AnchorPane.setBottomAnchor(game,0d);
        AnchorPane.setLeftAnchor(game,0d);
        AnchorPane.setRightAnchor(game,0d);
        AnchorPane.setTopAnchor(scores,10d);
        AnchorPane.setLeftAnchor(scores,10d);
        AnchorPane.setRightAnchor(restart,10d);
        AnchorPane.setTopAnchor(restart,10d);


    }

    private Node restart(){
        return ButtonBuilder.create().text("Restart").onAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                model.restart();
            }
        }).build();
    }
    private Node scores(){
        return ButtonBuilder.create().text("Scores").onAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Score");
                List<Integer> scores= model.getScores();
                alert.setContentText("Czarny: " + scores.get(0) + "\nBiały: " + scores.get(1));

                alert.showAndWait();

            }
        }).build();
    }
}
