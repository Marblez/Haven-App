package orihd.orihd;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Profile profile = Profile.getCurrentProfile();
        try {
            String firstName = profile.getFirstName();
            String lastName = profile.getLastName();
            String fullname = firstName + " " + lastName;
            String facebook_id = profile.getId();

            NavigationView navigationView2 = (NavigationView) findViewById(R.id.nav_view);
            View hView =  navigationView2.getHeaderView(0);
            ImageView pic = (ImageView)hView.findViewById(R.id.profile_image);
            TextView name = (TextView)hView.findViewById(R.id.username6969);
            name.setText(fullname);
        }
        catch(NullPointerException e){
            String firstName = "Edgar";
            String lastName = "Palacios";
            String fullname = firstName + " " + lastName;
            String facebook_id = profile.getId();

            NavigationView navigationView2 = (NavigationView) findViewById(R.id.nav_view);
            View hView =  navigationView2.getHeaderView(0);
            ImageView pic = (ImageView)hView.findViewById(R.id.profile_image);
            TextView name = (TextView)hView.findViewById(R.id.username6969);
            name.setText(fullname);

        }

        int testaqi;



        String strMeatFormat = getResources().getString(R.string.Name);

        Context context = getApplicationContext();


        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL img_value = null;
            try {
                String facebook_id = profile.getId();
                img_value = new URL("https://graph.facebook.com/" + facebook_id + "/picture?type=large");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap mIcon = null;
            try {
                mIcon = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            NavigationView navigationView2 = (NavigationView) findViewById(R.id.nav_view);
            View hView =  navigationView2.getHeaderView(0);
            ImageView pic = (ImageView)hView.findViewById(R.id.profile_image);
            pic.setImageBitmap(mIcon);

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTab1 fragment = null;
        try {
            fragment = new FragmentTab1();
        } catch (IOException e) {
            e.printStackTrace();
        }
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent fuckschool = new Intent(DrawerActivity.this, Login.class);
            DrawerActivity.this.startActivity(fuckschool);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            FragmentTab1 fragment = null;
            try {
                fragment = new FragmentTab1();
            } catch (IOException e) {
                e.printStackTrace();
            }
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.maps) {
            FragmentTab2 fragment = null;
            fragment = new FragmentTab2();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.info) {
            FragmentTab3 fragment = null;
            fragment = new FragmentTab3();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.settings) {
            Settings fragment = null;
            try {
                fragment = new Settings();
            } catch (IOException e) {
                e.printStackTrace();
            }
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.addfam) {
            FragmentTab6 fragment = null;
            fragment = new FragmentTab6();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.viewfam) {
            FragmentTab5 fragment = null;
            fragment = new FragmentTab5();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    public void onSuccess(LoginResult loginResult) {
        AccessToken accessToken = loginResult.getAccessToken();

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken1) {

            }
        };
        accessTokenTracker.startTracking();

        ProfileTracker profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile profile, Profile profile1) {

            }
        };
        profileTracker.startTracking();

        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            //get data here
        }
    }
}
