package com.quaigon.kamil.goban.gametree;

import com.quaigon.kamil.goban.CommentaryInterface;
import com.quaigon.kamil.goban.Goban;

import java.util.ArrayList;
import java.util.List;

public class GameTreeManagerImpl implements GameTreeManager, CommentaryInterface {
    private Node root;
    private Node actual;
    private int moveNo;
    private List<Goban> gameStates;


    public GameTreeManagerImpl() {
        this.root = null;
        this.actual = null;
        init();
    }

    public GameTreeManagerImpl(Node root) {
        this.root = root;
        this.actual = root;
        init();
    }

    private void init() {
        this.moveNo = 0;
        this.gameStates = new ArrayList<>();
        gameStates.add(new Goban());
    }

    @Override
    public int getMoveNo() {
        return moveNo;
    }

    private Move getNextMove() {
        if (moveNo == 0) {
            moveNo++;
            return actual.getMove();
        }
        Move move = null;
        Node nextNode = actual.getNode(0);
        actual = nextNode;
        move = actual.getMove();
        moveNo++;
        return move;
    }

    private Move getPrevMove() {
        Move move = null;
        if (0 < moveNo) {
            if (!actual.isRoot()) {
                Node prevNode = actual.getParent();
                actual = prevNode;
                move = actual.getMove();
            }
            moveNo--;
        }
        return move;
    }

    @Override
    public Goban getNextState() {
        Goban goban;
        goban = new Goban(gameStates.get(gameStates.size() - 1));
        Move move = getNextMove();
        goban.putStone(move);
        gameStates.add(goban);

        return gameStates.get(gameStates.size() - 1);
    }

    @Override
    public Goban getPrevState() {
        Move move = getPrevMove();
        gameStates.remove(gameStates.size() - 1);
        return gameStates.get(gameStates.size() - 1);
    }

    @Override
    public Goban getLast() {
        if (gameStates.size() > 0) return gameStates.get(gameStates.size() - 1);
        else return null;
    }

    @Override
    public String getComment() {
        return actual.getCommet();
    }

    @Override
    public void putMove(Move move) {
        Node newNode;
        Goban goban = gameStates.get(gameStates.size() - 1);

        if (goban.isEmpty(move)) {
            if (null != actual) {
                newNode = new Node(actual, move);
                actual.addChild(newNode);
            } else {
                newNode = new Node(null, move);
                root = newNode;
            }
            goban.putStone(move);
            gameStates.add(goban);
            actual = newNode;
            moveNo++;
        }
    }
}
