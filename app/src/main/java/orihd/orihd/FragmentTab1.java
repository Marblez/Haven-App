package orihd.orihd;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;

import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import com.bikomobile.donutprogress.DonutProgress;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static com.facebook.FacebookSdk.getApplicationContext;


public class FragmentTab1 extends Fragment implements View.OnClickListener {
    private Handler handler = new Handler();
    public static int tagval;
    public static String citynamuuu;
    public static String firstnamuuu;
    public static String pathvar;
    public static int prog1, prog2, prog3, prog4;
    public static int picval;
    public static String facebookidfuck;
    public static String globaltext;
    public static String latindex;
    public static String longindex;
    public static String[] stringarray = new String[100];
    public static ImageView globalimage;
    public static int AQI;
    private BluetoothLeService mBluetoothLeService;
    private BluetoothGatt mBluetoothGatt;
    static double arrayvalue[] = new double[100000];
    ProfilePictureView prof;
    public BluetoothGatt mbluetoothgatt;
    TextView nametext;
    String str;
    public static UUID BatteryLevel = UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb");
    private BluetoothGatt m_gatt;

    BluetoothGattCharacteristic mWriteCharacteristic;

    public FragmentTab1() throws IOException {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        TrackGPS NewGPS = new TrackGPS(getContext());
        double longitudev = NewGPS.getLongitude();
        double latitudev = NewGPS.getLatitude();
        boolean internet = isNetworkAvailable();
        if (internet) {
            Intent i = new Intent(getContext(), MyService.class);
            getContext().startService(i);
        }

        View rootview = inflater.inflate(R.layout.activity_fragment_tab1, container, false);
        final TextView change = (TextView) rootview.findViewById(R.id.NameText);
        TextView tagtext = (TextView) rootview.findViewById(R.id.textView2);
        EditText status = (EditText) rootview.findViewById(R.id.editText3);
        ImageView custpic = (ImageView) rootview.findViewById(R.id.imageView3);
        Button submit = (Button) rootview.findViewById(R.id.button6);
        submit.setOnClickListener(this);

        if (pathvar != null) {

            Bitmap saveicon = loadImageFromStorage(pathvar);
            custpic.setImageBitmap(saveicon);
            Context currcontext2 = getContext();
            String currname = readFromFile(currcontext2);
            latindex = Double.toString(latitudev);
            longindex = Double.toString(longitudev);


        } else {
            if (internet) {
                Profile profile = Profile.getCurrentProfile();
                try {
                    String facebook_id = profile.getId();
                    String firstName = profile.getFirstName();
                    String lastName = profile.getLastName();
                    String fullname = firstName + "_" + lastName;
                    firstnamuuu = fullname;
                    facebookidfuck = facebook_id;
                    Context currentcontext = getContext();
                    writeToFile(firstName, currentcontext);
                } catch (NullPointerException e) {

                    String firstName = "Edgar";
                    String lastName = "Palacios";
                    firstnamuuu = "Edgar Palacios";
                    String facebook_id = "10211808881471961";
                    facebookidfuck = "10211808881471961";
                    Context currentcontext = getContext();
                    writeToFile(firstName, currentcontext);
                }
                // ONCREATE VIEW AND SETUP


                //SAVES PROFILE ID AS STRING

                latindex = Double.toString(latitudev);
                longindex = Double.toString(longitudev);


                String currname = profile.getFirstName();


                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    URL img_value = null;
                    try {
                        img_value = new URL("https://graph.facebook.com/" + facebookidfuck + "/picture?type=large");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    Bitmap mIcon = null;
                    try {
                        mIcon = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    pathvar = saveToInternalStorage(mIcon);
                    custpic.setImageBitmap(mIcon);

                }
            }
        }


        String aventador = FragmentTab1.facebookidfuck;
        Date currentTime = Calendar.getInstance().getTime();
        //WRITING USER DATA TO DATABASE
        //
        long mills = currentTime.getTime();
        long Hours = mills / (1000 * 60 * 60);
        long Mins = mills % (1000 * 60 * 60);
        String diff = Hours + ":" + Mins;
        int prog1 = FragmentTab1.prog1;
        int prog2 = FragmentTab1.prog2;
        int prog3 = FragmentTab1.prog3;
        int prog4 = FragmentTab1.prog4;

        //writeNewUser(aventador, diff, prog1, prog2, prog3, prog4, citynamuuu, firstnamuuu);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("Users").child(facebookidfuck).child("Friends");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String temp = snapshot.getValue().toString();
                    //String temp2 = Long.toString(temp);
                    stringarray[count] = temp;
                    count++;
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        return rootview;
    }

    public void onServicesDiscovered(BluetoothGatt gatt, int status) {

        if (status == BluetoothGatt.GATT_SUCCESS) {

        } else {
            Toast.makeText(getApplicationContext(), "No Atts", Toast.LENGTH_SHORT).show();

        }
    }


    public String settext(String firstName, String lastname) {
        String settextvalue = firstName + lastname;
        return settextvalue;
    }

    public static final BluetoothGattCallback readGattCallback = new BluetoothGattCallback() {

        List<BluetoothGattCharacteristic> chars = new ArrayList<>();

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {

            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();
                gatt.discoverServices();
            }
            if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Toast.makeText(getApplicationContext(), "Disconnected", Toast.LENGTH_SHORT).show();

            }
        }


        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {

            List<BluetoothGattService> services = gatt.getServices();
            BluetoothGattService rightService = null;

            for (int i = 0; i < services.size(); i++) {
                if (services.get(i).getCharacteristics().size() > 8) {
                    rightService = services.get(i);
                }
            }

            chars.add(rightService.getCharacteristics().get(4));
            chars.add(rightService.getCharacteristics().get(6));

            requestCharacteristics(gatt);

        }

