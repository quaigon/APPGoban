package com.quaigon.kamil.goban;

import com.quaigon.kamil.goban.view.GobanView;
import com.quaigon.kamil.sgfparser.SGFProvider;
import com.quaigon.kamil.sgfparser.SGFnode;
import com.quaigon.kamil.sgfparser.Treewalker;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameManager implements TouchListener {
    private List<Goban> gameStates;
    private Treewalker walker;
    private GobanView gobanView;
    private int xToCheck;
    private int yToCheck;

    public GameManager(GobanView gobanView) {
        this.gobanView = gobanView;
        this.gameStates = new ArrayList<>();
    }

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
        if (matcher.find()) comment = matcher.group(1);

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

    private Goban getLast() {
        Goban goban = new Goban();
        if (gameStates.size() > 0) {
            goban = gameStates.get(gameStates.size() - 1);
        }
        return goban;
    }

    @Override
    public void onPositionGet(int x, int y) {
        Goban goban = new Goban(getLast());
        goban.printStones();
//        Ln.d("x:" + x + " y:" + y);
//        Ln.d(goban.isEmpty(x, y));
        int color = gameStates.size() % 2 == 0 ? 1 : 0;
        goban.putStone(x, y, color);
//        goban.printStones();
//        Ln.d(goban.isEmpty(x, y));
        if (!goban.isEmpty(x, y)) {
            gameStates.add(goban);
            gobanView.setGobanModel(goban);
            gobanView.invalidate();
        }
    }
}
