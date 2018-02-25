package orihd.orihd;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
public class FragmentTab6 extends Fragment implements Button.OnClickListener {
    private BluetoothAdapter mBluetoothAdapter;
    Context mcontext;
    private final static String TAG = FragmentTab3.class.getSimpleName();

    public static final int REQUEST_ENABLE_BT = 1;
    public static final int BTLE_SERVICES = 2;
    private HashMap<String, BTLE_Device> mBTDevicesHashMap;
    private ArrayList<BTLE_Device> mBTDevicesArrayList;
    private ListAdapter_BTLE_Devices adapter;
    private ListView listView;
    public static Date previousTime;
    private Button btn_Scan;

    private BroadcastReceiver_BTState mBTStateUpdateReceiver;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_fragment_tab6, container, false);
        TextView txt = (TextView) rootView.findViewById(R.id.textView21);
        Button button5 = (Button) rootView.findViewById(R.id.button5);

        String fbid = FragmentTab1.facebookidfuck;

        button5.setOnClickListener(this);




        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button5:
                EditText name = (EditText) getActivity().findViewById(R.id.contactName);
                EditText email = (EditText) getActivity().findViewById(R.id.email);
                EditText phone = (EditText) getActivity().findViewById(R.id.phone);
                EditText relationship= (EditText) getActivity().findViewById(R.id.relationship);
                String contactname = name.getText().toString();
                String contactemail = email.getText().toString();
                String contactphone = phone.getText().toString();
                String contactrelationship = relationship.getText().toString();

                String userId = FragmentTab1.facebookidfuck;
                NewFriend(userId, contactname, contactemail, contactphone, contactrelationship);
                Toast.makeText(getContext(), "Added!", Toast.LENGTH_LONG).show();

                break;
        }
    }

    private void NewFriend(String userId, String name, String email, String phone, String relationship) {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").child(userId).child("Contacts").child(name).setValue(userId);
        mDatabase.child("Users").child(userId).child("Contacts").child(name).child("Email").setValue(email);
        mDatabase.child("Users").child(userId).child("Contacts").child(name).child("Phone").setValue(phone);
        mDatabase.child("Users").child(userId).child("Contacts").child(name).child("Relationship").setValue(relationship);
    }
}







