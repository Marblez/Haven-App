package orihd.orihd;

import android.app.ActionBar;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;

import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.internal.ImageRequest;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class Login extends AppCompatActivity implements View.OnClickListener {
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    String Email;
    String Name;
    Button login;
    Button friends;
    public static int passvalue;
    Button mypet;
    static double arrayvalue[] = new double[100000];
    public static int AQILOGIN;
    Button guidance;
    private IWXAPI api;
    private float x1, x2;
    static final int MIN_DISTANCE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean internet = isNetworkAvailable();
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.content_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        callbackManager = CallbackManager.Factory.create();
        setSupportActionBar(toolbar);
        TrackGPS NewGPS = new TrackGPS(getApplicationContext());
        double longitudev = NewGPS.getLongitude();
        double latitudev = NewGPS.getLatitude();

        AccessToken token;
        token = AccessToken.getCurrentAccessToken();

        Profile profile = Profile.getCurrentProfile().getCurrentProfile();


        setContentView(R.layout.content_login);

        loginButton = (LoginButton) findViewById(R.id.login_button);


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent registerIntent2 = new Intent(Login.this, DrawerActivity.class);
                Login.this.startActivity(registerIntent2);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (deltaX > MIN_DISTANCE) {
                    AccessToken token;
                    token = AccessToken.getCurrentAccessToken();

                    if (token != null) {
                        //Means user is logged in

                                Intent registerIntent1234 = new Intent(Login.this, DrawerActivity.class);
                                Login.this.startActivity(registerIntent1234);
                                finish();
                            }


                     else {
                        Toast.makeText(getApplicationContext(), "Please Login First", Toast.LENGTH_SHORT).show();
                        break;
                    }


                } else {
                    //NOTHING
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /*
        public void sendnotification(){
            // Building Notification
            NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
            notification.setAutoCancel(true);
            notification.setSmallIcon(R.drawable.alert);
            notification.setTicker("This is the Ticker");
            notification.setWhen(System.currentTimeMillis());
            notification.setContentTitle("AQI Alert!");
            notification.setContentText("Air Quality within 30km is poor");
            Intent intent = new Intent(this,MyService.class);
            PendingIntent pendingintent = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setContentIntent(pendingintent);

            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            nm.notify(45612, notification.build());
        }
    */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.button:
                AccessToken token2;
                token2 = AccessToken.getCurrentAccessToken();

                if (token2 != null) {
                    //Means user is logged in
                    Intent registerIntent12 = new Intent(Login.this, Homemenu.class);
                    Login.this.startActivity(registerIntent12);
                } else {
                    break;
                }
        }
    }

    private void setFacebookData(final LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                            Log.i("Response", response.toString());

                            String email = response.getJSONObject().getString("email");
                            String firstName = response.getJSONObject().getString("first_name");
                            String lastName = response.getJSONObject().getString("last_name");
                            String gender = response.getJSONObject().getString("gender");

                            Email = email;
                            Name = firstName + " " + lastName;


                            Profile profile = Profile.getCurrentProfile();
                            String id = profile.getId();
                            String link = profile.getLinkUri().toString();
                            Log.i("Link", link);
                            if (Profile.getCurrentProfile() != null) {
                                Log.i("Login", "ProfilePic" + Profile.getCurrentProfile().getProfilePictureUri(200, 200));
                            }

                            Log.i("Login" + "Email", email);
                            Log.i("Login" + "FirstName", firstName);
                            Log.i("Login" + "LastName", lastName);
                            Log.i("Login" + "Gender", gender);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    public String getName() {
        return Name;
    }

    @Override
    public void onBackPressed() {
        // Return home button
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
        super.onBackPressed();
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
