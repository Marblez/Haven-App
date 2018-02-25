package orihd.orihd;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FragmentTab3 extends Fragment implements View.OnClickListener{



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.activity_fragment_tab3, container, false);
        SeekBar seekBar = (SeekBar) rootView.findViewById(R.id.happystatus);
        SeekBar seekBar2 = (SeekBar) rootView.findViewById(R.id.seekBar2);
        SeekBar seekBar3 = (SeekBar) rootView.findViewById(R.id.seekBar3);
        seekBar3.setProgress(0);
        seekBar3.setMax(200);
        seekBar3.incrementProgressBy(10);
        seekBar2.setProgress(0);
        seekBar2.setMax(200);
        seekBar2.incrementProgressBy(10);
        seekBar.setProgress(0);
        seekBar.incrementProgressBy(10);
        seekBar.setMax(200);

        Button submitButton = (Button) rootView.findViewById(R.id.feelingSubmit);
        submitButton.setOnClickListener(this);
        final TextView seekBarValue = (TextView) rootView.findViewById(R.id.happyprog);
        final TextView seekBarValue2 = (TextView) rootView.findViewById(R.id.loveprog);
        final TextView seekBarValue3 = (TextView) rootView.findViewById(R.id.connectprog);
        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int progress = seekBar.getProgress();
                progress = progress / 10;
                progress = progress * 10;
                progress = progress/2;
                seekBarValue3.setText(String.valueOf(progress)+ "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int progress = seekBar.getProgress();
                progress = progress / 10;
                progress = progress * 10;
                progress = progress/2;
                seekBarValue2.setText(String.valueOf(progress)+ "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int progress = seekBar.getProgress();
                progress = progress / 10;
                progress = progress * 10;
                progress = progress/2;
                seekBarValue.setText(String.valueOf(progress)+ "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

            return rootView;
    }


    @Override
    public void onClick(View view) {

        TextView happy = (TextView) getActivity().findViewById(R.id.happyprog);
        TextView love = (TextView) getActivity().findViewById(R.id.loveprog);
        TextView connect = (TextView) getActivity().findViewById(R.id.connectprog);
        String a = (String) happy.getText();
        String b = (String) love.getText();
        String c = (String) connect.getText();

        a = a.substring(0,a.length()-1);
        b = b.substring(0,b.length()-1);
        c = c.substring(0,c.length()-1);


        uploadUser(Integer.parseInt(a),Integer.parseInt(b),Integer.parseInt(c));

        SeekBar e1 = (SeekBar) getActivity().findViewById(R.id.happystatus);
        SeekBar e2 = (SeekBar) getActivity().findViewById(R.id.seekBar3);
        SeekBar e3 = (SeekBar) getActivity().findViewById(R.id.seekBar2);
        e1.setProgress(0);
        e2.setProgress(0);
        e3.setProgress(0);


    }
    public static void uploadUser(int data1, int data2, int data3){
        Date currentTime = Calendar.getInstance().getTime();

        long mills = currentTime.getTime();
        long Hours = mills / (1000 * 60 * 60);
        Toast.makeText(getApplicationContext(), Long.toString(Hours), Toast.LENGTH_LONG).show();
        long Mins = mills % (1000 * 60 * 60);
        String diff = Long.toString(Hours+Mins);
        String userId = FragmentTab1.facebookidfuck;
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").child(userId).child("Emotion").child(diff).child("Happy").setValue(data1);
        mDatabase.child("Users").child(userId).child("Emotion").child(diff).child("Self-Love").setValue(data2);
        mDatabase.child("Users").child(userId).child("Emotion").child(diff).child("Connection").setValue(data3);

    }
}