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




public class Settings extends Fragment implements View.OnClickListener {
    public static int notifperm = 1;


    public Settings() throws IOException {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.activity_settings,container,false);
        final Switch notifications = (Switch) rootview.findViewById(R.id.switch2);

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

        ImageButton orihd = (ImageButton) rootview.findViewById(R.id.imageButton2);
        orihd.setOnClickListener(this);

        return rootview;

    }

    public int NotificationStatus(){
        return notifperm;
    }

    public void TurnOnNotifications(){
        notifperm = 1;
    }

    public void TurnOffNotifications(){
        notifperm = 0;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.imageButton2:
                Uri uri = Uri.parse("https:www.orihd.com");
                Intent intent= new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);

                break;
        }
    }
}