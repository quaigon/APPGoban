package com.quaigon.kamil.goban.gobaninterface;

import android.content.Context;
import android.widget.TextView;

import com.quaigon.kamil.connection.GameConnectionService;
import com.quaigon.kamil.connection.LoginConnectionService;
import com.quaigon.kamil.connection.OAuthServiceGenrator;
import com.quaigon.kamil.dto.gamelist.MoveContainer;
import com.quaigon.kamil.dto.gamelist.Response;
import com.quaigon.kamil.dto.token.AccessToken;
import com.quaigon.kamil.goban.gametree.GameTreeManager;
import com.quaigon.kamil.goban.gametree.Move;
import com.quaigon.kamil.goban.gobanlogic.Goban;
import com.quaigon.kamil.goban.view.GobanView;

import retrofit2.Call;
import roboguice.util.Ln;
import roboguice.util.RoboAsyncTask;

public class GobanInterfaceImpl implements GobanInterface, TouchListener {
    private WorkType workType;
    private GameTreeManager gameTreeManager;
    private GobanView gobanView;
    private TextView moveNoView;

    public GobanInterfaceImpl(GameTreeManager gameTreeManager, GobanView gobanView, TextView moveNoView, WorkType workType) {
        this.workType = workType;
        this.gameTreeManager = gameTreeManager;
        this.gobanView = gobanView;
        this.moveNoView = moveNoView;
    }

    @Override
    public void onPositionGet(int x, int y) {
        int color = gameTreeManager.getMoveNo() % 2 == 0 ? 1 : 0;
        gameTreeManager.putMove(new Move(x, y, color));
        gobanView.setGobanModel(gameTreeManager.getLast());
        if (WorkType.PLAY == workType) {

        }

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

    @Override
    public boolean hasNext() {
        return gameTreeManager.hasNext();
    }

    private void refreshView() {
        gobanView.invalidate();
        moveNoView.setText(String.valueOf(gameTreeManager.getMoveNo()));
    }


    private class PostMoveAsyncTask <Void> extends RoboAsyncTask{
        private MoveContainer move;
        private long id;

        protected PostMoveAsyncTask(Context context, MoveContainer move, long id) {
            super(context);
            this.move = move;
            this.id = id;
        }

        @Override
        public Object call() throws Exception {
            GameConnectionService connectionService = OAuthServiceGenrator.createService(GameConnectionService.class);
            Call<Response> call = connectionService.postMove(id, move);
            Response response = call.execute().body();

            return null;
        }

        @Override
        protected void onSuccess(Object o) throws Exception {
            super.onSuccess(o);
        }

        @Override
        protected void onException(Exception e) throws RuntimeException {
            Ln.e(e);
        }
    }
}
