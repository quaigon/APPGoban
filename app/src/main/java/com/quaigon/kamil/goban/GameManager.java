package com.quaigon.kamil.goban;

import com.quaigon.kamil.sgfparser.SGFProvider;
import com.quaigon.kamil.sgfparser.SGFnode;
import com.quaigon.kamil.sgfparser.Treewalker;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private List<Goban> gameStates;
    private Treewalker walker;

    public GameManager(SGFProvider provider) {
        init(provider);
    }




    private void init(SGFProvider provider) {
        this.gameStates = new ArrayList<>();
        SGFnode node = new SGFnode();
        node.fromString(provider.getLines());
        this.walker = new Treewalker(node);
        gameStates.add(new Goban());
    }


    public Goban getNextState() {
        Goban goban = new Goban(getLastState());
        if (this.walker.getNextMove() == null) {
            return null;
        }

        goban.addSGFMove(walker.printCurrent());
        this.gameStates.add(goban);
        return goban;
    }


    public Goban getPreviousState() {
        this.walker.getPreviousMove();
        gameStates.remove(gameStates.size()-1);
        Goban goban = gameStates.get(gameStates.size()-1);
        return goban;
    }

    public Goban getLastState() {
        return this.gameStates.get(this.gameStates.size() - 1);
    }


    public List<Goban> getGameStates() {
        return gameStates;
    }

    int getMoveNo() {
        return gameStates.size() - 1;
    }
}
