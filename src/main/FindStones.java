package main;

import javafx.beans.property.ObjectProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konrad on 02.05.2016.
 */
public class FindStones {

    private List<Position> visitedNode;
    private ObjectProperty<Owner>[][] board;
    private Owner owner;
    private List<Position> queue;
    private int cellX;
    private int cellY;

    public FindStones(int cellX, int cellY, Owner owner) {
        board = GoModel.getInstance().board;
        visitedNode = new ArrayList<>();
        this.owner = owner.opposite();
        queue = new ArrayList<>();
        this.cellX = cellX;
        this.cellY = cellY;
    }

    public List<Position> getStonesListToRemove() {

        List<Position> globalStonesToRemove = new ArrayList<>();

        if (cellX - 1 >= 0) {
            queue.add(new Position(cellX - 1, cellY));
            globalStonesToRemove.addAll(getLocalStonesListToRemove());
        }
        if (cellY + 1 < board.length) {
            queue.add(new Position(cellX, cellY + 1));
            globalStonesToRemove.addAll(getLocalStonesListToRemove());
        }
        if (cellX + 1 < board.length) {
            queue.add(new Position(cellX + 1, cellY));
            globalStonesToRemove.addAll(getLocalStonesListToRemove());
        }
        if (cellY - 1 >= 0) {
            queue.add(new Position(cellX, cellY - 1));
            globalStonesToRemove.addAll(getLocalStonesListToRemove());
        }

        return globalStonesToRemove;
    }

    private List<Position> getLocalStonesListToRemove() {
        List<Position> localStones = new ArrayList<>();

        while (!queue.isEmpty()) {
            if (!visitedNode.contains(queue.get(0))) {
                visitedNode.add(queue.get(0));
                Position p = queue.remove(0);

                if (board[p.i][p.j].get() == owner) {
                    if (isPossible(p)) {
                        localStones.add(p);
                    } else {
                        queue.clear();
                        localStones.clear();
                    }
                } else if (board[p.i][p.j].get() == Owner.NONE) {
                    queue.clear();
                    localStones.clear();
                }
            } else {
                queue.remove(0);
            }
        }
        visitedNode.clear();
        return localStones;
    }

    private boolean isPossible(Position p) {
        boolean possible = false;
        if (board[p.i][p.j].get() == owner) {
            possible = true;
            addToQueue(p.i, p.j);
        } else if (board[p.i][p.j].get() == owner.opposite()) {
            possible = true;
        }
        return possible;
    }

    private void addToQueue(int cellX, int cellY) {
        if (cellX - 1 >= 0) {
            queue.add(new Position(cellX - 1, cellY));
        }
        if (cellY + 1 < board.length) {
            queue.add(new Position(cellX, cellY + 1));
        }
        if (cellX + 1 < board.length) {
            queue.add(new Position(cellX + 1, cellY));
        }
        if (cellY - 1 >= 0) {
            queue.add(new Position(cellX, cellY - 1));
        }
    }

    public boolean isSuicide() {

        board[cellX][cellY].setValue(owner.opposite());
        boolean foundNone = findNone(cellX, cellY);

        if (!foundNone) {
            int l = getStonesListToRemove().size();
            board[cellX][cellY].setValue(Owner.NONE);
            return l == 0;
        } else {
            return false;
        }

    }


    private boolean findNone(int cellX, int cellY) {
        Position p = null;
        addToQueue(cellX, cellY);
        while (!queue.isEmpty() && p == null) {
            Position pom = queue.remove(0);
            if (!visitedNode.contains(pom) && (cellX != pom.i || cellY != pom.j)) {
                if (board[pom.i][pom.j].get() == Owner.NONE) {
                    p = pom;
                } else if (board[pom.i][pom.j].get() == owner.opposite()) {
                    addToQueue(pom.i, pom.j);
                    visitedNode.add(new Position(pom.i, pom.j));
                }
            }
        }
        visitedNode.clear();
        queue.clear();
        return p != null;
    }
}
