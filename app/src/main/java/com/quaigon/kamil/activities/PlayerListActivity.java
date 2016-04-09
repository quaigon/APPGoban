package com.quaigon.kamil.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.inject.Inject;
import com.quaigon.kamil.connection.AuthenticationRepository;
import com.quaigon.kamil.connection.OAuthServiceGenrator;
import com.quaigon.kamil.connection.PlayersConnectionService;
import com.quaigon.kamil.dto.token.AccessToken;
import com.quaigon.kamil.dto.player.Friends;
import com.quaigon.kamil.dto.player.Player;
import com.quaigon.kamil.goban.R;

import java.util.List;

import retrofit2.Call;
import roboguice.activity.RoboListActivity;
import roboguice.util.RoboAsyncTask;

public class PlayerListActivity extends RoboListActivity {

    @Inject
    AuthenticationRepository authRepo;

    private AccessToken token;
    private PlayerAdapter playerAdapter;
    private List<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);

        this.token = authRepo.loadAccessToken();

        GetPlayersAsyncTask getPlayersAsyncTask  = new GetPlayersAsyncTask(this);
        getPlayersAsyncTask.execute();
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Player player = (Player) l.getItemAtPosition(position);

        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra("id", player.getId());
        intent.putExtra("username", player.getUsername());
        startActivity(intent);
    }

    private class GetPlayersAsyncTask extends RoboAsyncTask<Void> {


        protected GetPlayersAsyncTask(Context context) {
            super(context);
        }

        @Override
        public Void call() throws Exception {
            PlayersConnectionService playersConnectionService = OAuthServiceGenrator.createService(PlayersConnectionService.class, token, "application/json");
            Call<Friends> call = playersConnectionService.getFriends();
            Friends friends;
            friends = call.execute().body();
            players = friends.getResults();
            playerAdapter = new PlayerAdapter(players, PlayerListActivity.this);
            return null;
        }

        @Override
        protected void onSuccess(Void aVoid) throws Exception {
            setListAdapter(playerAdapter);
            super.onSuccess(aVoid);
        }
    }


    private class StarChallangeAsyncTask extends RoboAsyncTask<Void> {
        private int id;

        protected StarChallangeAsyncTask(Context context) {
            super(context);
        }

        protected StarChallangeAsyncTask(Context context, int id) {
            super(context);
            this.id = id;
        }

        @Override
        public Void call() throws Exception {
            return null;
        }

        @Override
        protected void onSuccess(Void aVoid) throws Exception {
            super.onSuccess(aVoid);
        }
    }


}
