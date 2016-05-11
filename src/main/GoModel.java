package main;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.NumberExpression;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Konrad on 29.04.2016.
 */
public class GoModel {
    public static int BOARD_SIZE = 9;

    public ObjectProperty<Owner> turn = new SimpleObjectProperty<>(Owner.BLACK);
    public ObjectProperty<Owner>[][] board = new ObjectProperty[BOARD_SIZE][BOARD_SIZE];
    public ObjectProperty<Owner>[][] previousBoard;

    public GoModel() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new SimpleObjectProperty<>(Owner.NONE);
            }
        }
    }

    public static GoModel getInstance() {
        return GoModelHolder.INSTANCE;
    }


    private static class GoModelHolder {
        private static final GoModel INSTANCE = new GoModel();
    }


    public List<Integer> getScores() {
        List<Integer> scores = new ArrayList<>();
        ScoreCounter sc = new ScoreCounter();
        scores.add(sc.getBlackScores());
        scores.add(sc.getWhiteScores());
        return scores;
    }


    public void restart() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j].setValue(Owner.NONE);
            }
        }
        turn.setValue(Owner.BLACK);
    }

    public BooleanBinding legalMove(int x, int y) {
        return board[x][y].isEqualTo(Owner.NONE);
    }

    private boolean isSuicide(int cellX, int cellY, Owner owner) {
        FindStones fs = new FindStones(cellX, cellY, owner);
        return fs.isSuicide();
    }


    public void play(int cellX, int cellY) {
        if (legalMove(cellX, cellY).get() && !isSuicide(cellX, cellY, turn.getValue())) {
            ObjectProperty<Owner>[][] copiedBoard = copyBoard(board);
            board[cellX][cellY].setValue((turn.get()));
            List<Position> wonStones = getWonStones(cellX, cellY, turn.getValue());

            removeWonStones(wonStones);
            if (previousBoard != null && previousEqualsCurrent()) {
                setCopiedBoardAsBoard(copiedBoard);
            } else {
                turn.setValue(turn.getValue().opposite());
                previousBoard = copiedBoard;
            }

        }
    }

    private ObjectProperty<Owner>[][] copyBoard(ObjectProperty<Owner>[][] board) {
        ObjectProperty<Owner>[][] copiedBoard = new ObjectProperty[board.length][board.length];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                copiedBoard[i][j] = new SimpleObjectProperty<>(board[i][j].get());
            }
        }
        return copiedBoard;
    }

    private List<Position> getWonStones(int cellX, int cellY, Owner owner) {
        FindStones fs = new FindStones(cellX, cellY, owner);
        List<Position> stonesToRemove = fs.getStonesListToRemove();

        return stonesToRemove;
    }

    private void removeWonStones(List<Position> stonesToRemove) {
        for (Position p : stonesToRemove) {
            board[p.i][p.j].setValue(Owner.NONE);
        }
    }

    private boolean previousEqualsCurrent() {
        boolean equal = true;

        for (int i = 0; i < board.length && equal; i++) {
            for (int j = 0; j < board.length && equal; j++) {
                if (board[i][j].get() != previousBoard[i][j].get()) {
                    equal = false;
                }
            }
        }
        return equal;
    }

    private void setCopiedBoardAsBoard(ObjectProperty<Owner>[][] copiedBoard) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j].setValue(copiedBoard[i][j].getValue());
            }
        }
    }


}
