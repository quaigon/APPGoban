package com.quaigon.kamil.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.google.inject.Inject;
import com.quaigon.kamil.connection.AuthenticationRepository;
import com.quaigon.kamil.connection.GameConnectionService;
import com.quaigon.kamil.connection.OAuthServiceGenrator;
import com.quaigon.kamil.dto.token.AccessToken;
import com.quaigon.kamil.dto.gamelist.Game;
import com.quaigon.kamil.dto.gamelist.Games;
import com.quaigon.kamil.goban.R;
import com.quaigon.kamil.goban.gobaninterface.WorkType;
import com.quaigon.kamil.goban.view.GobanActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import roboguice.activity.RoboListActivity;
import roboguice.util.Ln;
import roboguice.util.RoboAsyncTask;

public class GamesListActivity extends RoboListActivity {

    @Inject
    AuthenticationRepository authRepo;

    private AccessToken token;
    private GameAdapter gameAdapter;
    private List<Game> listGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);




        listGames = new ArrayList<>();
        this.token = authRepo.loadAccessToken();

        GetGamesAsyncTask getGamesAsyncTask = new GetGamesAsyncTask(this);
        getGamesAsyncTask.execute();

    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Game game = (Game) l.getItemAtPosition(position);
        GetGameSgfAsyncTask getGameSgfAsyncTask = new GetGameSgfAsyncTask(GamesListActivity.this, game.getId());
        getGameSgfAsyncTask.execute();

    }


    private class GetGamesAsyncTask extends RoboAsyncTask<Void> {

        protected GetGamesAsyncTask(Context context) {
            super(context);
        }

        @Override
        public Void call() throws Exception {
            List <Game> results = new ArrayList<>();
            String next;
            int i = 1;
            GameConnectionService connectionService = OAuthServiceGenrator.createService(GameConnectionService.class, token, "application/json");
            Call<Games> call = connectionService.getGames(1);
            Games games = call.execute().body();
            listGames.addAll(games.getResults());
            next = games.getNext();
            while (null != next) {
                call = connectionService.getGames(i);
                games = call.execute().body();
                listGames.addAll(games.getResults());
                next = games.getNext();
                i++;
            }

            for (Game g : listGames) {
                if (!g.isAnnulled() && g.getWidth() == 19) {
                    results.add(g);
                }
            }

            gameAdapter = new GameAdapter(results, GamesListActivity.this);
            return null;
        }

        @Override
        protected void onSuccess(Void result) throws Exception {
            setListAdapter(gameAdapter);
            super.onSuccess(result);

        }

        @Override
        protected void onException(Exception e) throws RuntimeException {
            Ln.e(e);
        }
    }


    private class GetGameSgfAsyncTask extends RoboAsyncTask<String> {

        private long position;

        protected GetGameSgfAsyncTask(Context context) {
            super(context);
        }

        protected GetGameSgfAsyncTask(Context context, long position) {
            super(context);
            this.position = position;
        }

        @Override
        public String call() throws Exception {

            GameConnectionService connectionService = OAuthServiceGenrator.createService(GameConnectionService.class, token, "application/x-go-sgf");
            Call<String> call = connectionService.getSgf(position);
            String gameSgf = call.execute().body();
            return gameSgf;
        }

        @Override
        protected void onSuccess(String result) throws Exception {
//            Game game = listGames.get((int) position);

            Intent intent = new Intent(GamesListActivity.this, GobanActivity.class);
            intent.putExtra("sgf", result);
//            if (null == game.getEnded()) intent.putExtra("workType", WorkType.REVIEW);
            startActivity(intent);
            super.onSuccess(result);
        }

        @Override
        protected void onException(Exception e) throws RuntimeException {
            Ln.e(e);
        }
    }


}
