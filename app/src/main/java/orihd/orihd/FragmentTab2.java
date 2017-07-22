package orihd.orihd;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import com.google.android.gms.maps.GoogleMap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.Manifest;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONException;

import java.io.InputStream;
import java.util.List;

import orihd.orihd.Manifest.permission;

public class FragmentTab2 extends Fragment implements OnMapReadyCallback{
    public double longitudev;
    public double latitudev;
    public List<MyItem> items;

    private ClusterManager<MyItem> mClusterManager;
    public static double testing;
    private final static String TAG_FRAGMENT = "TAG_FRAGMENT";
    static double arrayvalue[] = new double[100000];
    public static FragmentTab2 newInstance() {
        FragmentTab2 fragment = new FragmentTab2();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_fragment_tab2, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("Location");

        databaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot snapshot2 : snapshot.getChildren()) {

                        arrayvalue = new double[100];
                        Double doubleval = snapshot2.getValue(Double.class);
                        arrayvalue[count] = doubleval;
                        count++;

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        */

        return rootView;
    }


/*
    private void setUpClusterer() {
        mClusterManager.setAnimation(false);
        // Position the map.
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MyItem>(this, getMap());

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        getMap().setOnCameraIdleListener(mClusterManager);
        getMap().setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.
        addItems();
    }

    private void addItems() {

        // Set some lat/lng coordinates to start with.
        double lat = 51.5145160;
        double lng = -0.1270060;

        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 10; i++) {
            double offset = i / 60d;
            lat = lat + offset;
            lng = lng + offset;
            MyItem offsetItem = new MyItem(lat, lng);
            mClusterManager.addItem(offsetItem);
        }
    }
    */

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        TrackGPS NewGPS = new TrackGPS(getContext());
        if(Settings.LocationStatus()== 1 ) {
            longitudev = NewGPS.getLongitude();
            latitudev = NewGPS.getLatitude();
            LatLng current = new LatLng(latitudev, longitudev);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15));
            googleMap.addMarker(new MarkerOptions().position(current).icon(getMarkerIcon("#00f921"))
                    .title("Current Location"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 10));
        }
        else{
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 15));
            Toast.makeText(getContext(), "Turn On Location Tracking", Toast.LENGTH_SHORT).show();

        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("Location");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                        double doubleval = snapshot2.getValue(double.class);
                        arrayvalue[count] = doubleval;
                        count++;
                    }
                }

                //String testval = Integer.toString(count);
                //Toast.makeText(getContext(), testval, Toast.LENGTH_SHORT).show();
                int limit = count -1;


                // Initialize the manager with the context and the map.
                // (Activity extends context, so we can pass 'this' in the constructor.)
                mClusterManager = new ClusterManager<MyItem>(getContext(), googleMap);

                // Point the map's listeners at the listeners implemented by the cluster
                // manager.
                googleMap.setOnCameraIdleListener(mClusterManager);
                googleMap.setOnMarkerClickListener(mClusterManager);

                for(int x = 0; x <limit; x+=3){
                    double aqitemp;
                    double lattest;
                    double longtest;

                    aqitemp = arrayvalue[x];
                    int aqitest = (int) aqitemp;
                    lattest = arrayvalue[x+1];
                    longtest = arrayvalue[x+2];
                    String color;
                    String AQITAG = new String();
                    String AQIMSG = new String();
                    //CHANGE AQI VALUE HERE



                    // DONE CHANGING AQI VALUE

                    MyItem offsetItem = new MyItem(lattest, longtest,AQITAG, AQIMSG);

                    mClusterManager.addItem(offsetItem);



                    switch(aqitest){
                        case 1:
                            color = "#00f921";
                            break;
                        case 2:
                            color = "#e5e514";
                            break;
                        case 3:
                            color = "#ff9d00";
                            break;
                        case 4:
                            color = "#ff1500";
                            break;
                        case 5:
                            color = "#1b0289";
                            break;
                        case 6:
                            color = "#4B0082";
                            break;
                        default:
                            color = "#e5e514";
                            break;


                    }

                    //googleMap.addMarker(new MarkerOptions().position(newlocation).icon(getMarkerIcon(color))
                    //);
                    //googleMap.moveCamera(CameraUpdateFactory.newLatLng(newlocation));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

/*
        for(int x = 0; x <6; x+=3){
            double aqitemp;
            double lattest;
            double longtest;

            aqitemp = arrayvalue[x];
            int aqitest = (int) aqitemp;
            lattest = arrayvalue[x+1];
            longtest = arrayvalue[x+2];
            LatLng newlocation = new LatLng(lattest,longtest);
            String color;
            switch(aqitest){
                case 1:
                    color = "#00f921";
                    break;
                case 2:
                    color = "#e5e514";
                    break;
                case 3:
                    color = "#ff9d00";
                    break;
                case 4:
                    color = "#ff1500";
                    break;
                case 5:
                    color = "#1b0289";
                    break;
                case 6:
                    color = "#000000";
                    break;
                default:
                    color = "#e5e514";
                    break;

            }
            googleMap.addMarker(new MarkerOptions().position(newlocation).icon(getMarkerIcon(color))
                    );
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(newlocation));
            }
            */



    }

    private void setUpClusterer(GoogleMap googleMap, double lat, double lng) {
        // Position the map.
        if(Settings.LocationStatus()== 1 ) {
            TrackGPS NewGPS = new TrackGPS(getContext());
            longitudev = NewGPS.getLongitude();
            latitudev = NewGPS.getLatitude();
            LatLng current = new LatLng(latitudev, longitudev);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15));
        }
        else {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 15));
        }
        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MyItem>(getContext(), googleMap);
        mClusterManager.clearItems();
        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        googleMap.setOnCameraIdleListener(mClusterManager);
        googleMap.setOnMarkerClickListener(mClusterManager);

        addItems(lat,lng);

    }

    private void addItems(double lat, double lng){

            MyItem offsetItem = new MyItem(lat, lng);
            mClusterManager.addItem(offsetItem);
        for (int i = 0; i < 10; i++) {
            double offset = i / 60d;
            lat = lat + offset;
            lng = lng + offset;
            MyItem offsetItem2 = new MyItem(lat, lng);
            mClusterManager.addItem(offsetItem2);
        }
    }


    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null)
            try {
                getFragmentManager().beginTransaction().remove(mapFragment).commit();
            }
            catch(java.lang.RuntimeException e){
            gohome();

        }

    }

    public void gohome(){
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }



    public static class Location {
        String aqi;
        String longitude;
        String latitude;

    }


    }




