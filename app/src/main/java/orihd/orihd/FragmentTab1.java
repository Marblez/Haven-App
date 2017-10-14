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


public class FragmentTab1 extends Fragment {
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
    public static UUID BatteryLevel    = UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb");
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


        // BLUETOOTH IMPLEMENTATION WITH HARDWARE
        //String address = MainActivity.finaladdress;
        //BluetoothManager mBluetoothManager = MainActivity.bmstatic;
        // BluetoothAdapter mBluetoothAdapter = MainActivity.bmadapter;
        //BTLE_Device device = MainActivity.btlestatic;
        // boolean ans = mBluetoothGatt.discoverServices();

        //BluetoothGattService service = new BluetoothGattService();
        // BluetoothGattCharacteristic battery = (BluetoothGattCharacteristic) service.getCharacteristic(UUID
        //        .fromString("00002a19-0000-1000-8000-00805f9b34fb"));;

        /*
        BluetoothGattCharacteristic characteristic = new BluetoothGattCharacteristic(BatteryLevel,1,1);
        byte[] ADCValue3 = characteristic.getValue();
        String adc3Hex = ADCValue3.toString()
                .replace("[", "")   //remove the right bracket
                .replace("]", ""); //remove the left bracket
        String ch3 = (String.valueOf(characteristic.getUuid()));
        String ch3UUID = ch3.substring(0, Math.min(ch3.length(), 8));
        String adc3hex6 = adc3Hex.substring(adc3Hex.length() - 6);
        StringBuilder sb = new StringBuilder();
        for (byte b : ADCValue3) {
            if (sb.length() > 0) {
                sb.append(':');
            }
            sb.append(String.format("%02x", b));
        }
        Log.w("ADC3", "StringBuilder " + sb);
        */
        View rootview = inflater.inflate(R.layout.activity_fragment_tab1, container, false);
        final TextView change = (TextView) rootview.findViewById(R.id.NameText);
        TextView tagtext = (TextView) rootview.findViewById(R.id.textView2);
        ImageView custpic = (ImageView) rootview.findViewById(R.id.imageView3);

