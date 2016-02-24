package com.quaigon.kamil.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.inject.Inject;
import com.quaigon.kamil.connection.AuthenticationRepository;
import com.quaigon.kamil.connection.ConnectionService;
import com.quaigon.kamil.connection.OAuthServiceGenrator;
import com.quaigon.kamil.goban.GobanActivity;
import com.quaigon.kamil.goban.R;
import com.quaigon.kamil.pojo.AccessToken;
import com.quaigon.kamil.pojo.Game;
import com.quaigon.kamil.pojo.Games;

import java.util.List;

import retrofit2.Call;
import roboguice.activity.RoboListActivity;
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

        @Override
        public Void call() throws Exception {

            ConnectionService connectionService = OAuthServiceGenrator.createService(ConnectionService.class, token, "application/json");
            Call<Games> call = connectionService.getGames();
            Games games = call.execute().body();
            listGames = games.getResults();
            gameAdapter = new GameAdapter(listGames, GamesListActivity.this);
            return null;
        }

        protected GetGamesAsyncTask(Context context) {
            super(context);
        }


        @Override
        protected void onSuccess(Void result) throws Exception {
            setListAdapter(gameAdapter);
            super.onSuccess(result);

        }
    }


    private class GetGameSgfAsyncTask extends RoboAsyncTask<String> {

        private long position;

        @Override
        public String call() throws Exception {

            ConnectionService connectionService = OAuthServiceGenrator.createService(ConnectionService.class, token, "application/x-go-sgf");
            Call<String> call = connectionService.getSgf(position);
            String gameSgf = call.execute().body();
            return gameSgf;
        }

        protected GetGameSgfAsyncTask(Context context) {
            super(context);
        }

        protected GetGameSgfAsyncTask(Context context, long position) {
            super(context);
            this.position = position;
        }


        @Override
        protected void onSuccess(String result) throws Exception {
            Intent intent = new Intent(GamesListActivity.this, GobanActivity.class);
            intent.putExtra("sgf", result);
            startActivity(intent);

            super.onSuccess(result);

        }
    }


}
