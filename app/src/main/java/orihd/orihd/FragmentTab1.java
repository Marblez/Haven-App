package orihd.orihd;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;

import org.json.JSONObject;

import java.io.IOException;


public class FragmentTab1 extends Fragment {
    private Handler handler = new Handler();
    TextView nametext;

    public FragmentTab1() throws IOException {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.activity_fragment_tab1,container,false);

        TextView change = (TextView) rootview.findViewById(R.id.NameText);
        final ImageView pic = (ImageView) rootview.findViewById(R.id.imageView3);
        change.setText(fullname);
        //pic.setImageBitmap(mIcon);


    return rootview;
    }

    Profile profile = Profile.getCurrentProfile();
    String firstName = profile.getFirstName();
    String lastName = profile.getLastName();
    String fullname = firstName + " " + lastName;
    String facebook_id = profile.getId();

    URL img_value = new URL("https://graph.facebook.com/" + facebook_id + "/picture?type=large");
    //Bitmap mIcon = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());



}