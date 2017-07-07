package orihd.orihd;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;
import android.os.Handler;

import com.facebook.Profile;

import java.io.IOException;


public class FragmentTab1 extends Fragment {
    private Handler handler = new Handler();
    TextView nametext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.activity_fragment_tab1,container,false);
        
        setText(fullname);

    return rootview;
    }

    Profile profile = Profile.getCurrentProfile();
    String firstName = profile.getFirstName();
    String lastName = profile.getLastName();
    String fullname = firstName + " " + lastName;

    public void setText(String text) {
        TextView text2 = (TextView) getActivity().findViewById(R.id.NameText);

        text2.setText("hello");
    }
}