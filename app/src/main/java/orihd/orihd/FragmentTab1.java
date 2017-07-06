package orihd.orihd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;
import android.os.Handler;



public class FragmentTab1 extends Fragment {
    private Handler handler = new Handler();
    TextView nametext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.activity_fragment_tab1,container,false);

        Login login = new Login();
        String Namer = login.getName();
        FragmentTab1.setText(Namer);

    return rootview;
    }


    public static void setText(String text) {
        TextView t = (TextView) getView().findViewById(R.id.NameText);
        t.setText(text);
    }
}