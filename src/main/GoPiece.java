package main;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.Region;

/**
 * Created by Konrad on 30.04.2016.
 */
public class GoPiece extends Region {

    private ObjectProperty<Owner> ownerProperty = new SimpleObjectProperty<>(this, "owner", Owner.BLACK.NONE);

    public ObjectProperty<Owner> ownerProperty() {
        return ownerProperty;
    }

    public Owner getOwner() {
        return ownerProperty.get();
    }

    public void setOwner(Owner owner) {
        ownerProperty.set(owner);
    }

    public GoPiece() {
        styleProperty().bind(Bindings.when(ownerProperty.isEqualTo(Owner.NONE))
                .then("radius 0")
                .otherwise(Bindings.when(ownerProperty.isEqualTo(Owner.WHITE))
                        .then("-fx-background-color: radial-gradient(radius 100%, white .4, gray .9, darkgray 1)")
                        .otherwise("-fx-background-color: radial-gradient(radius 100%, white 0, black .6)"))
                .concat("; -fx-background-radius: 1000em; -fx-background-insets: 5"));

        Reflection reflection = new Reflection();
        reflection.setFraction(1);
        reflection.topOffsetProperty().bind(heightProperty().multiply(-.75));
        setEffect(reflection);
        setPrefSize(40,40);
        setMouseTransparent(true);
    }
    public GoPiece(Owner owner){
        this();
        ownerProperty.setValue(owner);
    }
}
