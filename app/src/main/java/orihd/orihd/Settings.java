package orihd.orihd;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import java.net.MalformedURLException;
import java.net.URL;

import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;

import org.json.JSONObject;

import java.io.IOException;




public class Settings extends Fragment{
    public static int notifperm;
    public static int updateperm;
    public static int locationperm;
    public static int seekvalue;
    public Settings() throws IOException {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.activity_settings,container,false);
        final Switch notifications = (Switch) rootview.findViewById(R.id.switch2);
        final Switch updates= (Switch) rootview.findViewById(R.id.switch4);
        final SeekBar seekbar = (SeekBar) rootview.findViewById(R.id.seekBar);
        final Switch location = (Switch) rootview.findViewById(R.id.switch5);

        seekvalue = seekbar.getProgress();

        seekbar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                seekvalue = seekbar.getProgress();
                writeseekvalue();
            }
        });

        notifications.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (notifications.isChecked()) {
                    TurnOnNotifications();
                    Toast.makeText(rootview.getContext(), "Notifications On", Toast.LENGTH_SHORT).show();
                }
                else {
                    TurnOffNotifications();
                    Toast.makeText(rootview.getContext(), "Notiifcations Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        updates.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (updates.isChecked()) {
                    TurnOnUpdates();
                    Toast.makeText(rootview.getContext(), "Live Updates On", Toast.LENGTH_SHORT).show();
                }
                else {
                    TurnOffUpdates();
                    Toast.makeText(rootview.getContext(), "Live Updates Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (location.isChecked()) {
                    TurnOnLocation();
                    Toast.makeText(rootview.getContext(), "Location On", Toast.LENGTH_SHORT).show();
                }
                else {
                    TurnOffLocation();
                    Toast.makeText(rootview.getContext(), "Location Off", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return rootview;

    }

    public static int NotificationStatus(){
        return notifperm;
    }

    public void TurnOnNotifications(){
        notifperm = 1;
    }

    public void TurnOffNotifications(){

        notifperm = 0;
    }

    public static int UpdateStatus(){
        return updateperm;
    }

    public void TurnOnUpdates(){
        updateperm = 1;
    }

    public void TurnOffUpdates(){

        updateperm = 0;
    }

    public static int LocationStatus(){
        return locationperm;
    }

    public void TurnOnLocation(){
        locationperm = 1;
    }

    public void TurnOffLocation(){

        locationperm = 0;
    }

    public void writeseekvalue(){
        //BLUETOOTH WRITE CHARACTERISTIC IMPLEMENTATION




    }


}