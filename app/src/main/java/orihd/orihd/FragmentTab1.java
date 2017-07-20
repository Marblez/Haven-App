package orihd.orihd;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.widget.ProgressBar;
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
    private BluetoothLeService mBluetoothLeService;
    private BluetoothGatt mBluetoothGatt;
    ProfilePictureView prof;
    TextView nametext;
    String str;

    BluetoothGattCharacteristic mWriteCharacteristic;
    public FragmentTab1() throws IOException {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // BLUETOOTH IMPLEMENTATION WITH HARDWARE
        String address = MainActivity.finaladdress;
        BluetoothManager mBluetoothManager = MainActivity.bmstatic;
        BluetoothAdapter mBluetoothAdapter = MainActivity.bmadapter;
        BTLE_Device device = MainActivity.btlestatic;
       












        // ONCREATE VIEW AND SETUP
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

        // SETTING PROGRESS-BAR STATUS AND NUMBERS

        final ProgressBar unfilteredair = (ProgressBar) rootview.findViewById(R.id.progressBar);
        final ProgressBar filteredair = (ProgressBar) rootview.findViewById(R.id.progressBar2);
        final ProgressBar so2 = (ProgressBar) rootview.findViewById(R.id.progressBarFilter);
        final ProgressBar no2 = (ProgressBar) rootview.findViewById(R.id.progressBar3);
        final ProgressBar filterstate = (ProgressBar) rootview.findViewById(R.id.progressBarBattery);
        final ProgressBar battery = (ProgressBar) rootview.findViewById(R.id.progressBar4);

        final TextView unfilteredairtext = (TextView) rootview.findViewById(R.id.textView5);
        final TextView filteredtext = (TextView) rootview.findViewById(R.id.textView3);
        final TextView so2text = (TextView) rootview.findViewById(R.id.txtview);
        final TextView no2text = (TextView) rootview.findViewById(R.id.textView12);
        final TextView filterstatetext = (TextView) rootview.findViewById(R.id.textView13);
        final TextView batterytext = (TextView) rootview.findViewById(R.id.txtview5);
        handler.post(new Runnable() {
            public void run() {
                // Particles range from 0 ~ 1 billion
                // unfilteredair.setProgress(progressStatus);
                // unfilteredairtext.setText(progressStatus + " PPB");
            }
        });













    return rootview;
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


    Profile profile = Profile.getCurrentProfile();
    String firstName = profile.getFirstName();
    String lastName = profile.getLastName();
    String fullname = firstName + " " + lastName;
    String facebook_id = profile.getId();






}