        if(pathvar!=null) {

            Bitmap saveicon = loadImageFromStorage(pathvar);
            custpic.setImageBitmap(saveicon);
            Context currcontext2 = getContext();
            String currname = readFromFile(currcontext2);
            latindex = Double.toString(latitudev);
            longindex = Double.toString(longitudev);

            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(getContext(), Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(latitudev, longitudev, 1);
                String var = addresses.get(0).getAddressLine(0);
                String city = parsecity(var);
                citynamuuu=city;
                String settextvalue = settext(currname, city, latitudev, longitudev);
                change.setText(settextvalue);
            } catch (IOException e) {
                e.printStackTrace();
            }




        }
        else {
            if (internet) {
                Profile profile = Profile.getCurrentProfile();
                try{
                String facebook_id = profile.getId();
                String firstName = profile.getFirstName();
                String lastName = profile.getLastName();
                String fullname = firstName + "_"+ lastName;
                firstnamuuu = fullname;
                facebookidfuck = facebook_id;
                    Context currentcontext = getContext();
                    writeToFile(firstName, currentcontext);}
                catch(NullPointerException e){

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

                int aqiupdate = getAQI(latitudev, longitudev);
                String aqitext = Integer.toString(aqiupdate);

                String currname = profile.getFirstName();
                //String city = "Evanston";
                //String settextvalue = settext(currname, city, latitudev, longitudev);
                //change.setText(settextvalue);

                try {
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(getContext(), Locale.getDefault());
                    addresses = geocoder.getFromLocation(latitudev, longitudev, 3);
                    String var = addresses.get(0).getAddressLine(0);
                    String city = parsecity(var);
                    citynamuuu=city;
                    String settextvalue = settext(currname, city, latitudev, longitudev);
                    change.setText(settextvalue);
                } catch (IOException e) {
                    e.printStackTrace();
                }


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
        /*Typeface poiret = Typeface.createFromAsset(getContext().getAssets(),"PoiretOne.tff");
        change.setTypeface(poiret);
        */
        //pic.setImageBitmap(mIcon);


        // SETTING PROGRESS-BAR STATUS AND NUMBERS


/*
        handler.post(new Runnable() {
            public void run() {

                int aqipercentage = aqiupdate/5;
                filteredair.setProgress(aqipercentage);
                // Particles range from 0 ~ 1 billion
                // unfilteredair.setProgress(progressStatus);
                // unfilteredairtext.setText(progressStatus + " PPB");
            }
        });

*/


        int percent = 38;
        int percent2= 94;
        int percent3=49;
        int percent4=77;

        DonutProgress donutProgress = (DonutProgress) rootview.findViewById(R.id.donut_progress);
        donutProgress.setProgressWithAnimation(percent, 30);


        DonutProgress donutProgress2 = (DonutProgress) rootview.findViewById(R.id.donut_progress2);
        donutProgress2.setProgressWithAnimation(percent2, 30);


        DonutProgress donutProgress3 = (DonutProgress) rootview.findViewById(R.id.donut_progress3);
        donutProgress3.setProgressWithAnimation(percent3, 30);


        DonutProgress donutProgress4 = (DonutProgress) rootview.findViewById(R.id.donutProgress4);
        donutProgress4.setProgressWithAnimation(percent4, 30);

        prog1 = donutProgress.getProgress();
        prog2 = donutProgress2.getProgress();
        prog3 = donutProgress3.getProgress();
        prog4 = donutProgress4.getProgress();

        String aventador = FragmentTab1.facebookidfuck;
        Date currentTime = Calendar.getInstance().getTime();
        //WRITING USER DATA TO DATABASE
        //
        long mills = currentTime.getTime();
        long Hours = mills/(1000 * 60 * 60);
        long Mins = mills % (1000*60*60);
        String diff = Hours + ":" + Mins;
        int prog1 = FragmentTab1.prog1;
        int prog2 = FragmentTab1.prog2;
        int prog3 = FragmentTab1.prog3;
        int prog4 = FragmentTab1.prog4;

        writeNewUser(aventador,diff,prog1,prog2,prog3,prog4,citynamuuu,firstnamuuu);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("Users").child(facebookidfuck).child("Friends");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String temp = snapshot.getValue().toString();
                    //String temp2 = Long.toString(temp);
                    stringarray[count]=temp;
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

        }
        else {
            Toast.makeText(getApplicationContext(), "No Atts", Toast.LENGTH_SHORT).show();

        }
    }

    public void disconnect() {
        if (mBluetoothGatt == null) {
            Toast.makeText(getContext(), "Gatt is null", Toast.LENGTH_SHORT).show();

            return;
        }
        mBluetoothGatt.disconnect();
    }

    public void close() {
        if (mBluetoothGatt == null) {
            Toast.makeText(getContext(), "Gatt is null", Toast.LENGTH_SHORT).show();
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothGatt == null) {
            Toast.makeText(getContext(), "Gatt is null", Toast.LENGTH_SHORT).show();
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
    }

    public void writeCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothGatt == null) {
            Toast.makeText(getContext(), "Gatt is null", Toast.LENGTH_SHORT).show();
            return;
        }
        mBluetoothGatt.writeCharacteristic(characteristic);
    }

