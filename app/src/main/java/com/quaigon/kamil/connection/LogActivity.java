package com.quaigon.kamil.connection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.quaigon.kamil.goban.R;

import retrofit2.Call;
import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;
import roboguice.util.Ln;
import roboguice.util.RoboAsyncTask;

public class LogActivity extends RoboActionBarActivity {

    @InjectView(R.id.signInButton)
    private Button signInButton;

    @InjectView(R.id.usernameEditText)
    private EditText usernameEditText;


    @InjectView(R.id.passwordEditText)
    private EditText passwordEditText;


    private final static String clientId = "c864df57fa2c675f313d";
    private final static String clientSecret = "fe53725ce1156a82a57b73cde649561d60efc0c6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);


        this.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String username = usernameEditText.getText().toString();
//                String password = passwordEditText.getText().toString();

                String username = "quaigon";
                String password = "b4d8d75db2ce6c5f0855806d67c5d4d5";

                GetTokenAsyncTask getTokenAsyncTask = new GetTokenAsyncTask(LogActivity.this);
                getTokenAsyncTask.execute();


//                Intent intent = new Intent(LogActivity.this, MenuActivity.class);
//                startActivity(intent);
            }
        });
    }


    public class GetTokenAsyncTask extends RoboAsyncTask<AccessToken> {


        public GetTokenAsyncTask(Context context) {
            super(context);
        }

        @Override
        public AccessToken call() throws Exception {
                String username = "quaigon";
                String password = "b4d8d75db2ce6c5f0855806d67c5d4d5";
                String grantType = "password";

                LoginService loginService = OAuthServiceGenrator.createService(LoginService.class);
                Call<AccessToken> call = loginService.getAccessToken(clientId, clientSecret, username, password, grantType);
                AccessToken accessToken = call.execute().body();


            return accessToken;
        }

        @Override
        protected void onException(Exception e) throws RuntimeException {
            Ln.e(e);
        }

        @Override
        protected void onSuccess(AccessToken accessToken) throws Exception {
            Intent intent = new Intent(LogActivity.this, MenuActivity.class);
            intent.putExtra("accesstoken", accessToken.getAccess_token());
            intent.putExtra("refreshtoken", accessToken.getRefresh_token());
            intent.putExtra("scope", accessToken.getScope());
            intent.putExtra("expiresin", accessToken.getExpires_in());
            startActivity(intent);
        }
    }






}
