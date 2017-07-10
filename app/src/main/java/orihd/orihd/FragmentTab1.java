package orihd.orihd;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import java.net.MalformedURLException;
import java.net.URL;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONObject;

import java.io.IOException;

import static com.facebook.FacebookSdk.getApplicationContext;


public class FragmentTab1 extends Fragment {
    private Handler handler = new Handler();
    public static int tagval;
    public static int picval;
    public static String globaltext;
    public static ImageView globalimage;
    ProfilePictureView prof;
    TextView nametext;

    public FragmentTab1() throws IOException {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.activity_fragment_tab1,container,false);

        TextView change = (TextView) rootview.findViewById(R.id.NameText);
        TextView tagtext = (TextView) rootview.findViewById(R.id.textView2);
        ImageView custpic = (ImageView) rootview.findViewById(R.id.imageView3);
        change.setText(fullname);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL img_value = null;
            try {
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
            custpic.setImageBitmap(mIcon);

        }
        //pic.setImageBitmap(mIcon);

        if(globaltext!= null) {
            tagtext.setText(globaltext);
        }
        else{
            tagtext.setText("Working Class");
        }


    return rootview;
    }

    Profile profile = Profile.getCurrentProfile();
    String firstName = profile.getFirstName();
    String lastName = profile.getLastName();
    String fullname = firstName + " " + lastName;
    String facebook_id = profile.getId();






}