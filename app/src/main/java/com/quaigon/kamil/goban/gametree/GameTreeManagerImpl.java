package com.quaigon.kamil.goban.gametree;

import com.quaigon.kamil.goban.gobaninterface.CommentaryInterface;
import com.quaigon.kamil.goban.gobanlogic.GobanModel;

import java.util.ArrayList;
import java.util.List;

public class GameTreeManagerImpl implements GameTreeManager, CommentaryInterface {
    private Node root;
    private Node actual;
    private int moveNo;
    private List<GobanModel> gameStates;


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
        gameStates.add(new GobanModel());
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
        if (null != actual.getNode(0)) {
            Node nextNode = actual.getNode(0);
            actual = nextNode;
            move = actual.getMove();
            moveNo++;
        }
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
    public GobanModel getNextState() {
        GobanModel gobanModel;
        gobanModel = new GobanModel(gameStates.get(gameStates.size() - 1));
        Move move = getNextMove();
        if (move != null) {
            gobanModel.putStone(move);
            gameStates.add(gobanModel);
            return gameStates.get(gameStates.size() - 1);
        }
        return null;
    }

    @Override
    public GobanModel getPrevState() {
        Move move = getPrevMove();
        gameStates.remove(gameStates.size() - 1);
        return gameStates.get(gameStates.size() - 1);
    }

    @Override
    public GobanModel getLast() {
        if (gameStates.size() > 0) return gameStates.get(gameStates.size() - 1);
        else return null;
    }

    @Override
    public String getComment() {
        if (null != actual)
        return actual.getCommet();
        return null;
    }

    @Override
    public void putMove(Move move) {
        Node newNode;
        GobanModel gobanModel = gameStates.get(gameStates.size() - 1);

        if (gobanModel.isEmpty(move)) {
            if (null != actual) {
                newNode = new Node(actual, move);
                actual.addChild(newNode);
            } else {
                newNode = new Node(null, move);
                root = newNode;
            }
            gobanModel.putStone(move);
            gameStates.add(gobanModel);
            actual = newNode;
            moveNo++;
        }
    }

    @Override
    public boolean hasNext() {
        return actual.hasChild();
    }
}
