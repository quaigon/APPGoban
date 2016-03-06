package com.quaigon.kamil.goban;

import com.quaigon.kamil.sgfparser.SGFProvider;
import com.quaigon.kamil.sgfparser.SGFnode;
import com.quaigon.kamil.sgfparser.Treewalker;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import roboguice.util.Ln;

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


    public StateContainer getNextState() {
        Goban goban = new Goban(getLastState());
        String comment = null;
        if (this.walker.getNextMove() == null) {
            return null;
        }
        String current = walker.printCurrent();

        Pattern move = Pattern.compile("[WB]\\[\\w\\w\\]");
        Matcher matcher = move.matcher(current);
        if (matcher.find()) goban.addSGFMove(matcher.group());
        Pattern commentPattern = Pattern.compile("C\\[([\\u0000-\\u00FF]+)\\]");
        matcher = commentPattern.matcher(current);
        if (matcher.find()) comment = matcher.group(1
        );

        this.gameStates.add(goban);
        return new StateContainer(goban, comment);
    }

    public Goban getPreviousState() {
        this.walker.getPreviousMove();
        gameStates.remove(gameStates.size() - 1);
        Goban goban = gameStates.get(gameStates.size() - 1);
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
