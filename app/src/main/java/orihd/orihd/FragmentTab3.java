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

public class FragmentTab3 extends Fragment {
    private BluetoothAdapter mBluetoothAdapter;
    Context mcontext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mcontext.getApplicationContext();
        View rootView = inflater.inflate(R.layout.activity_fragment_tab3, container, false);
        // Initializes Bluetooth adapter.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) mcontext.getSystemService(Context.BLUETOOTH_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mBluetoothAdapter = bluetoothManager.getAdapter();
        }
        // Ensures Bluetooth is available on the device and it is enabled. If not,
        // displays a dialog requesting user permission to enable Bluetooth.
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            int REQUEST_ENABLE_BT=1;
            //The REQUEST_ENABLE_BT constant passed to
            // startActivityForResult(android.content.Intent, int)
            // is a locally-defined integer (which must be greater than 0)
            // that the system passes back to you in your
            // onActivityResult(int, int, android.content.Intent)
            // implementation as the requestCode parameter.
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        return rootView;
    }


}