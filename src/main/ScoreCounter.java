package main;

import javafx.beans.property.ObjectProperty;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konrad on 11.05.2016.
 */
public class ScoreCounter {

    private List<Position> visitedNode;
    private List<Position> globalVisitedNode;
    private int whiteScores=0;
    private int blackScores =0;
    private ObjectProperty<Owner>[][] board;
    private boolean stop = false;

    public ScoreCounter(){
        board = GoModel.getInstance().board;
        visitedNode = new ArrayList<>();
        globalVisitedNode = new ArrayList<>();
        fillScores();
    }
    private void fillScores(){
        whiteScores = getScore(Owner.WHITE);
        blackScores = getScore(Owner.BLACK);
    }

    private int getScore(Owner o) {
        int ownerScores =0;
        globalVisitedNode.clear();
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board.length;j++){
                if(!globalVisitedNode.contains(new Position(i,j))) {
                    stop = false;
                    visitedNode.clear();
                    int score = countScore(new Position(i, j), o);
                    ownerScores += !stop ? score : 0;
                    globalVisitedNode.addAll(visitedNode);
                }
            }
        }
        return ownerScores;
    }

    private int countScore(Position p, Owner owner) {
        if(p.i>=0 && p.j>=0 && p.i<board.length && p.j < board.length && !stop) {
            if (board[p.i][p.j].get() == owner) {
                return 0;
            } else if (board[p.i][p.j].get() == owner.opposite()) {
                stop = true;
                return 0;
            } else if(!visitedNode.contains(p)) {
                visitedNode.add(p);
                return 1 + countScore(new Position(p.i - 1, p.j), owner) + countScore(new Position(p.i , p.j - 1), owner) +countScore(new Position(p.i + 1, p.j), owner) + countScore(new Position(p.i , p.j + 1), owner);
            }
        }
        return 0;
    }

    public int getWhiteScores(){
        return whiteScores;
    }
    public int getBlackScores(){
        return blackScores;
    }
//    Flood-fill (node, target-color, replacement-color):
//            1. If target-color is equal to replacement-color, return.
//            2. If the color of node is not equal to target-color, return.
//            3. Set the color of node to replacement-color.
//    4. Perform Flood-fill (one step to the south of node, target-color, replacement-color).
//    Perform Flood-fill (one step to the north of node, target-color, replacement-color).
//    Perform Flood-fill (one step to the west of node, target-color, replacement-color).
//    Perform Flood-fill (one step to the east of node, target-color, replacement-color).
//            5. Return.
}