    /*
    Profile profile = Profile.getCurrentProfile();
    String firstName = profile.getFirstName();
    String lastName = profile.getLastName();
    String fullname = firstName + " " + lastName;
    String facebook_id = profile.getId();
*/
    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        String uuid = null;
        String unknownServiceString = getResources().
                getString(R.string.unknown_service);
        String unknownCharaString = getResources().
                getString(R.string.unknown_characteristic);
        ArrayList<HashMap<String, String>> gattServiceData =
                new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData
                = new ArrayList<ArrayList<HashMap<String, String>>>();
        ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
                new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {
            HashMap<String, String> currentServiceData =
                    new HashMap<String, String>();
            uuid = gattService.getUuid().toString();
            /*
            currentServiceData.put(
                    LIST_NAME, SampleGattAttributes.
                            lookup(uuid, unknownServiceString));
            currentServiceData.put(LIST_UUID, uuid);
            gattServiceData.add(currentServiceData);
*/
            ArrayList<HashMap<String, String>> gattCharacteristicGroupData =
                    new ArrayList<HashMap<String, String>>();
            List<BluetoothGattCharacteristic> gattCharacteristics =
                    gattService.getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas =
                    new ArrayList<BluetoothGattCharacteristic>();
            // Loops through available Characteristics.
            for (BluetoothGattCharacteristic gattCharacteristic :
                    gattCharacteristics) {
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData =
                        new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();
            /*
                currentCharaData.put(
                        LIST_NAME, SampleGattAttributes.lookup(uuid,
                                unknownCharaString));
                currentCharaData.put(LIST_UUID, uuid);
                gattCharacteristicGroupData.add(currentCharaData);
                */
            }
            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);
        }
    }

    public String parsecity(String state){
        try {
            List<String> StringList = Arrays.asList(state.split(","));
            String temp2 = StringList.get(1);
            return temp2;
        }
        catch(NullPointerException e){
            String temp2 = "Unavailable";
            return temp2;
        }

    }
    public int getAQI(final double latitudev, final double longitudev){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("Location");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = 0;

                double tempdistance = 99999;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                        double doubleval = snapshot2.getValue(double.class);
                        arrayvalue[count] = doubleval;
                        if (count != 0) {
                            if (count % 3 == 0) {
                                double testlong = arrayvalue[count - 1];
                                double testlat = arrayvalue[count - 2];
                                double aqivalue = arrayvalue[count - 3];
                                double indexlong = Math.abs(longitudev - testlong);
                                double indexlat = Math.abs(latitudev - testlat);
                                double distlong = indexlong * indexlong;
                                double distlat = indexlat * indexlat;
                                double truedist = distlat + distlong;
                                double distance = Math.sqrt(truedist);
                                if (tempdistance > distance) {
                                    tempdistance = distance;
                                    AQI = (int) aqivalue;

                                }

                            }
                        }
                        count++;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

           return AQI;

    }

    public String settext(String firstName,String city, double latitude, double longitude){

        int settextaqi = getAQI(latitude,longitude);
        if(settextaqi==0){
            String settextvalue = firstName +" | "+city+" | "+"AQI "+ "--";
            return settextvalue;
        }
        else{
            String settextvalue = firstName +" | "+city+" | "+"AQI "+ settextaqi;
            return settextvalue;
        }
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
            gatt.readCharacteristic(chars.get(chars.size()-1));
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == 0) {
            // REMEMBER TO CONFIGURE THE UUID VALUES FOR THESE CHARACTERISTICS
                if (characteristic.getUuid().toString().substring(7, 8).equals("5")) {
                    int pm25 = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 0);

                } else if (characteristic.getUuid().toString().substring(7,8).equals("7")) {
                    int gasvalue = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 0);
                }
                  else if (characteristic.getUuid().toString().substring(7,8).equals("6")){
                    int batterylevel = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8,0);
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

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

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

    private Bitmap loadImageFromStorage(String path)
    {

        try {
            File f=new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }

    }

    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
    private void writeNewUser(String userId, String timestamp, int p1, int p2, int p3, int p4, String city, String name) {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").child(userId).child("Name").setValue(name);
        mDatabase.child("Users").child(userId).child("TimeStamp").setValue(timestamp);
        mDatabase.child("Users").child(userId).child("Location").setValue(city);
        mDatabase.child("Users").child(userId).child("Prog1").setValue(p1);
        mDatabase.child("Users").child(userId).child("Prog2").setValue(p2);
        mDatabase.child("Users").child(userId).child("Prog3").setValue(p3);
        mDatabase.child("Users").child(userId).child("Prog4").setValue(p4);


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
