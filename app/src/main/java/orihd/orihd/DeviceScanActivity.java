package orihd.orihd;


import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


// Created by Matthew on 7/18/2017.


 public class DeviceScanActivity extends ListActivity {

     private BluetoothAdapter mBluetoothAdapter;
     private boolean mScanning;
     private LeDeviceListAdapter mLeDeviceListAdapter;
     private Handler mHandler;
     // Stops scanning after 10 seconds.
     private static final long SCAN_PERIOD = 10000;

     private BluetoothAdapter.LeScanCallback mLeScanCallback =
             new BluetoothAdapter.LeScanCallback() {
                 @Override
                 public void onLeScan(final BluetoothDevice device, int rssi,
                                      byte[] scanRecord) {
                     runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             mLeDeviceListAdapter.addDevice(device);
                             mLeDeviceListAdapter.notifyDataSetChanged();
                         }
                     });
                 }
             };

     private void scanLeDevice(final boolean enable) {
         if (enable) {
             // Stops scanning after a pre-defined scan period.
             mHandler.postDelayed(new Runnable() {
                 @Override
                 public void run() {
                     mScanning = false;
                     mBluetoothAdapter.stopLeScan(mLeScanCallback);
                     invalidateOptionsMenu();
                 }
             }, SCAN_PERIOD);
             mScanning = true;
             mBluetoothAdapter.startLeScan(mLeScanCallback);
         } else {
             mScanning = false;
             mBluetoothAdapter.stopLeScan(mLeScanCallback);
         }
         invalidateOptionsMenu();
     }

 }