package com.quaigon.kamil.goban;

import android.widget.TextView;

import com.quaigon.kamil.goban.gametree.GameTreeManager;
import com.quaigon.kamil.goban.gametree.Move;
import com.quaigon.kamil.goban.view.GobanView;

public class GobanInterfaceImpl implements GobanInterface, TouchListener {
    private GameTreeManager gameTreeManager;
    private GobanView gobanView;
    private TextView moveNoView;

    public GobanInterfaceImpl(GameTreeManager gameTreeManager, GobanView gobanView, TextView moveNoView) {
        this.gameTreeManager = gameTreeManager;
        this.gobanView = gobanView;
        this.moveNoView = moveNoView;
    }

    @Override
    public void onPositionGet(int x, int y) {
        int color = gameTreeManager.getMoveNo() % 2 == 0 ? 1 : 0;

        gameTreeManager.putMove(new Move(x,y,color));
        gobanView.setGobanModel(gameTreeManager.getLast());
        refreshView();
    }

    @Override
    public void nextState() {
        gobanView.setGobanModel(gameTreeManager.getNextState());
        refreshView();
    }

    @Override
    public void prevState() {
        gobanView.setGobanModel(gameTreeManager.getPrevState());
        refreshView();
    }

    private void refreshView() {
        gobanView.invalidate();
        moveNoView.setText(String.valueOf(gameTreeManager.getMoveNo()));
    }

}
