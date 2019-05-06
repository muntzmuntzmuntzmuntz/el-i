package com.mememe;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.mememe.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.Arrays;
import java.util.logging.Logger;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends Activity {
//    Bundle user = new Bundle();

    User user;
    private LoginButton loginButton;
    private TextView nameText, emailText;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.login_button);
//        nameText = findViewById(R.id.profile_name);
//        emailText = findViewById(R.id.profile_email);

        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email");

        checkLoginStatus();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
//                loadUserProfile(AccessToken.getCurrentAccessToken());
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this, "Login UNSUCCESSFUL", Toast.LENGTH_LONG).show();
                Log.d(">> FACEBOOK CONNECTION","LOGIN UNSUCCESSFULL "+error);
            }
        });
    }
//    }
//
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }
//
    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken==null){
                emailText.setText("");
                nameText.setText("");
                Toast.makeText(MainActivity.this,"Please Login",Toast.LENGTH_LONG).show();
            }else{
                loadUserProfile(currentAccessToken);
            }
        }
    };

    private void loadUserProfile(AccessToken newAccessToken){
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d(">> PARSING",""+object.toString());
                try {
                    user = new User(object.getString("id"),object.getString("name"),"https://graph.facebook.com/"+object.getString("id")+"/picture?type=normal");
                    transition();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        request.executeAsync();
    }

    public void checkLoginStatus(){
        if (AccessToken.getCurrentAccessToken()!=null){
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }else{
            Toast.makeText(MainActivity.this,"Please Login",Toast.LENGTH_LONG).show();
        }
    }

    public void transition(){
        Intent intent = new Intent(MainActivity.this,MemesengerActivity.class);
        intent.putExtra("user",user);
        MainActivity.this.startActivity(intent);
    }
}
