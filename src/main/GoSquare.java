package main;

import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.effect.Light;
import javafx.scene.effect.LightingBuilder;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.File;

/**
 * Created by Konrad on 30.04.2016.
 */
public class GoSquare extends Region {

    private static GoModel model = GoModel.getInstance();

    private Region highlight = RegionBuilder.create()
            .opacity(0)
            .style("-fx-border-width: 3; -fx-border-color: dodgerblue")
            .build();

    private FadeTransition highlightTransition = FadeTransitionBuilder.create()
            .node(highlight)
            .duration(Duration.millis(200))
            .fromValue(0)
            .toValue(1)
            .build();

    public GoSquare(){
        setStyle("-fx-background-color: burlywood");
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135);
        light.setElevation(30);
        setEffect(LightingBuilder.create().light(light).build());
        setPrefSize(200,200);
        getChildren().add(highlight);
    }

    public GoSquare(final int x,final int y){
        //setStyle("-fx-background-color: burlywood");


        String uri = getBackgroundURI(x,y);
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135);
        light.setElevation(30);
        setEffect(LightingBuilder.create().light(light).build());
        setPrefSize(200,200);
        getChildren().add(highlight);

        styleProperty().bind(Bindings.when(model.legalMove(x,y))
                .then("-fx-background-image: url('" + uri +
                        "'); " +
                        "-fx-background-size: cover;-fx-background-repeat: no-repeat;-fx-background-size: stretch")
                .otherwise("-fx-background-image: url('" + uri +
                        "'); " +
                        "-fx-background-size: cover;-fx-background-repeat: no-repeat;-fx-background-size: stretch"));
        //Light.Distant light = new Light.Distant();

        //.then("-fx-background-color: derive(dodgerblue,-60%)")

        addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t){
                if(model.legalMove(x,y).get()){
                    highlightTransition.setRate(1);
                    highlightTransition.play();
                }
            }
        });

        addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                highlightTransition.setRate(-1);
                highlightTransition.play();
            }
        });

        addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                model.play(x,y);
                highlightTransition.setRate(1);
                highlightTransition.play();
            }
        });
    }

    @Override
    protected void layoutChildren(){
        layoutInArea(highlight,0,0,getWidth(),getHeight(),getBaselineOffset(), HPos.CENTER, VPos.CENTER);
    }

    public String getBackgroundURI(int x,int y){
        String uri = "File:";

        if(x>0 && y>0 && x< GoModel.BOARD_SIZE-1 && y<GoModel.BOARD_SIZE -1){
            uri+="tlo/srodek.png";
        }
        else if(x==0 && y!=0 && y!= GoModel.BOARD_SIZE-1){
            uri+="tlo/bok3.png";
        }
        else if(y==0 && x!=0 && x!=GoModel.BOARD_SIZE-1)
        {
            uri+="tlo/bok4.png";
        }
        else if(y==GoModel.BOARD_SIZE-1 && x!=0 && x!=GoModel.BOARD_SIZE-1)
        {
            uri+="tlo/bok1.png";
        }
        else if(x==GoModel.BOARD_SIZE-1 && y!=0 && y!=GoModel.BOARD_SIZE-1)
        {
            uri+="tlo/bok2.png";
        }
        else if(x==0 && y==0)
        {
            uri+="tlo/krawedz3.png";
        }
        else if(x==0 && y==GoModel.BOARD_SIZE-1)
        {
            uri+="tlo/krawedz2.png";
        }
        else if(x==GoModel.BOARD_SIZE-1 && y==0)
        {
            uri+="tlo/krawedz4.png";
        }
        else{
            uri+="tlo/krawedz1.png";
        }
        return uri;
    }
}