        public void requestCharacteristics(BluetoothGatt gatt) {
            gatt.readCharacteristic(chars.get(chars.size() - 1));
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == 0) {
                // REMEMBER TO CONFIGURE THE UUID VALUES FOR THESE CHARACTERISTICS
                if (characteristic.getUuid().toString().substring(7, 8).equals("5")) {
                    int pm25 = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 0);

                } else if (characteristic.getUuid().toString().substring(7, 8).equals("7")) {
                    int gasvalue = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 0);
                } else if (characteristic.getUuid().toString().substring(7, 8).equals("6")) {
                    int batterylevel = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 0);
                }

                chars.remove(chars.get(chars.size() - 1));

                if (chars.size() > 0) {
                    requestCharacteristics(gatt);
                } else {
                    gatt.disconnect();
                }
            }
        }

    };

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    private Bitmap loadImageFromStorage(String path) {

        try {
            File f = new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }


    public void writeEntry(String timestamp, String journal) {
        String userId = FragmentTab1.facebookidfuck;
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        boolean check = checkWords(journal);
        if(check){
            mDatabase.child("Users").child(userId).child("Special Journals").child(timestamp).setValue(journal);
        }
        mDatabase.child("Users").child(userId).child("Journals").child(timestamp).setValue(journal);
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

        }
    }

    public static boolean checkWords(String journal){
        String[] arr = new String[]{"murder","kill","shoot", "anxiety", "withdrawal", "severe", "delusions", "adhd", "weight", "insomnia", "drowsiness", "suicidal", "appetite", "dizziness", "nausea", "episodes", "attacks", "sleepless", "seizures", "addictive", "weaned", "swings", "dysfunction","blurred", "irritability", "headache", "fatigue", "imbalance", "nervousness", "psychosis", "drowsy"};
        for(int i =0; i<arr.length; i++){
            if(journal.contains(arr[i])){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button6:
                Date currentTime = Calendar.getInstance().getTime();

                long mills = currentTime.getTime();
                long Hours = mills / (1000 * 60 * 60);
                long Mins = mills % (1000 * 60 * 60);
                String diff = Long.toString(Hours+Mins);
                EditText editText = (EditText) getActivity().findViewById(R.id.editText3);
                String content = editText.getText().toString();
                writeEntry(diff,content);
                editText.setText("");

                Toast.makeText(getApplicationContext(), "Submitted!", Toast.LENGTH_LONG).show();
                FragmentTab3 fragment = null;
                fragment = new FragmentTab3();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                break;
        }
    }
}

