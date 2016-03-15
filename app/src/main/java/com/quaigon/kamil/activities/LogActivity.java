package com.quaigon.kamil.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.inject.Inject;
import com.quaigon.kamil.connection.AuthenticationRepository;
import com.quaigon.kamil.connection.ConnectionService;
import com.quaigon.kamil.connection.OAuthServiceGenrator;
import com.quaigon.kamil.goban.GobanActivity;
import com.quaigon.kamil.goban.R;
import com.quaigon.kamil.pojo.AccessToken;

import retrofit2.Call;
import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;
import roboguice.util.Ln;
import roboguice.util.RoboAsyncTask;

public class LogActivity extends RoboActionBarActivity {

    @InjectView(R.id.signInButton)
    private Button signInButton;

    @InjectView(R.id.loadGame)
    private Button loadGameButton;

    @InjectView(R.id.usernameEditText)
    private EditText usernameEditText;


    @InjectView(R.id.passwordEditText)
    private EditText passwordEditText;

    @Inject
    AuthenticationRepository authRepo;

    @Inject
    private Resources resources;

    private final static String clientId = "c864df57fa2c675f313d";
    private final static String clientSecret = "fe53725ce1156a82a57b73cde649561d60efc0c6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);


        this.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                GetTokenAsyncTask getTokenAsyncTask = new GetTokenAsyncTask(LogActivity.this);
                getTokenAsyncTask.execute();

            }
        });

        this.loadGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("file/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Wybierz SGF"), 1000);
                Intent intent = new Intent(LogActivity.this, GobanActivity.class);
                startActivity(intent);
            }
        });
        usernameEditText.setText(resources.getString(R.string.username));
        passwordEditText.setText(resources.getString(R.string.password));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri uri = data.getData();
        Intent intent = new Intent(LogActivity.this, GobanActivity.class);
        intent.putExtra("sgfPath", uri.getPath());
        startActivity(intent);
    }

    public class GetTokenAsyncTask extends RoboAsyncTask<Void> {


        public GetTokenAsyncTask(Context context) {
            super(context);
        }

        @Override
        public Void call() throws Exception {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String grantType = "password";

            ConnectionService connectionService = OAuthServiceGenrator.createService(ConnectionService.class);
            Call<AccessToken> call = connectionService.getAccessToken(clientId, clientSecret, username, password, grantType);
            AccessToken accessToken = call.execute().body();
            authRepo.saveAccessToken(accessToken);

            return null;
        }

        @Override
        protected void onException(Exception e) throws RuntimeException {
            Ln.e(e);
        }

        @Override
        protected void onSuccess(Void result) throws Exception {
            Intent intent = new Intent(LogActivity.this, MenuActivity.class);
            startActivity(intent);
        }
    }


}
