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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
    public BluetoothGatt mbluetoothgatt;
    TextView nametext;
    String str;
    public static UUID BatteryLevel    = UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb");
    private BluetoothGatt m_gatt;

    BluetoothGattCharacteristic mWriteCharacteristic;
    public FragmentTab1() throws IOException {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intent i= new Intent(getContext(), MyService.class);
        getContext().startService(i);
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


    Profile profile = Profile.getCurrentProfile();
    String firstName = profile.getFirstName();
    String lastName = profile.getLastName();
    String fullname = firstName + " " + lastName;
    String facebook_id = profile.getId();

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



}