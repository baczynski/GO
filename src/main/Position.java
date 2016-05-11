package main;

/**
 * Created by Konrad on 02.05.2016.
 */
public class Position {
    int i,j;
    public Position(int i,int j){
        this.i=i;
        this.j =j;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Position){
            Position p1 = (Position) o;
            return i==p1.i && j==p1.j;
        }
        return false;
    }
}
