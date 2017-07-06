package orihd.orihd;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Login extends AppCompatActivity implements View.OnClickListener {
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    Button login;
    Button friends;
    Button mypet;
    Button guidance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.content_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        callbackManager = CallbackManager.Factory.create();
        setSupportActionBar(toolbar);
        // Layout setup

        AccessToken token;
        token = AccessToken.getCurrentAccessToken();

        if (token != null) {
            //Means user is logged in
            Intent registerIntenthome = new Intent(Login.this, Dashboard.class);
            Login.this.startActivity(registerIntenthome);
            finish();
        }

        setContentView(R.layout.content_login);
        info = (TextView)findViewById(R.id.info);
        loginButton = (LoginButton)findViewById(R.id.login_button);
        login = (Button)findViewById(R.id.button);
        friends = (Button)findViewById(R.id.button4);
        mypet = (Button)findViewById(R.id.button3);
        guidance = (Button)findViewById(R.id.button5);
        login.setOnClickListener(this);
        mypet.setOnClickListener(this);
        guidance.setOnClickListener(this);






        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent registerIntent2 = new Intent(Login.this, Home.class);
                Login.this.startActivity(registerIntent2);
            }

            @Override
            public void onCancel() {
                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed.");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button4:
                Intent registerIntent123 = new Intent(Login.this, friends.class);
                Login.this.startActivity(registerIntent123);

                break;

            case R.id.button3:
                Intent registerIntent1234 = new Intent(Login.this, mypet.class);
                Login.this.startActivity(registerIntent1234);

                break;

            case R.id.button5:
                Intent registerIntent12345 = new Intent(Login.this, Guidance.class);
                Login.this.startActivity(registerIntent12345);

                break;

            case R.id.button:
                AccessToken token;
                token = AccessToken.getCurrentAccessToken();

                if (token != null) {
                    //Means user is logged in
                    Intent registerIntent12 = new Intent(Login.this, Home.class);
                    Login.this.startActivity(registerIntent12);
                }

                else {
                    Toast.makeText(getApplicationContext(), "You must login first", Toast.LENGTH_LONG).show();
                    break;
                }
        }
    }
}
