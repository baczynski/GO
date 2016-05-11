package main;


import javafx.scene.paint.Color;

/**
 * Created by Konrad on 29.04.2016.
 */
public enum Owner {
    NONE,WHITE,BLACK;

    public Owner opposite(){
        return this ==WHITE ? BLACK :this == BLACK ? WHITE : NONE;
    }

    public Color getColor(){
        return this == Owner.WHITE ? Color.WHITE : Color.BLACK;
    }
    public String getColorStyle(){
        return this == Owner.WHITE ? "white" : "black";
    }
}
